package com.hert.legacyofatbackend.db.template.enemy;

import com.hert.legacyofatbackend.db.template.action.Kick;
import com.hert.legacyofatbackend.db.template.action.Poke;
import com.hert.legacyofatbackend.db.template.action.Rattle;
import com.hert.legacyofatbackend.db.template.action.Slash;
import com.hert.legacyofatbackend.db.template.character.Chara;

import javax.persistence.Entity;

/**
 * Mugger enemy. Nothing impressive but can stun with kick.
 */
@Entity
public class Mugger extends Chara {

    /**
     * Instantiates a new Mugger.
     */
    public Mugger() {

        super();

        setName("Mugger");
        setDescription(".");

        setGraphic("m");
        setColor("#7f0000");

        setRarity(1);

        setHealth(300);
        setSpecial(100);

        setAttack(40);
        setDefense(40);

        setEvade(8);
        setSpeed(6);

        setAction1(new Slash());
        setAction2(new Slash());
        setAction3(new Slash());
        setAction4(new Kick());
    }
}
