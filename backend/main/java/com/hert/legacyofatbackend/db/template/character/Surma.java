package com.hert.legacyofatbackend.db.template.character;

import com.hert.legacyofatbackend.db.template.action.*;

import javax.persistence.Entity;

/**
 * Surma chara. Big damage dealer but quite frail.
 */
@Entity
public class Surma extends Chara {

    /**
     * Instantiates a new Surma.
     */
    public Surma() {

        super();

        setName("Surma");
        setDescription("The personification of a violent death");

        setGraphic("S");
        setColor("#FF0000");

        setRarity(2);

        setHealth(350);
        setSpecial(100);

        setAttack(100);
        setDefense(10);

        setEvade(2);
        setSpeed(14);

        setAction1(new Smack());
        setAction2(new BSmack());
        setAction3(new Kick());
        setAction4(new Rage());
    }
}