package com.hert.legacyofatbackend.db.template.item;

import javax.persistence.Entity;

@Entity
public class Ring extends Item {

    public Ring() {

        super();

        setName("Ring");
        setDescription("An old ring.");

        setHealth(50);

        setAttack(10);
        setDefense(10);

        setEvade(2);
        setSpeed(2);
    }
}