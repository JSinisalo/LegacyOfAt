package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

/**
 * Class for the piercing voice action. Big piercing dmg but self stun.
 */
public class PVoice extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous) {

        source.setStunned(true);

        double dmg = Random.randDouble(source.getAttack() - (source.getAttack() / 8.0), source.getAttack() + (source.getAttack() * 2));

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}
