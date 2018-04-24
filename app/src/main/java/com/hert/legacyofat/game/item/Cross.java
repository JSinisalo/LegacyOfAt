package com.hert.legacyofat.game.item;

import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.backend.Item;

import java.util.List;

/**
 * Class for the cross item. Regens 20hp every turn.
 */
public class Cross extends Item {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        source.setHealth(source.getHealth() + 20);

        return 0;
    }
}