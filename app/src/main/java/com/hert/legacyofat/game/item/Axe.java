package com.hert.legacyofat.game.item;

import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.backend.Item;

import java.util.List;

/**
 * Class for the axe item. No perform.
 */
public class Axe extends Item {

    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        return 0;
    }
}
