package com.hert.legacyofatbackend.db.template.item;

import javax.persistence.Entity;

/**
 * Ring item. Mild stat boost to everything.
 */
@Entity
public class Ring extends Item {

    /**
     * Instantiates a new Ring.
     */
    public Ring() {

        super();

        setName("Ring");
        setDescription("An old ring");

        setHealth(20);

        setAttack(10);
        setDefense(10);

        setEvade(2);
        setSpeed(2);

        setPriority(0);
        setItemId(getClass().getSimpleName());

        setItemClass("Trinket");

        setPrice(2000);

        setGraphic("O");
        setColor("#FFFF00");
    }
}