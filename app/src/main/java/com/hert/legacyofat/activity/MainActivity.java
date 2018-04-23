package com.hert.legacyofat.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.TextView;

import com.hert.legacyofat.R;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.frag.BattleFragment;
import com.hert.legacyofat.frag.GachaMainFragment;
import com.hert.legacyofat.frag.GachaResultFragment;
import com.hert.legacyofat.frag.MainFragment;
import com.hert.legacyofat.frag.RosterFragment;
import com.hert.legacyofat.frag.RosterListFragment;
import com.hert.legacyofat.frag.ShopFragment;
import com.hert.legacyofat.frag.ShopInventoryFragment;
import com.hert.legacyofat.frag.ShopShopFragment;
import com.hert.legacyofat.misc.Utils;
import com.hert.legacyofat.popup.PopupError;

import static com.hert.legacyofat.popup.PopupError.GACHA_ENOUGH_1;
import static com.hert.legacyofat.popup.PopupError.GACHA_ENOUGH_10;
import static com.hert.legacyofat.popup.PopupError.GACHA_NOT;

/**
 * Activity which handles all menu fragments and their interactions
 */
public class MainActivity extends AppCompatActivity implements AsyncResponse, FragmentResponse, PopupError.PopupErrorListener {

    private static final int NUM_PAGES = 5;
    private ViewPager mPager;

    private int selectedChara = -1;
    private int team = 0;

    //TODO: this is a bit shit
    private RosterListFragment rosterListFragment;
    private ShopInventoryFragment inventoryListFragment;
    private ShopInventoryFragment inventoryListFragment2;
    private ShopShopFragment shopFragment;
    private GachaResultFragment resultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.contentPager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(5);

        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);

        setTopText();
    }

    /**
     * Sets the top info bar text according to Guser data.
     */
    public void setTopText() {

        TextView name = (TextView) findViewById(R.id.mainName);
        TextView rank = (TextView) findViewById(R.id.mainLevel);
        TextView jims = (TextView) findViewById(R.id.mainJims);
        TextView gold = (TextView) findViewById(R.id.mainGold);

        name.setText(Guser.getName());
        rank.setText("rank " + Guser.getRank());
        jims.setText("jims " + Guser.getJims());
        gold.setText("gold " + Guser.getGold());
    }

    /**
     * Clears the colors of the bottom nav buttons.
     */
    private void clearColors() {

        ((Button)findViewById(R.id.mainButton)).setTextColor(Color.parseColor("#FFFFFF"));
        ((Button)findViewById(R.id.rosterButton)).setTextColor(Color.parseColor("#FFFFFF"));
        ((Button)findViewById(R.id.battleButton)).setTextColor(Color.parseColor("#FFFFFF"));
        ((Button)findViewById(R.id.gachaButton)).setTextColor(Color.parseColor("#FFFFFF"));
        ((Button)findViewById(R.id.shopButton)).setTextColor(Color.parseColor("#FFFFFF"));
    }

    /**
     * Handles onClick events from the user.
     *
     * @param v the view clicked
     */
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.mainButton:

                clearColors();

                ((Button)v).setTextColor(Color.parseColor("#00FF00"));

                mPager.setCurrentItem(0);
                break;

            case R.id.rosterButton:

                clearColors();

                ((Button)v).setTextColor(Color.parseColor("#00FF00"));

                mPager.setCurrentItem(1);
                break;

            case R.id.battleButton:

                clearColors();

                ((Button)v).setTextColor(Color.parseColor("#00FF00"));

                mPager.setCurrentItem(2);
                break;

            case R.id.gachaButton:

                clearColors();

                ((Button)v).setTextColor(Color.parseColor("#00FF00"));

                mPager.setCurrentItem(3);
                break;

            case R.id.shopButton:

                clearColors();

                ((Button)v).setTextColor(Color.parseColor("#00FF00"));

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

                resultFragment.setResults(result);
                resultFragment.changePage();

                Guser.setFullData(result);

                setTopText();

                rosterListFragment.updateData();
                inventoryListFragment.updateData();
                inventoryListFragment2.updateData();

                break;

            case CallBackendTask.POST_TEAM_DATA:

                if(!result.equals("success")) {

                    PopupError.newPopupError("Error: Backend connectivity",
                            "Error connecting to backend. Going back to title screen.",
                            0, true, getSupportFragmentManager());
                }

                break;

            case CallBackendTask.POST_CHARA_ITEMS:

                if(!result.equals("success")) {

                    PopupError.newPopupError("Error: Backend connectivity",
                            "Error connecting to backend. Going back to title screen.",
                            0, true, getSupportFragmentManager());
                }

                break;

            case CallBackendTask.BUY_ITEM:

                Guser.setFullData(result);

                inventoryListFragment.updateData();
                inventoryListFragment2.updateData();

                setTopText();

                break;

            case CallBackendTask.GET_GUSER_DATA:

                Guser.setFullData(result);

                setTopText();

                break;

            case CallBackendTask.START_BATTLE:

                Bundle b = new Bundle();
                b.putString("json", result);
                b.putInt("team", team);

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
    public void onDialogClose(DialogFragment dialog, int id, String extra) {

        if(id < 100) {

            switch(id){

                case GACHA_NOT:

                    break;

                case GACHA_ENOUGH_1:

                    new CallBackendTask(CallBackendTask.GET_GUSER_ROLL, this).execute("api/roll", Guser.getToken());

                    break;

                case GACHA_ENOUGH_10:

                    new CallBackendTask(CallBackendTask.GET_GUSER_ROLL, this).execute("api/bigroll", Guser.getToken());

                    break;

                case 11:

                    inventoryListFragment.switchItem(11);
                    break;

                case 12:

                    inventoryListFragment.switchItem(12);
                    break;

                case 13:

                    inventoryListFragment.switchItem(13);
                    break;

                case 21:

                    shopFragment.buyItem(21);

                default:

                    break;
            }
            
        } else {

            new CallBackendTask(CallBackendTask.START_BATTLE, this).execute("api/battlestart", Guser.getToken(), (id - 100) + "");
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

                case 0:
                    return new MainFragment();
                case 1:
                    return new RosterFragment();
                case 2:
                    return new BattleFragment();
                case 3:
                    return new GachaMainFragment();
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

    /**
     * Gets selected character in roster fragment.
     *
     * @return the selected chara
     */
//TODO: cant this be better like why wtf
    public int getSelectedChara() {
        return selectedChara;
    }

    /**
     * Sets selected chara in roster fragment.
     *
     * @param selectedChara the selected chara
     */
//TODO: cant this be better like why wtf
    public void setSelectedChara(int selectedChara) {
        this.selectedChara = selectedChara;
    }

    /**
     * Sets team name and selected team in the top info bar.
     *
     * @param name the name
     * @param team the team
     */
    public void setTeamName(String name, int team) { ((TextView)findViewById(R.id.mainTeam)).setText(name); this.team = team; }

    /**
     * Sets roster list fragment for further interactions.
     *
     * @param rosterListFragment the roster list fragment
     */
    public void setRosterListFragment(RosterListFragment rosterListFragment) {
        this.rosterListFragment = rosterListFragment;
    }

    /**
     * Sets inventory list fragment for further interactions.
     *
     * @param rosterListFragment the roster list fragment
     */
    public void setInventoryListFragment(ShopInventoryFragment rosterListFragment) {
        this.inventoryListFragment = rosterListFragment;
    }

    /**
     * Sets inventory list fragment 2 for further interactions.
     *
     * @param rosterListFragment the roster list fragment
     */
    public void setInventoryListFragment2(ShopInventoryFragment rosterListFragment) {
        this.inventoryListFragment2 = rosterListFragment;
    }

    /**
     * Sets shop fragment for further interactions.
     *
     * @param shopFragment the shop fragment
     */
    public void setShopFragment(ShopShopFragment shopFragment) {
        this.shopFragment = shopFragment;
    }

    /**
     * Sets result fragment for further interactions.
     *
     * @param resultFragment the result fragment
     */
    public void setResultFragment(GachaResultFragment resultFragment) {
        this.resultFragment = resultFragment;
    }

    /**
     * Gets the selected team.
     *
     * @return the team
     */
    public int getTeam() {
        return team;
    }

    @Override
    public void onBackPressed() {

        if(mPager.getCurrentItem() != 0)
            mPager.setCurrentItem(0);
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {

        Utils.clearTextLineCache();

        mPager = null;
        rosterListFragment = null;
        resultFragment = null;
        shopFragment = null;
        inventoryListFragment = null;
        inventoryListFragment2 = null;

        super.onDestroy();
    }
}
