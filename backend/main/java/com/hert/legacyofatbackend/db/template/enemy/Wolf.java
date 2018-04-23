package com.hert.legacyofatbackend.db.template.enemy;

import com.hert.legacyofatbackend.db.template.action.*;
import com.hert.legacyofatbackend.db.template.character.Chara;

import javax.persistence.Entity;

/**
 * Wolf enemy. Very frail but high damage for an early enemy.
 */
@Entity
public class Wolf extends Chara {

    /**
     * Instantiates a new Wolf.
     */
    public Wolf() {

        super();

        setName("Wolf");
        setDescription(".");

        setGraphic("w");
        setColor("#7f7f7f");

        setRarity(1);

        setHealth(150);
        setSpecial(100);

        setAttack(100);
        setDefense(20);

        setEvade(10);
        setSpeed(8);

        setAction1(new Slash());
        setAction2(new WSlash());
        setAction3(new Slash());
        setAction4(new Rage());
    }
}
