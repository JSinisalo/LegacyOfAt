package com.hert.legacyofatbackend.db.template.battle;

/**
 * Class for battle results that the user receives when a battle has concluded.
 */
public class BattleResult {

    private Integer jims;
    private Integer gold;
    private Integer battle;
    private boolean rankUp = false;

    /**
     * Instantiates a new Battle result.
     *
     * @param jims the jims
     * @param gold the gold
     */
    public BattleResult(int jims, int gold) {

        this.jims = jims;
        this.gold = gold;
    }

    /**
     * Gets jims.
     *
     * @return the jims
     */
    public Integer getJims() {
        return jims;
    }

    /**
     * Sets jims.
     *
     * @param jims the jims
     */
    public void setJims(Integer jims) {
        this.jims = jims;
    }

    /**
     * Gets gold.
     *
     * @return the gold
     */
    public Integer getGold() {
        return gold;
    }

    /**
     * Sets gold.
     *
     * @param gold the gold
     */
    public void setGold(Integer gold) {
        this.gold = gold;
    }

    /**
     * Gets battle.
     *
     * @return the battle
     */
    public Integer getBattle() {
        return battle;
    }

    /**
     * Sets battle.
     *
     * @param battle the battle
     */
    public void setBattle(Integer battle) {
        this.battle = battle;
    }

    /**
     * Gets rank up.
     *
     * @return the rank up
     */
    public boolean getRankUp() {
        return rankUp;
    }

    /**
     * Sets rank up.
     *
     * @param rankUp the rank up
     */
    public void setRankUp(boolean rankUp) {
        this.rankUp = rankUp;
    }
}
