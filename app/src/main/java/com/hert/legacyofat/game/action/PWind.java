package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

/**
 * Class for the piercing wind action. Average dmg and pierces.
 */
public class PWind extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous) {

        double dmg = Random.randDouble(source.getAttack() - (source.getAttack() / 4.0), source.getAttack() + (source.getAttack() / 4.0));

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}