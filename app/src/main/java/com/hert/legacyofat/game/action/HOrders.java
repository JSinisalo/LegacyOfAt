package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

import java.util.List;

/**
 * Class for the holy orders action.  dmg to all enemies and 100 heal to self
 */
public class HOrders extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        source.setHealth(source.getHealth() + 100);

        double dmg = Random.randDouble(source.getAttack() - (source.getAttack() / 4.0), source.getAttack() + (source.getAttack() / 4.0)) - (target.getDefense() / 10.0);

        if(dmg < 0)
            dmg = 0;

        return dmg;
    }
}
