package com.hert.legacyofat.activity;

import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.TextView;

import com.hert.legacyofat.R;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.frag.BattleFragment;
import com.hert.legacyofat.frag.GachaFragment;
import com.hert.legacyofat.frag.MainFragment;
import com.hert.legacyofat.frag.RosterFragment;
import com.hert.legacyofat.frag.RosterListFragment;
import com.hert.legacyofat.frag.ShopFragment;
import com.hert.legacyofat.misc.Debug;
import com.hert.legacyofat.popup.PopupError;

import static com.hert.legacyofat.popup.PopupError.GACHA_ENOUGH_1;
import static com.hert.legacyofat.popup.PopupError.GACHA_ENOUGH_10;
import static com.hert.legacyofat.popup.PopupError.GACHA_NOT;

public class MainActivity extends AppCompatActivity implements AsyncResponse, FragmentResponse, PopupError.PopupErrorListener {

    private static final int NUM_PAGES = 5;
    private ViewPager mPager;

    private int selectedChara = -1;

    //TODO: this is a bit shit
    private RosterListFragment rosterListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.contentPager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(5);

        setTopText();
    }

    public void setTopText() {

        TextView name = (TextView) findViewById(R.id.mainName);
        TextView rank = (TextView) findViewById(R.id.mainLevel);
        TextView jims = (TextView) findViewById(R.id.mainJims);
        TextView gold = (TextView) findViewById(R.id.mainGold);

        name.setText(Guser.getName());
        rank.setText("Rank " + Guser.getRank());
        jims.setText("Jims " + Guser.getJims());
        gold.setText("Gold " + Guser.getGold());
    }

    public void onClick(View v) {

        switch(v.getId()){

            case R.id.mainButton:

                if(mPager.getCurrentItem() == 0) {

                    PopupError.newPopupError("Error: Test error",
                            "Testing errors, going back to title screen.",
                            0, true, getSupportFragmentManager());
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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        findViewById(R.id.mainConnecting).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideConnectingWidget() {

        findViewById(R.id.mainConnecting).setVisibility(View.INVISIBLE);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void processFinish(int id, String result) {

        switch(id) {

            case CallBackendTask.GET_GUSER_ROLL:

                Guser.setFullData(result);

                setTopText();

                rosterListFragment.updateData();

                break;

            case CallBackendTask.POST_TEAM_DATA:

                if(!result.equals("success")) {

                    PopupError.newPopupError("Error: Backend connectivity",
                            "Error connecting to backend. Going back to title screen.",
                            0, true, getSupportFragmentManager());
                }

                break;

            case CallBackendTask.GET_GUSER_DATA:

                Guser.setFullData(result);

                setTopText();

                break;

            case CallBackendTask.START_BATTLE:

                Bundle b = new Bundle();
                b.putString("json", result);

                Intent intent = new Intent(this, BattleActivity.class);
                intent.putExtras(b);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
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

    @Override
    public void onDialogClose(DialogFragment dialog, int id) {

        Debug.log(id);

        switch(id){

            case GACHA_NOT:

                break;

            case GACHA_ENOUGH_1:

                new CallBackendTask(CallBackendTask.GET_GUSER_ROLL, this).execute("http://91.155.202.223:7500/api/roll", Guser.getToken());

                break;

            case GACHA_ENOUGH_10:

                new CallBackendTask(CallBackendTask.GET_GUSER_ROLL, this).execute("http://91.155.202.223:7500/api/bigroll", Guser.getToken());

                break;

            default:

                break;
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {

                case 0:
                    return new MainFragment();
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

    public void setTeamName(String name) { ((TextView)findViewById(R.id.mainTeam)).setText(name); }

    public RosterListFragment getRosterListFragment() {
        return rosterListFragment;
    }

    public void setRosterListFragment(RosterListFragment rosterListFragment) {
        this.rosterListFragment = rosterListFragment;
    }

    @Override
    protected void onDestroy() {

        mPager = null;
        rosterListFragment = null;

        super.onDestroy();
    }
}
