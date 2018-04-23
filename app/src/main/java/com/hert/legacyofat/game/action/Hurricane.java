package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

/**
 * Class for the hurricane action. Big damage to everoyne.
 */
public class Hurricane extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous) {

        double dmg = Random.randDouble(source.getAttack() - (source.getAttack() / 10.0), source.getAttack() + (source.getAttack() * 1.5));

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}