package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;

import java.util.List;

/**
 * Class for the heal action. heals teammates
 */
public class Heal extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        for(Chara c : team) {

            c.setHealth(c.getHealth() + 150);
        }

        return 0;
    }
}