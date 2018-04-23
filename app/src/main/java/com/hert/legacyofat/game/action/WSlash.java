package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

/**
 * Class for the wide slash action. Mild dmg to all enemies.
 */
public class WSlash extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous) {

        double min = source.getAttack() - (source.getAttack() / 2.0);
        double max = min + 1;

        double dmg = Random.randDouble(min, max) - (target.getDefense() / 10.0);

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}
