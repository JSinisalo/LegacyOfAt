package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

import java.util.List;

/**
 * Class for the draining voice action. Mild lifesteal to everyone.
 */
public class DVoice extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        double min = source.getAttack() - (source.getAttack() / 2.0);
        double max = min + 1;

        double dmg = Random.randDouble(min, max) - (target.getDefense() / 10.0);

        source.setHealth(source.getHealth() + dmg);

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}