package com.hert.legacyofatbackend.db.template.item;

import javax.persistence.Entity;

/**
 * Robes item. Mild health and defense boost.
 */
@Entity
public class Robes extends Item {

    /**
     * Instantiates a new Robes.
     */
    public Robes() {

        super();

        setName("Robes");
        setDescription("Just some robes");

        setHealth(30);

        setDefense(5);

        setEvade(1);

        setPriority(0);
        setItemId(getClass().getSimpleName());

        setItemClass("Armor");

        setPrice(1000);

        setGraphic("Ω");
        setColor("#AA0000");
    }
}
