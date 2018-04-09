package com.hert.legacyofatbackend.api;

public class BattleStart {

    private String enemy;

    public BattleStart() {

        enemy = "mock enemy!";
    }

    public String getEnemy() {
        return enemy;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }
}
