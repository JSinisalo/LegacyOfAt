package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;

import java.util.List;

/**
 * Class for the evade action. boosts own evade
 */
public class Evade extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        source.setEvade(source.getEvade() + 5);

        return 0;
    }
}