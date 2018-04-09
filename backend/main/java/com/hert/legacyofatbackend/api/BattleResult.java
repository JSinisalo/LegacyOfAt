package com.hert.legacyofatbackend.api;

import java.util.Random;

public class BattleResult {

    private Integer jims;
    private Integer gold;

    public BattleResult() {

        Random random = new Random();

        jims = random.nextInt(50);
        gold = random.nextInt(200);
    }

    public Integer getJims() {
        return jims;
    }

    public void setJims(Integer jims) {
        this.jims = jims;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }
}
