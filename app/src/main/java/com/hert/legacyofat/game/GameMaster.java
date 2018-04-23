package com.hert.legacyofat.game;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.backend.Team;
import com.hert.legacyofat.misc.Random;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class that handles the game related events, such as advancing a turn.
 */
public class GameMaster {

    private GameListener parent;

    private Chara p1;
    private Chara p2;
    private Chara p3;
    private Chara p4;

    private Chara e1;
    private Chara e2;
    private Chara e3;
    private Chara e4;

    private int selectedTarget = 1;
    private int selectedChara = 1;
    private int selectedAction = 1;

    private boolean battleOver = false;

    /**
     * Instantiates a new Game master.
     * <p>
     * Copies all the selected characters into the variables here and sets them up.
     *
     * @param parent the parent (battle activity)
     * @param team   the selected team of user
     * @param json   the json enemy data
     */
    public GameMaster(GameListener parent, int team, JSONObject json) {

        this.parent = parent;
        List<Team> teams = Guser.getTeams();

        e1 = Guser.JSONToChara(json,"char1");
        e2 = Guser.JSONToChara(json,"char2");
        e3 = Guser.JSONToChara(json,"char3");
        e4 = Guser.JSONToChara(json,"char4");

        if(e1 == null) {

            e1 = new Chara();
            e1.setDead(true);
            parent.kill(5);
        }
        if(e2 == null) {

            e2 = new Chara();
            e2.setDead(true);
            parent.kill(6);
        }
        if(e3 == null) {

            e3 = new Chara();
            e3.setDead(true);
            parent.kill(7);
        }
        if(e4 == null) {

            e4 = new Chara();
            e4.setDead(true);
            parent.kill(8);
        }

        p1 = Guser.getCharaFromPosition(teams.get(team).getChar1());

        if(p1 == null) {

            p1 = new Chara();
            p1.setDead(true);
            parent.kill(1);

        } else {

            p1 = new Chara(p1);
        }

        p2 = Guser.getCharaFromPosition(teams.get(team).getChar2());

        if(p2 == null) {

            p2 = new Chara();
            p2.setDead(true);
            parent.kill(2);

        } else {

            p2 = new Chara(p2);
        }

        p3 = Guser.getCharaFromPosition(teams.get(team).getChar3());

        if(p3 == null) {

            p3 = new Chara();
            p3.setDead(true);
            parent.kill(3);

        } else {

            p3 = new Chara(p3);
        }

        p4 = Guser.getCharaFromPosition(teams.get(team).getChar4());

        if(p4 == null) {

            p4 = new Chara();
            p4.setDead(true);
            parent.kill(4);

        } else {

            p4 = new Chara(p4);
        }

        p1.setEnemy(false);
        p2.setEnemy(false);
        p3.setEnemy(false);
        p4.setEnemy(false);

        e1.setMaxHealth(e1.getHealth());
        e2.setMaxHealth(e2.getHealth());
        e3.setMaxHealth(e3.getHealth());
        e4.setMaxHealth(e4.getHealth());

        p1.setMaxHealth(p1.getHealth());
        p2.setMaxHealth(p2.getHealth());
        p3.setMaxHealth(p3.getHealth());
        p4.setMaxHealth(p4.getHealth());

        List<Chara> charas = getAll();

        for(int i = 0; i < charas.size(); i++) {

            Chara c = charas.get(i);

            if(c.isDead())
                continue;

            for(int j = 1; j <= 4; j++) {

                c.getAction(j).setCooldown(0);
            }
        }

        parent.updateHealthBars(getAll());
    }

    /**
     * Gets all characters into a list.
     *
     * @return the list of charas
     */
    private List<Chara> getAll() {

        List<Chara> list = new ArrayList<>();

        if(p1 != null)
            list.add(p1);
        if(p2 != null)
            list.add(p2);
        if(p3 != null)
            list.add(p3);
        if(p4 != null)
            list.add(p4);

        if(e1 != null)
            list.add(e1);
        if(e2 != null)
            list.add(e2);
        if(e3 != null)
            list.add(e3);
        if(e4 != null)
            list.add(e4);

        return list;
    }

    /**
     * Advances the turn.
     * <p>
     * First it sorts all characters based on their speed + some randomness
     * Then it will iterate over the sorted list (fastest character first) and performs their action on the its selected targets.
     */
    public void advance() {

        List<Chara> list = getAll();
        List<Chara> olist = new ArrayList<>(8);

        olist.addAll(list);

        for(int i = 0; i < list.size(); i++) {

            list.get(i).setSortSpeed(list.get(i).getSpeed() + Random.randInt(-9,9));
        }

        Collections.sort(list, new Comparator<Chara>(){

            public int compare(Chara o1, Chara o2){

                if(o1.getSortSpeed() == o2.getSortSpeed())
                    return 0;

                return o1.getSortSpeed() > o2.getSortSpeed() ? -1 : 1;
            }
        });

        for(int i = 0; i < list.size(); i++) {

            if(battleOver)
                break;

            Chara c = list.get(i);

            if(c.isDead())
                continue;

            if(c.isStunned()) {

                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString str1 = new SpannableString(c.getName());
                str1.setSpan(new ForegroundColorSpan(Color.parseColor(c.getColor())), 0, str1.length(), 0);
                builder.append(str1);

                SpannableString str = new SpannableString(" is stunned and cannot do anything this turn!");
                builder.append(str);

                parent.pushText(builder);

                c.setStunned(false);
                continue;
            }

            List<Double> damage = new ArrayList<>();
            List<Chara> target = new ArrayList<>();

            if(c.isEnemy()) {

                c.setSelectedTarget(Random.randInt(1,4));

                if(getP(c.getSelectedTarget()).isDead())
                    c.setSelectedTarget(getFreePTarget());

                c.setSelectedAction(Random.randInt(1,4));
            }

            if(c.getAction(c.getSelectedAction()).getCooldown() != 0) {

                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString str1 = new SpannableString(c.getName());
                builder.append(str1);
                builder.append("s " + c.getAction(c.getSelectedAction()).getName() + " was on cooldown, " + c.getAction(1).getName() + " is used instead");

                parent.pushText(builder);

                c.setSelectedAction(1);
            }

            if(c.getAction(c.getSelectedAction()).getTargets() == 1) {

                if(!c.isEnemy()) {

                    if(!getE(c.getSelectedTarget()).isDead())
                        target.add(getE(c.getSelectedTarget()));
                    else
                        target.add(getE(getFreeETarget()));

                } else {

                    if(!getP(c.getSelectedTarget()).isDead())
                        target.add(getP(c.getSelectedTarget()));
                    else
                        target.add(getP(getFreePTarget()));
                }

            } else {

                if(!c.isEnemy()) {

                    if(!getE(1).isDead())
                        target.add(getE(1));
                    if(!getE(2).isDead())
                        target.add(getE(2));
                    if(!getE(3).isDead())
                        target.add(getE(3));
                    if(!getE(4).isDead())
                        target.add(getE(4));

                } else {

                    if(!getP(1).isDead())
                        target.add(getP(1));
                    if(!getP(2).isDead())
                        target.add(getP(2));
                    if(!getP(3).isDead())
                        target.add(getP(3));
                    if(!getP(4).isDead())
                        target.add(getP(4));
                }
            }

            damage = c.getAction(c.getSelectedAction()).prePerform(c, target);

            for(int j = 0; j < damage.size(); j++) {

                double evade = target.get(j).getEvade();

                if(Random.randDouble(0, 100) > evade) {

                    target.get(j).setHealth(target.get(j).getHealth() - damage.get(j));

                    SpannableStringBuilder builder = new SpannableStringBuilder();

                    SpannableString str1 = new SpannableString(c.getName());
                    str1.setSpan(new ForegroundColorSpan(Color.parseColor(c.getColor())), 0, str1.length(), 0);
                    builder.append(str1);

                    SpannableString str = new SpannableString("s " + c.getAction(c.getSelectedAction()).getName() + " dealt " + Math.round(damage.get(j)) + " dmg to ");
                    builder.append(str);

                    SpannableString str2 = new SpannableString(target.get(j).getName());
                    str2.setSpan(new ForegroundColorSpan(Color.parseColor(target.get(j).getColor())), 0, str2.length(), 0);
                    builder.append(str2);

                    SpannableString str3 = new SpannableString("(" + c.getSelectedTarget() + ")");
                    builder.append(str3);

                    if(target.get(j).isStunned()) {

                        builder.append("\n");
                        builder.append(str2);
                        builder.append(" has been stunned!");
                    }

                    parent.pushText(builder);

                } else {

                    SpannableStringBuilder builder = new SpannableStringBuilder();

                    SpannableString str1 = new SpannableString(target.get(j).getName());
                    str1.setSpan(new ForegroundColorSpan(Color.parseColor(target.get(j).getColor())), 0, str1.length(), 0);
                    builder.append(str1);

                    SpannableString str = new SpannableString("(" + c.getSelectedTarget() + ")" + " evaded ");
                    builder.append(str);

                    SpannableString str2 = new SpannableString(c.getName());
                    str2.setSpan(new ForegroundColorSpan(Color.parseColor(c.getColor())), 0, str2.length(), 0);
                    builder.append(str2);

                    SpannableString str3 = new SpannableString("s attack and took no damage!");
                    builder.append(str3);

                    if(target.get(j).isStunned())
                        target.get(j).setStunned(false);

                    parent.pushText(builder);
                }
            }

            target.clear();
            damage.clear();

            if(!battleOver)
                checkStatuses();
        }

        for(int i = 0; i < olist.size(); i++) {

            Chara c = olist.get(i);

            if(c.isDead())
                continue;

            for(int j = 1; j <= 4; j++) {

                if(c.getAction(j).getCooldown() > 0)
                    c.getAction(j).setCooldown(c.getAction(j).getCooldown() - 1);
            }
        }

        parent.updateHealthBars(olist);
    }

    /**
     * Checks statuses of all charas, whether theyre dead or not and whether the game is over now or not.
     */
    public void checkStatuses() {

        List<Chara> list = getAll();

        for(int i = 0; i < list.size(); i++) {

            Chara c = list.get(i);

            if(c.getHealth() <= 0 && !c.isDead()) {

                c.setDead(true);

                parent.kill(i + 1);

                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString str1 = new SpannableString(c.getName());
                str1.setSpan(new ForegroundColorSpan(Color.parseColor(c.getColor())), 0, str1.length(), 0);
                builder.append(str1);

                builder.append(" has been defeated!");

                parent.pushText(builder);
            }
        }

        if(getP(1).isDead() && getP(2).isDead() && getP(3).isDead() && getP(4).isDead()) {

            lose();
        }

        if(getE(1).isDead() && getE(2).isDead() && getE(3).isDead() && getE(4).isDead()) {

            win();
        }
    }

    /**
     * Loses the game.
     */
    private void lose() {

        battleOver = true;
        parent.lose();
    }

    /**
     * Wins the game.
     */
    private void win() {

        battleOver = true;
        parent.win();
    }

    /**
     * Sets selected target.
     *
     * @param selectedTarget the selected target
     */
    public void setSelectedTarget(int selectedTarget) {

        this.selectedTarget = selectedTarget;

        getP(selectedChara).setSelectedTarget(selectedTarget);
    }

    /**
     * Sets selected chara.
     *
     * @param selectedChara the selected chara
     */
    public void setSelectedChara(int selectedChara) {

        this.selectedChara = selectedChara;
    }

    /**
     * Sets selected action. Pushes the action selected text onto parent.
     *
     * @param selectedAction the selected action
     */
    public void setSelectedAction(int selectedAction) {

        this.selectedAction = selectedAction;

        getP(selectedChara).setSelectedAction(selectedAction);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str1 = new SpannableString(getP(selectedChara).getName());
        str1.setSpan(new ForegroundColorSpan(Color.parseColor(getP(selectedChara).getColor())), 0, str1.length(), 0);
        builder.append(str1);

        builder.append(" will target ");

        if(getP(selectedChara).getAction(getP(selectedChara).getSelectedAction()).getTargets() == 1) {

            SpannableString str2 = new SpannableString(getE(selectedTarget).getName());
            str2.setSpan(new ForegroundColorSpan(Color.parseColor(getE(selectedTarget).getColor())), 0, str2.length(), 0);
            builder.append(str2);

            builder.append("(" + selectedTarget + ")" + " with " + getP(selectedChara).getAction(this.selectedAction).getName());

        } else {

            builder.append("all enemies with " + getP(selectedChara).getAction(this.selectedAction).getName());
        }

        parent.pushText(builder);
    }

    /**
     * Gets a pchara with the desired slot.
     *
     * @param i the slot
     * @return the pchara
     */
    public Chara getP(int i) {

        //TODO: these are so fucking shit fuck this
        switch(i) {

            case 1:
                return p1;
            case 2:
                return p2;
            case 3:
                return p3;
            case 4:
                return p4;
            default:
                return p1;
        }
    }

    /**
     * Gets a echara with the desired slot.
     *
     * @param i the slot
     * @return the echara
     */
    public Chara getE(int i) {

        //TODO: these are so fucking shit fuck this
        switch(i) {

            case 1:
                return e1;
            case 2:
                return e2;
            case 3:
                return e3;
            case 4:
                return e4;
            default:
                return e1;
        }
    }

    /**
     * Gets the first not dead echara.
     *
     * @return echara
     */
    private int getFreeETarget() {

        if(!getE(1).isDead())
            return 1;
        if(!getE(2).isDead())
            return 2;
        if(!getE(3).isDead())
            return 3;
        if(!getE(4).isDead())
            return 4;
        else
            return -1;
    }

    /**
     * Gets the first not dead pchara.
     *
     * @return pchara
     */
    private int getFreePTarget() {

        if(!getP(1).isDead())
            return 1;
        if(!getP(2).isDead())
            return 2;
        if(!getP(3).isDead())
            return 3;
        if(!getP(4).isDead())
            return 4;
        else
            return -1;
    }

    /**
     * Gets selected target. If target is dead, takes the next free one.
     *
     * @param selectedChara the selected chara
     * @return the selected target
     */
    public int getSelectedTarget(int selectedChara) {

        if(selectedChara == 0 || selectedChara == -1)
            selectedChara = 1;

        if(getP(selectedChara).getAction(getP(selectedChara).getSelectedAction()).getTargets() == 1)
        {
            if(!getE(getP(selectedChara).getSelectedTarget()).isDead())
                return getP(selectedChara).getSelectedTarget();
            else {

                getP(selectedChara).setSelectedTarget(getFreeETarget());
                return getFreeETarget();
            }
        }
        else
            return -1;
    }

    /**
     * Gets selected action.
     *
     * @param selectedChara the selected chara
     * @return the selected action
     */
    public int getSelectedAction(int selectedChara) {

        return getP(selectedChara).getSelectedAction();
    }

    /**
     * Push action description text.
     *
     * @param slot the slot
     */
    public void pushActionDescriptionText(int slot) {

        parent.pushText(getP(selectedChara).getAction(slot).getDescription());
    }

    /**
     * Gets cooldown.
     *
     * @param slot the slot
     * @return the cooldown
     */
    public int getCooldown(int slot) {

        return getP(selectedChara).getAction(slot).getCooldown();
    }

    /**
     * Gets targets.
     *
     * @param slot the slot
     * @return the targets
     */
    public int getTargets(int slot) {

        return getP(selectedChara).getAction(slot).getTargets();
    }
}
