package com.hert.legacyofatbackend.db.template.item;

import javax.persistence.Entity;

@Entity
public class Robes extends Item {

    public Robes() {

        super();

        setName("Robes");
        setDescription("Just some robes.");

        setHealth(100);

        setDefense(50);

        setEvade(2);
    }
}
