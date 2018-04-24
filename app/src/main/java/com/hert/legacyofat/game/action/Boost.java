package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;

import java.util.List;

/**
 * Class for the boost action. boosts teammates attack.
 */
public class Boost extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        for(Chara c : team) {

            c.setAttack(c.getAttack() + 50);
        }

        return 0;
    }
}