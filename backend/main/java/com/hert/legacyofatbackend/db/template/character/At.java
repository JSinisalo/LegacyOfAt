package com.hert.legacyofatbackend.db.template.character;

import javax.persistence.Entity;

@Entity
public class At extends Chara {

    public At() {

        super();

        setName("At");
        setDescription("You.");

        setRarity(1);

        setHealth(900);
        setSpecial(100);

        setAttack(150);
        setDefense(100);

        setEvade(10);
        setSpeed(10);
    }
}
