package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

/**
 * Class for the poke action. Average piercing dmg.
 */
public class Poke extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous) {

        double dmg = Random.randDouble(source.getAttack() - (source.getAttack() / 2.0), source.getAttack() + (source.getAttack() / 2.0));

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}
