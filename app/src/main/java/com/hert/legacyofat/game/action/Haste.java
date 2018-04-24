package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

import java.util.List;

/**
 * Class for the haste action. No damage but sets all cooldowns of the chara to 0.
 */
public class Haste extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        source.getAction1().setCooldown(0);
        source.getAction2().setCooldown(0);
        source.getAction3().setCooldown(0);
        source.getAction4().setCooldown(0);

        double min = source.getAttack() - (source.getAttack() / 2.0);
        double max = min + 1;

        double dmg = Random.randDouble(min, max) - (target.getDefense() / 10.0);

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}