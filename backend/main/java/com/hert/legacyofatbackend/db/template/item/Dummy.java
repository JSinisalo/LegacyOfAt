package com.hert.legacyofatbackend.db.template.item;

import javax.persistence.Entity;

/**
 * Special item that the application turns into the button to remove an item from your character.
 */
@Entity
public class Dummy extends Item {

    /**
     * Instantiates a new Dummy.
     */
    public Dummy() {

        super();

        setName("Remove");
        setDescription(".");

        setHealth(50);

        setAttack(10);
        setDefense(10);

        setEvade(2);
        setSpeed(2);

        setPriority(0);
        setItemId("Ring");

        setItemClass("Dummy");

        setColor("#FF0000");
        setGraphic("X");
    }
}