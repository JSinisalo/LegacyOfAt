package com.hert.legacyofatbackend.db.template.character;

import javax.persistence.Entity;

@Entity
public class Surma extends Chara {

    public Surma() {

        super();

        setName("Surma");
        setDescription("The personification of a violent death.");

        setRarity(2);

        setHealth(1040);
        setSpecial(100);

        setAttack(333);
        setDefense(80);

        setEvade(2);
        setSpeed(14);
    }
}