package com.hert.legacyofatbackend.db.template.item;

import javax.persistence.Entity;

@Entity
public class Sword extends Item {

    public Sword() {

        super();

        setName("Sword");
        setDescription("A sword....");

        setHealth(0);

        setAttack(200);
        setDefense(50);

        setSpeed(1);
    }
}
