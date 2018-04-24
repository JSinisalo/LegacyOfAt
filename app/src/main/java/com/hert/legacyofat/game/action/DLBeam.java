package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

import java.util.List;

/**
 * Class for the dark lunar beam action. Big damage and stun to target.
 */
public class DLBeam extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        target.setStunned(true);

        double dmg = Random.randDouble(source.getAttack() - (source.getAttack() / 8.0), source.getAttack() + (source.getAttack() / 1.0));

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}