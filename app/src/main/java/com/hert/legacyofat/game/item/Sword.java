package com.hert.legacyofat.game.item;

import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.backend.Item;

import java.util.List;

/**
 * Class for the sword item. No perform.
 */
public class Sword extends Item {


    @Override
    public double perform(Chara source, Chara target, double previous, List<Chara> team) {

        return 0;
    }
}
