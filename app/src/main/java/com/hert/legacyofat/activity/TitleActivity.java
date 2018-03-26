package com.hert.legacyofat.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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
import com.hert.legacyofat.R;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.error.PopupError;
import com.hert.legacyofat.misc.Debug;

public class TitleActivity extends AppCompatActivity implements AsyncResponse, FragmentResponse, PopupError.PopupErrorListener {

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;

    private PlayersClient mPlayersClient;

    private static final int RC_SIGN_IN = 9001;
    private static final int CHECK_GUSER = 1;
    private static final int GET_GUSER_DATA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_title);

        Debug.log(GoogleSignIn.getLastSignedInAccount(this) + "");

        mGoogleSignInClient = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.web_id))
                .build());
    }

    private boolean isSignedIn() {

        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    @Override
    protected void onResume() {

        super.onResume();

        // Since the state of the signed in user can change when the activity is not active
        // it is recommended to try and sign in silently from when the app resumes.
        signInSilently();
    }

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

    public void onClick(View view) {

        if(!isSignedIn())
            startSignInIntent();
        else
            signInSilently();
    }

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

                    Bundle b = new Bundle();

                    b.putString("title", "Error: Google ApiException");
                    b.putString("content", "Error connecting to Google API. Going back to title screen.");
                    b.putBoolean("restart", true);

                    DialogFragment dialog = new PopupError();
                    dialog.setArguments(b);
                    dialog.show(getSupportFragmentManager(), "PopupError");
                }

                onDisconnected();
            }
        }
    }

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

                    Bundle b = new Bundle();

                    b.putString("title", "Error: Google ApiException");
                    b.putString("content", "Error getting Google Games player data. Going back to title screen.");
                    b.putBoolean("restart", true);

                    DialogFragment dialog = new PopupError();
                    dialog.setArguments(b);
                    dialog.show(getSupportFragmentManager(), "PopupError");
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

    private void checkGuser(String token) {

        new CallBackendTask(CHECK_GUSER, this).execute("http://91.155.202.223:7500/api/test", token);
    }

    private void getGuserData(String token) {

        new CallBackendTask(GET_GUSER_DATA, this).execute("http://91.155.202.223:7500/api/me", token);
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

            case CHECK_GUSER:

                Games.getGamesClient(this, mGoogleSignInAccount).setViewForPopups(findViewById(R.id.signInText));
                Games.getGamesClient(this, mGoogleSignInAccount).setGravityForPopups(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

                getGuserData(mGoogleSignInAccount.getIdToken());

                break;

            case GET_GUSER_DATA:

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("INITIALDATA", result);
                intent.putExtra("GOOGLESIGNINACCOUNT", mGoogleSignInAccount);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish(); //TODO: wtf finish first?
                startActivity(intent);
                overridePendingTransition(0,0);

                break;

            case -1:
            default:

                Bundle b = new Bundle();

                b.putString("title", "Error: Backend connectivity");
                b.putString("content", "Error communicating with backend. Going back to title screen.");
                b.putBoolean("restart", true);

                DialogFragment dialog = new PopupError();
                dialog.setArguments(b);
                dialog.show(getSupportFragmentManager(), "PopupError");

                break;
        }
    }

    private void onDisconnected() {

        mPlayersClient = null;
    }

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
    public void onDialogClose(DialogFragment dialog) {

    }
}
