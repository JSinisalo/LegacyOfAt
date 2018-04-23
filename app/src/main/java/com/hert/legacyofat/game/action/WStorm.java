package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;

/**
 * Class for the wind storm action. Stuns all targets but no dmg.
 */
public class WStorm extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous) {

        target.setStunned(true);

        return 0;
    }
}