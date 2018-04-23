package com.hert.legacyofat.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.backend.Team;
import com.hert.legacyofat.frag.BattleSkillsFragment;
import com.hert.legacyofat.frag.BattleTeamFragment;
import com.hert.legacyofat.game.GameListener;
import com.hert.legacyofat.game.GameMaster;
import com.hert.legacyofat.layout.CustomScrollView;
import com.hert.legacyofat.misc.Utils;
import com.hert.legacyofat.popup.PopupError;
import com.hert.legacyofat.popup.PopupYesNo;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Activity which handles the battle section of the application
 */
public class BattleActivity extends AppCompatActivity implements AsyncResponse, FragmentResponse, PopupError.PopupErrorListener, GameListener {

    private boolean battleOver = false;

    private static final int NUM_PAGES = 2;
    private ViewPager mPager;

    private int team = 0;
    private int selectedSlot = 1;
    private int selectedAction = 1;
    private int selectedTarget = 1;

    private GameMaster gameMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_battle);

        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);

        team = getIntent().getExtras().getInt("team");

        mPager = (ViewPager) findViewById(R.id.battlePager);

        //TODO: find out why this fucking gay ass phone tries to orient it as portrait SOMETIMES when i explicitly tell it not to every fucking time
        if(mPager == null) {

            processFinish(-1, "");
            return;

        } else {

            PagerAdapter mPagerAdapter = new BattleActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
            mPager.setOffscreenPageLimit(2);
        }

        pushText("The battle begins...\nClick to continue...");

        clearBorders();

        final View rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
        new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                try {

                    gameMaster = new GameMaster(BattleActivity.this, getTeam(), new JSONObject(getIntent().getExtras().getString("json")));
                    updateNames();
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Highlights the wanted enemy.
     *
     * @param i     enemy to highlight
     * @param clear whether to clear other enemies highlights or not
     */
    public void highlightEnemy(int i, boolean clear) {

        if(clear)
            clearBorders();

        switch(i) {

            case 1:
                findViewById(R.id.eslot1).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                break;
            case 2:
                findViewById(R.id.eslot2).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                break;
            case 3:
                findViewById(R.id.eslot3).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                break;
            case 4:
                findViewById(R.id.eslot4).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                break;
        }

        findViewById(R.id.eslot1).refreshDrawableState();
        findViewById(R.id.eslot2).refreshDrawableState();
        findViewById(R.id.eslot3).refreshDrawableState();
        findViewById(R.id.eslot4).refreshDrawableState();
    }

    /**
     * Handles onClick events from the user.
     *
     * @param v the view clicked
     */
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.battleText:

                if(mPager.getCurrentItem() == 0) {

                    if(!battleOver) {

                        gameMaster.advance();

                        //new CallBackendTask(CallBackendTask.START_BATTLERESULTS, this).execute("http://91.155.202.223:7500/api/battleresult", Guser.getToken());

                    } else {

                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }

                } else {

                    pushText("Select an action to use before advancing the turn...");
                }

                return;

            case R.id.eslot1:

                if(mPager.getCurrentItem() != 0) {

                    setSelectedTarget(1);
                }
                else
                    return;

                break;

            case R.id.eslot2:

                if(mPager.getCurrentItem() != 0) {

                    setSelectedTarget(2);
                }
                else
                    return;

                break;

            case R.id.eslot3:

                if(mPager.getCurrentItem() != 0) {

                    setSelectedTarget(3);
                }
                else
                    return;

                break;

            case R.id.eslot4:

                if(mPager.getCurrentItem() != 0) {

                    setSelectedTarget(4);
                }
                else
                    return;

                break;

            default:

                return;
        }

        if(gameMaster.getTargets(gameMaster.getP(selectedSlot).getSelectedAction()) == 1)

            highlightEnemy(gameMaster.getSelectedTarget(selectedSlot), true);

        else {

            highlightEnemy(1, false);
            highlightEnemy(2, false);
            highlightEnemy(3, false);
            highlightEnemy(4, false);
        }
    }

    /**
     * Updates the healthbars of all characters in the game
     *
     * @param list the list of characters, must be in the correct order (0-3 pcharas, 4-7 echaras)
     */
    public void updateHealthBars(List<Chara> list) {

        //TODO: how could this happen

        ((ProgressBar)findViewById(R.id.pbar1)).setProgress((int)((list.get(0).getHealth() / list.get(0).getMaxHealth()) * 100));
        ((ProgressBar)findViewById(R.id.pbar2)).setProgress((int)((list.get(1).getHealth() / list.get(1).getMaxHealth()) * 100));
        ((ProgressBar)findViewById(R.id.pbar3)).setProgress((int)((list.get(2).getHealth() / list.get(2).getMaxHealth()) * 100));
        ((ProgressBar)findViewById(R.id.pbar4)).setProgress((int)((list.get(3).getHealth() / list.get(3).getMaxHealth()) * 100));

        ((ProgressBar)findViewById(R.id.ebar1)).setProgress((int)((list.get(4).getHealth() / list.get(4).getMaxHealth()) * 100));
        ((ProgressBar)findViewById(R.id.ebar2)).setProgress((int)((list.get(5).getHealth() / list.get(5).getMaxHealth()) * 100));
        ((ProgressBar)findViewById(R.id.ebar3)).setProgress((int)((list.get(6).getHealth() / list.get(6).getMaxHealth()) * 100));
        ((ProgressBar)findViewById(R.id.ebar4)).setProgress((int)((list.get(7).getHealth() / list.get(7).getMaxHealth()) * 100));

        ((TextView)findViewById(R.id.pbart1)).setText(Math.round((int)list.get(0).getHealth()) + "/" + Math.round((int)list.get(0).getMaxHealth()));
        ((TextView)findViewById(R.id.pbart2)).setText(Math.round((int)list.get(1).getHealth()) + "/" + Math.round((int)list.get(1).getMaxHealth()));
        ((TextView)findViewById(R.id.pbart3)).setText(Math.round((int)list.get(2).getHealth()) + "/" + Math.round((int)list.get(2).getMaxHealth()));
        ((TextView)findViewById(R.id.pbart4)).setText(Math.round((int)list.get(3).getHealth()) + "/" + Math.round((int)list.get(3).getMaxHealth()));

        ((TextView)findViewById(R.id.ebart1)).setText(Math.round((int)list.get(4).getHealth()) + "/" + Math.round((int)list.get(4).getMaxHealth()));
        ((TextView)findViewById(R.id.ebart2)).setText(Math.round((int)list.get(5).getHealth()) + "/" + Math.round((int)list.get(5).getMaxHealth()));
        ((TextView)findViewById(R.id.ebart3)).setText(Math.round((int)list.get(6).getHealth()) + "/" + Math.round((int)list.get(6).getMaxHealth()));
        ((TextView)findViewById(R.id.ebart4)).setText(Math.round((int)list.get(7).getHealth()) + "/" + Math.round((int)list.get(7).getMaxHealth()));
    }

    /**
     * Updates graphics of all characters according to Guser data and the recieved enemy data.
     */
    public void updateNames() {

        List<Team> teams = Guser.getTeams();
        int position = getTeam();

        try {

            JSONObject json = new JSONObject(getIntent().getExtras().getString("json"));

            if(!gameMaster.getE(1).isDead()) {
                ((TextView)findViewById(R.id.eslot1)).setText(json.getJSONObject("char1").getString("graphic"));
                ((TextView)findViewById(R.id.eslot1)).setTextColor(Color.parseColor(json.getJSONObject("char1").getString("color")));
            }
            if(!gameMaster.getE(2).isDead()) {
                ((TextView)findViewById(R.id.eslot2)).setText(json.getJSONObject("char2").getString("graphic"));
                ((TextView)findViewById(R.id.eslot2)).setTextColor(Color.parseColor(json.getJSONObject("char2").getString("color")));
            }
            if(!gameMaster.getE(3).isDead()) {
                ((TextView)findViewById(R.id.eslot3)).setText(json.getJSONObject("char3").getString("graphic"));
                ((TextView)findViewById(R.id.eslot3)).setTextColor(Color.parseColor(json.getJSONObject("char3").getString("color")));
            }
            if(!gameMaster.getE(4).isDead()) {
                ((TextView)findViewById(R.id.eslot4)).setText(json.getJSONObject("char4").getString("graphic"));
                ((TextView)findViewById(R.id.eslot4)).setTextColor(Color.parseColor(json.getJSONObject("char4").getString("color")));
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

        ((TextView)findViewById(R.id.pslot1)).setText(Guser.getGraphicFromPosition(teams.get(position).getChar1()));
        ((TextView)findViewById(R.id.pslot2)).setText(Guser.getGraphicFromPosition(teams.get(position).getChar2()));
        ((TextView)findViewById(R.id.pslot3)).setText(Guser.getGraphicFromPosition(teams.get(position).getChar3()));
        ((TextView)findViewById(R.id.pslot4)).setText(Guser.getGraphicFromPosition(teams.get(position).getChar4()));

        ((TextView)findViewById(R.id.pslot1)).setTextColor(Color.parseColor(Guser.getColorFromPosition(teams.get(position).getChar1())));
        ((TextView)findViewById(R.id.pslot2)).setTextColor(Color.parseColor(Guser.getColorFromPosition(teams.get(position).getChar2())));
        ((TextView)findViewById(R.id.pslot3)).setTextColor(Color.parseColor(Guser.getColorFromPosition(teams.get(position).getChar3())));
        ((TextView)findViewById(R.id.pslot4)).setTextColor(Color.parseColor(Guser.getColorFromPosition(teams.get(position).getChar4())));
    }

    /**
     * Clear borders of echaras.
     */
    public void clearBorders() {

        findViewById(R.id.eslot1).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
        findViewById(R.id.eslot2).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
        findViewById(R.id.eslot3).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
        findViewById(R.id.eslot4).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));

        findViewById(R.id.eslot1).refreshDrawableState();
        findViewById(R.id.eslot2).refreshDrawableState();
        findViewById(R.id.eslot3).refreshDrawableState();
        findViewById(R.id.eslot4).refreshDrawableState();
    }

    @Override
    public void showConnectingWidget() {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        findViewById(R.id.battleConnecting).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideConnectingWidget() {

        findViewById(R.id.battleConnecting).setVisibility(View.INVISIBLE);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * Changes the pageviewer page position.
     *
     * @param position the position to change to
     */
    public void changePage(int position) {

        mPager.setCurrentItem(position);

        //TODO: clean up every single instance of these fucking shit ass fucks

        if(position == 0) {

            gameMaster.setSelectedAction(selectedAction);

            BattleTeamFragment f = (BattleTeamFragment)((ScreenSlidePagerAdapter)((ViewPager)findViewById(R.id.battlePager)).getAdapter()).getRegisteredFragment(0);

            f.clearBorders();
            clearBorders();

        } else {

            BattleSkillsFragment f = (BattleSkillsFragment)((ScreenSlidePagerAdapter)((ViewPager)findViewById(R.id.battlePager)).getAdapter()).getRegisteredFragment(1);

            f.updateInfo(selectedSlot);

            if(gameMaster.getSelectedTarget(selectedSlot) != -1)
                highlightEnemy(gameMaster.getSelectedTarget(selectedSlot), true);
            else {

                highlightEnemy(1, false);
                highlightEnemy(2, false);
                highlightEnemy(3, false);
                highlightEnemy(4, false);
            }
        }
    }

    @Override
    public void processFinish(int id, String result) {

        switch(id) {

            case CallBackendTask.START_BATTLERESULTS_WIN:

                int jims = 0;
                int gold = 0;
                int battle = 0;
                boolean renkUp = false;

                try {

                    JSONObject json = new JSONObject(result);

                    gold = json.getInt("gold");
                    jims = json.getInt("jims");
                    battle = json.getInt("battle");
                    renkUp = json.getBoolean("rankUp");

                } catch (JSONException e) {

                    e.printStackTrace();
                }

                Guser.setJims(Guser.getJims() + jims);
                Guser.setGold(Guser.getGold() + gold);
                Guser.getBattles().get(battle).setCleared(true);

                if(renkUp)
                    Guser.setRank(Guser.getRank() + 1);

                pushText("You have won!");
                pushText("You got " + jims + " jims \nand " + gold + " gold \nClick to continue...");

                battleOver = true;

                break;

            case CallBackendTask.START_BATTLERESULTS_LOSE:

                pushText("You have been defeated...");
                pushText("\n Click to continue...");

                battleOver = true;

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
    public void lose() {

        new CallBackendTask(CallBackendTask.START_BATTLERESULTS_LOSE, this).execute("api/battlelose", Guser.getToken());
    }

    @Override
    public void win() {

        new CallBackendTask(CallBackendTask.START_BATTLERESULTS_WIN, this).execute("api/battleresult", Guser.getToken());
    }

    @Override
    public void onDialogClose(DialogFragment dialog, int id, String extra) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    public void onBackPressed() {

        PopupYesNo.newPopupYesNo("Return to main menu?",
                "You will lose all progress",
                0, false, getSupportFragmentManager());
    }

    @Override
    public void pushText(String string) {

        SpannableStringBuilder p = SpannableStringBuilder.valueOf(((TextView)findViewById(R.id.battleText)).getText());
        p.append(string);
        p.append("\n\n");

        ((TextView)findViewById(R.id.battleText)).setText(p, TextView.BufferType.SPANNABLE);

        ((CustomScrollView)findViewById(R.id.battleScroll)).post(new Runnable() {
            @Override
            public void run() {
                ((CustomScrollView)findViewById(R.id.battleScroll)).fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void pushText(SpannableStringBuilder builder) {

        SpannableStringBuilder p = SpannableStringBuilder.valueOf(((TextView)findViewById(R.id.battleText)).getText());
        p.append(builder);
        p.append("\n\n");

        ((TextView)findViewById(R.id.battleText)).setText(p, TextView.BufferType.SPANNABLE);

        ((CustomScrollView)findViewById(R.id.battleScroll)).post(new Runnable() {
            @Override
            public void run() {
                ((CustomScrollView)findViewById(R.id.battleScroll)).fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    /**
     * Pushes the description of the user selected action slot.
     *
     * @param slot    the slot
     * @param targets if there are more than one target, highlight all targets
     */
    public void pushActionDescriptionText(int slot, int targets) {

        gameMaster.pushActionDescriptionText(slot);

        if(targets > 1) {

            highlightEnemy(1, false);
            highlightEnemy(2, false);
            highlightEnemy(3, false);
            highlightEnemy(4, false);

        } else {

            highlightEnemy(selectedTarget, true);
        }
    }

    /**
     * Gets cooldown of the desired slot (of the selected character).
     *
     * @param slot the slot
     * @return the cooldown
     */
    public int getCooldown(int slot) {

        return gameMaster.getCooldown(slot);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        /**
         * The Registered fragments in this pager.
         */
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

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
                    return new BattleTeamFragment();
                case 1:
                    return new BattleSkillsFragment();
                default:
                    return new BattleTeamFragment();
            }
        }

        @Override
        public int getCount() {

            return NUM_PAGES;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);

            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        /**
         * Gets registered fragment.
         *
         * @param position the position
         * @return the registered fragment
         */
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

    /**
     * Gets the team for this battle.
     *
     * @return the team
     */
    public int getTeam() {
        return team;
    }

    /**
     * Sets selected character.
     *
     * @param selectedChara the selected chara
     */
    public void setSelectedSlot(int selectedChara) {

        this.selectedSlot = selectedChara;
        gameMaster.setSelectedChara(selectedChara);
    }

    /**
     * Gets the amount of targets of the desired action slot.
     *
     * @param slot the slot
     * @return the targets
     */
    public int getTargets(int slot) {

        return gameMaster.getTargets(slot);
    }

    /**
     * Sets selected target.
     *
     * @param selectedTarget the selected target
     */
    public void setSelectedTarget(int selectedTarget) {

        this.selectedTarget = selectedTarget;
        gameMaster.setSelectedTarget(selectedTarget);
    }

    /**
     * Sets selected action.
     *
     * @param selectedAction the selected action
     */
    public void setSelectedAction(int selectedAction) {

        this.selectedAction = selectedAction;
    }

    /**
     * Gets selected action.
     *
     * @param slot the slot
     * @return the selected action
     */
    public int getSelectedAction(int slot) {

        return gameMaster.getSelectedAction(slot);
    }

    /**
     * Kills (hides) the desired character.
     *
     * @param slot character number to hide
     */
    public void kill(int slot) {

        switch(slot) {

            case 1:
                findViewById(R.id.pslot1).setVisibility(View.INVISIBLE);
                findViewById(R.id.pbar1).setVisibility(View.INVISIBLE);
                findViewById(R.id.pbart1).setVisibility(View.INVISIBLE);
                ((BattleTeamFragment)((ScreenSlidePagerAdapter)((ViewPager)findViewById(R.id.battlePager)).getAdapter()).getRegisteredFragment(0)).kill(slot);
                break;
            case 2:
                findViewById(R.id.pslot2).setVisibility(View.INVISIBLE);
                findViewById(R.id.pbar2).setVisibility(View.INVISIBLE);
                findViewById(R.id.pbart2).setVisibility(View.INVISIBLE);
                ((BattleTeamFragment)((ScreenSlidePagerAdapter)((ViewPager)findViewById(R.id.battlePager)).getAdapter()).getRegisteredFragment(0)).kill(slot);
                break;
            case 3:
                findViewById(R.id.pslot3).setVisibility(View.INVISIBLE);
                findViewById(R.id.pbar3).setVisibility(View.INVISIBLE);
                findViewById(R.id.pbart3).setVisibility(View.INVISIBLE);
                ((BattleTeamFragment)((ScreenSlidePagerAdapter)((ViewPager)findViewById(R.id.battlePager)).getAdapter()).getRegisteredFragment(0)).kill(slot);
                break;
            case 4:
                findViewById(R.id.pslot4).setVisibility(View.INVISIBLE);
                findViewById(R.id.pbar4).setVisibility(View.INVISIBLE);
                findViewById(R.id.pbart4).setVisibility(View.INVISIBLE);
                ((BattleTeamFragment)((ScreenSlidePagerAdapter)((ViewPager)findViewById(R.id.battlePager)).getAdapter()).getRegisteredFragment(0)).kill(slot);
                break;
            case 5:
                findViewById(R.id.eslot1).setVisibility(View.INVISIBLE);
                findViewById(R.id.ebar1).setVisibility(View.INVISIBLE);
                findViewById(R.id.ebart1).setVisibility(View.INVISIBLE);
                findViewById(R.id.eslot1).setOnClickListener(null);
                findViewById(R.id.eslot1).setClickable(false);
                break;
            case 6:
                findViewById(R.id.eslot2).setVisibility(View.INVISIBLE);
                findViewById(R.id.ebar2).setVisibility(View.INVISIBLE);
                findViewById(R.id.ebart2).setVisibility(View.INVISIBLE);
                findViewById(R.id.eslot2).setOnClickListener(null);
                findViewById(R.id.eslot2).setClickable(false);
                break;
            case 7:
                findViewById(R.id.eslot3).setVisibility(View.INVISIBLE);
                findViewById(R.id.ebar3).setVisibility(View.INVISIBLE);
                findViewById(R.id.ebart3).setVisibility(View.INVISIBLE);
                findViewById(R.id.eslot3).setOnClickListener(null);
                findViewById(R.id.eslot3).setClickable(false);
                break;
            case 8:
                findViewById(R.id.eslot4).setVisibility(View.INVISIBLE);
                findViewById(R.id.ebar4).setVisibility(View.INVISIBLE);
                findViewById(R.id.ebart4).setVisibility(View.INVISIBLE);
                findViewById(R.id.eslot4).setOnClickListener(null);
                findViewById(R.id.eslot4).setClickable(false);
                break;
        }
    }

    @Override
    public void onDestroy() {

        Utils.clearTextLineCache();

        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
