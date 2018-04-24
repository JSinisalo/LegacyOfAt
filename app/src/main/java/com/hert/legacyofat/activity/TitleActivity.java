package com.hert.legacyofat.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.frag.TitleMainFragment;
import com.hert.legacyofat.frag.TitleNoteFragment;
import com.hert.legacyofat.misc.Utils;
import com.hert.legacyofat.popup.PopupEditText;
import com.hert.legacyofat.popup.PopupError;
import com.squareup.leakcanary.RefWatcher;

/**
 * Activity for handling user log in to google play games services and getting the initial Guser data from backend.
 */
public class TitleActivity extends AppCompatActivity implements AsyncResponse, FragmentResponse, PopupError.PopupErrorListener {

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;

    private PlayersClient mPlayersClient;

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_title);

        mGoogleSignInClient = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.web_id))
                .build());

        mPager = (ViewPager) findViewById(R.id.contentPager);
        PagerAdapter mPagerAdapter = new TitleActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(2);

        mPager.setCurrentItem(1);

        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);
    }

    /**
     * If user is already signed in.
     *
     * @return signed in or not
     */
    private boolean isSignedIn() {

        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    @Override
    protected void onResume() {

        super.onResume();

        // Since the state of the signed in user can change when the activity is not active
        // it is recommended to try and sign in silently from when the app resumes.
        //signInSilently();
    }

    /**
     * Attempts to sign in to gms without any prompts from the user.
     */
    private void signInSilently() {

        mGoogleSignInClient.silentSignIn().addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {

            @Override
            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {

                if(task.isSuccessful()) {

                    onConnected(task.getResult());

                } else {

                    onDisconnected();
                }
            }
        });
    }

    /**
     * On click.
     *
     * @param view the view
     */
    public void onClick(View view) {

        if(!isSignedIn())
            startSignInIntent();
        else
            signInSilently();
    }

    /**
     * Method for google pre launch report crawlers. Wont do anything in release because backend wont accept it.
     *
     * @param view the view
     */
    public void onCrawl(View view) {

        checkGuser("crawler");
    }

    /**
     * Starts the sign in intent to gms.
     */
    private void startSignInIntent() {

        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                onConnected(account);

            } catch (ApiException apiException) {

                String message = apiException.getMessage();

                if (message == null || message.isEmpty()) {

                    PopupError.newPopupError("Error: Google ApiException",
                                          "Error connecting to Google API. Going back to title screen.",
                                               0, true, getSupportFragmentManager());
                }

                onDisconnected();
            }
        }
    }

    /**
     * When the user has signed in to gms.
     *
     * @param googleSignInAccount the google sign in account
     */
    private void onConnected(final GoogleSignInAccount googleSignInAccount) {

        //master coder here mate
        mGoogleSignInAccount = googleSignInAccount;
        mPlayersClient = Games.getPlayersClient(this, googleSignInAccount);

        mPlayersClient.getCurrentPlayer().addOnCompleteListener(new OnCompleteListener<Player>() {

            @Override
            public void onComplete(@NonNull Task<Player> task) {

                if (task.isSuccessful()) {

                    //truly a marvel of programming genius
                    checkGuser(googleSignInAccount.getIdToken());

                } else {

                    PopupError.newPopupError("Error: Google ApiException",
                            "Error connecting to Google API. Going back to title screen.",
                            0, true, getSupportFragmentManager());
                }
            }
        });
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message message) {

            Toast.makeText(TitleActivity.this, message.obj.toString(), Toast.LENGTH_LONG).show();
        }
    };

    /**
     * Method for starting Guser check in backend.
     *
     * @param token google jwt token
     */
    private void checkGuser(String token) {

        mPager.setCurrentItem(0);
        new CallBackendTask(CallBackendTask.CHECK_GUSER, this).execute("api/test", token);
    }

    /**
     * Method for getting Guser data from backend.
     *
     * @param token google jwt token
     */
    private void getGuserData(String token) {

        new CallBackendTask(CallBackendTask.GET_GUSER_DATA, this).execute("api/me", token);
    }

    /**
     * Method for getting name from user to be set in the backend.
     */
    private void setGuserName() {

        PopupEditText.newPopupEditText("Whats your name?",
                "hehe xd",
                8, true, getSupportFragmentManager());
    }

    @Override
    public void showConnectingWidget() {

        findViewById(R.id.titleConnecting).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideConnectingWidget() {

        findViewById(R.id.titleConnecting).setVisibility(View.INVISIBLE);
    }

    @Override
    public void processFinish(int id, String result) {

        switch(id) {

            case CallBackendTask.CHECK_GUSER:

                if(mGoogleSignInAccount != null) {

                    Games.getGamesClient(this, mGoogleSignInAccount).setViewForPopups(findViewById(R.id.signInText));
                    Games.getGamesClient(this, mGoogleSignInAccount).setGravityForPopups(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

                    if(result.equals("success")) {

                        getGuserData(mGoogleSignInAccount.getIdToken());

                    } else {

                        setGuserName();
                    }

                } else {

                    getGuserData("crawler");
                }

                break;

            case CallBackendTask.POST_NAME:

                getGuserData(mGoogleSignInAccount.getIdToken());
                break;

            case CallBackendTask.GET_GUSER_DATA:

                Guser.setFullData(result);

                if(mGoogleSignInAccount != null)
                    Guser.setToken(mGoogleSignInAccount.getIdToken());
                else
                    Guser.setToken("crawler");

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish(); //TODO: wtf finish first?
                startActivity(intent);
                overridePendingTransition(0,0);

                break;

            case -1:
            default:

                PopupError.newPopupError("Error: Backend connectivity",
                        "Error connecting to backend. Going back to title screen.",
                        0, true, getSupportFragmentManager());

                break;
        }
    }

    /**
     * When the user has signed out of gms.
     */
    private void onDisconnected() {

        mPlayersClient = null;
    }

    /**
     * Signs out from gms.
     */
    private void signOut() {

        if (!isSignedIn()) {

            return;
        }

        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {

                boolean successful = task.isSuccessful();

                onDisconnected();
            }
        });
    }

    @Override
    public void onDialogClose(DialogFragment dialog, int id, String extra) {

        switch(id) {

            case 8:

                if(extra.equals("") || extra == null)
                    extra = "unknown";

                new CallBackendTask(CallBackendTask.POST_NAME, this).execute("api/name", mGoogleSignInAccount.getIdToken(), extra);
                break;
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        /**
         * Instantiates a new Screen slide pager adapter.
         *
         * @param fm the fm
         */
        public ScreenSlidePagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {

                case 1:
                    return new TitleMainFragment();
                case 0:
                    return new TitleNoteFragment();
                default:
                    return new TitleMainFragment();
            }
        }

        @Override
        public int getCount() {

            return NUM_PAGES;
        }
    }

    @Override
    public void onDestroy() {

        Utils.clearTextLineCache();

        mPager = null;

        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
