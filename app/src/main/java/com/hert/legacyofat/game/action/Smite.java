package com.hert.legacyofat.game.action;

import com.hert.legacyofat.backend.Action;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.misc.Random;

import java.util.List;

/**
 * Class for the smite action. more dmg the less hp you have
 */
public class Smite extends Action {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        double dmg = Random.randDouble(source.getAttack() - (source.getAttack() / 4.0), source.getAttack() + (source.getAttack() / 2.0));

        if(dmg < 0)
            dmg = 0;

        return dmg + ( 15 / (source.getHealth()/source.getMaxHealth()) );
    }
}
