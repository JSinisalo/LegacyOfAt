package com.hert.legacyofatbackend.db.template.character;

import javax.persistence.Entity;

@Entity
public class Kuu extends Chara {

    public Kuu() {

        super();

        setName("Kuu");
        setDescription("Goddess of the moon.");

        setRarity(2);

        setHealth(1233);
        setSpecial(100);

        setAttack(200);
        setDefense(200);

        setEvade(8);
        setSpeed(10);
    }
}
