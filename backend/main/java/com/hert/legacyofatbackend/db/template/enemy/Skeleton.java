package com.hert.legacyofatbackend.db.template.enemy;

import com.hert.legacyofatbackend.db.template.action.Poke;
import com.hert.legacyofatbackend.db.template.action.Rattle;
import com.hert.legacyofatbackend.db.template.character.Chara;

import javax.persistence.Entity;

/**
 * Skeleton enemy. The basicest of basics, fairly frail and low damage.
 */
@Entity
public class Skeleton extends Chara {

    /**
     * Instantiates a new Skeleton.
     */
    public Skeleton() {

        super();

        setName("Skeleton");
        setDescription("Rattle rattle...");

        setGraphic("Z");
        setColor("#AAAAAA");

        setRarity(1);

        setHealth(250);
        setSpecial(100);

        setAttack(50);
        setDefense(30);

        setEvade(3);
        setSpeed(4);

        setAction1(new Rattle());
        setAction2(new Rattle());
        setAction3(new Rattle());
        setAction4(new Poke());
    }
}
