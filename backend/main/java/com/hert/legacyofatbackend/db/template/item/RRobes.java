package com.hert.legacyofatbackend.db.template.item;

import javax.persistence.Entity;

/**
 * Regen robes item. Same as robes but heals 15 every turn.
 */
@Entity
public class RRobes extends Item {

    /**
     * Instantiates a new R robes.
     */
    public RRobes() {

        super();

        setName("Robes of regeneration");
        setDescription("These will regenerate 15 health every turn");

        setHealth(30);

        setDefense(5);

        setEvade(1);

        setPriority(0);
        setItemId(getClass().getSimpleName());

        setItemClass("Armor");

        setPrice(2500);

        setGraphic("Ω");
        setColor("#b0ffa8");
    }
}
