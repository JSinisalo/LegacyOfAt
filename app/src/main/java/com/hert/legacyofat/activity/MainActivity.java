package com.hert.legacyofat.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.hert.legacyofat.R;
import com.hert.legacyofat.error.PopupError;
import com.hert.legacyofat.frag.BattleFragment;
import com.hert.legacyofat.frag.GachaFragment;
import com.hert.legacyofat.frag.MainFragment;
import com.hert.legacyofat.frag.RosterFragment;
import com.hert.legacyofat.frag.ShopFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements AsyncResponse, FragmentResponse, PopupError.PopupErrorListener {

    private GoogleSignInAccount mGoogleSignInAccount;
    private JSONObject initialData;

    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private int selectedChara;

    public JSONObject getInitialData() { return initialData; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mGoogleSignInAccount = (GoogleSignInAccount)getIntent().getExtras().get("GOOGLESIGNINACCOUNT");

        try {

            initialData = new JSONObject((String)getIntent().getExtras().get("INITIALDATA"));

        } catch (JSONException e) {

            e.printStackTrace();
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.contentPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    public void onClick(View v) {

        switch(v.getId()){

            case R.id.mainButton:

                if(mPager.getCurrentItem() == 0) {

                    Bundle b = new Bundle();

                    b.putString("title", "Error: Test");
                    b.putString("content", "Test error. Going back to title screen.");
                    b.putBoolean("restart", true);

                    DialogFragment dialog = new PopupError();
                    dialog.setArguments(b);
                    dialog.show(getSupportFragmentManager(), "PopupError");
                }

                mPager.setCurrentItem(0);
                break;

            case R.id.rosterButton:

                mPager.setCurrentItem(1);
                break;

            case R.id.battleButton:

                mPager.setCurrentItem(2);
                break;

            case R.id.gachaButton:

                mPager.setCurrentItem(3);
                break;

            case R.id.shopButton:

                mPager.setCurrentItem(4);
                break;

            default:

                //just ignore the calls from fagments
                break;
        }
    }

    @Override
    public void showConnectingWidget() {

        findViewById(R.id.mainConnecting).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideConnectingWidget() {

        findViewById(R.id.mainConnecting).setVisibility(View.INVISIBLE);
    }

    @Override
    public void processFinish(int id, String result) {

    }

    @Override
    public void onDialogClose(DialogFragment dialog) {

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {

                case 0:

                    Bundle b = new Bundle();

                    try {

                        b.putString("initial", initialData.getString("loginAmount"));

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    MainFragment frag = new MainFragment();
                    frag.setArguments(b);

                    return frag;

                case 1:
                    return new RosterFragment();
                case 2:
                    return new BattleFragment();
                case 3:
                    return new GachaFragment();
                case 4:
                    return new ShopFragment();
                default:
                    return new MainFragment();
            }
        }

        @Override
        public int getCount() {

            return NUM_PAGES;
        }
    }

    public int getSelectedChara() {
        return selectedChara;
    }

    public void setSelectedChara(int selectedChara) {
        this.selectedChara = selectedChara;
    }
}
