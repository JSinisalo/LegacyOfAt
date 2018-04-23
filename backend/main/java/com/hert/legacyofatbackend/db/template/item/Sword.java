package com.hert.legacyofatbackend.db.template.item;

import javax.persistence.Entity;

/**
 * Sword item. Mild offensive boosts.
 */
@Entity
public class Sword extends Item {

    /**
     * Instantiates a new Sword.
     */
    public Sword() {

        super();

        setName("Sword");
        setDescription("A fine sword");

        setHealth(0);

        setAttack(20);
        setDefense(5);

        setSpeed(1);

        setPriority(0);
        setItemId(getClass().getSimpleName());

        setItemClass("Weapon");

        setPrice(1000);

        setGraphic("†");
        setColor("#CCCCCC");
    }
}
