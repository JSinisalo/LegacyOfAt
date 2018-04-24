package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

import java.util.List;

/**
 * Class for the thrust action. Good piercing damage.
 */
public class Thrust extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        double dmg = Random.randDouble(source.getAttack() - (source.getAttack() / 8.0), source.getAttack() + (source.getAttack() / 2.0));

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}