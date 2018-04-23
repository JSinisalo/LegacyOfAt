package com.hert.legacyofatbackend.db.template.character;

import com.hert.legacyofatbackend.db.template.action.*;

import javax.persistence.Entity;

/**
 * Kuu chara. Doesnt deal much damage apart from dark lunar beam, but has interesting skills.
 */
@Entity
public class Kuu extends Chara {

    /**
     * Instantiates a new Kuu.
     */
    public Kuu() {

        super();

        setName("Kuu");
        setDescription("Goddess of the moon");

        setGraphic("K");
        setColor("#00FFFF");

        setRarity(3);

        setHealth(500);
        setSpecial(100);

        setAttack(90);
        setDefense(30);

        setEvade(12);
        setSpeed(8);

        setAction1(new LBeam());
        setAction2(new DLBeam());
        setAction3(new LSteal());
        setAction4(new TSkip());
    }
}
