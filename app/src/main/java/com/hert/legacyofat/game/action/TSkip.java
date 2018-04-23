package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;

/**
 * Class for the time skip action. No damage but sets all cooldowns of the chara to 0.
 */
public class TSkip extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous) {

        source.setStunned(true);

        source.getAction1().setCooldown(0);
        source.getAction2().setCooldown(0);
        source.getAction3().setCooldown(0);
        source.getAction4().setCooldown(0);

        return 0;
    }
}