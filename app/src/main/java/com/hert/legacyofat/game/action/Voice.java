package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

/**
 * Class for the voice of väinämöinen. Stuns and mild dmg to all.
 */
public class Voice extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous) {

        target.setStunned(true);

        double dmg = Random.randDouble(source.getAttack() - (source.getAttack() / 2.0), source.getAttack() + (source.getAttack() / 8.0)) - (target.getDefense() / 10.0);

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}