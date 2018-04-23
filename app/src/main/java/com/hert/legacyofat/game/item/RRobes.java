package com.hert.legacyofat.game.item;

import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.backend.Item;

/**
 * Class for the regen robes item. Regens 15hp every turn.
 */
public class RRobes extends Item {

    @Override
    public double perform(Chara source, Chara target, double previous) {

        source.setHealth(source.getHealth() + 15);

        return 0;
    }
}