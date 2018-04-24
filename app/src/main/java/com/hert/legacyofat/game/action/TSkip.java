package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;

import java.util.List;

/**
 * Class for the time skip action. No damage but sets all cooldowns of the chara to 0.
 */
public class TSkip extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        source.setStunned(true);

        for(Chara c : team) {

            c.getAction1().setCooldown(0);
            c.getAction2().setCooldown(0);
            c.getAction3().setCooldown(0);
            c.getAction4().setCooldown(0);
        }

        return 0;
    }
}