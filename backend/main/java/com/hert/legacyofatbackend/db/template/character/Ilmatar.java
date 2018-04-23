package com.hert.legacyofatbackend.db.template.character;

import com.hert.legacyofatbackend.db.template.action.*;

import javax.persistence.Entity;

/**
 * Ilmatar chara. A hugely overpowered character with massive damage and stuns and self heals.
 */
@Entity
public class Ilmatar extends Chara {

    /**
     * Instantiates a new Ilmatar.
     */
    public Ilmatar() {

        super();

        setName("Ilmatar");
        setDescription("Female spirit of air; the daughter of primeval substance of creative spirit, mother of Väinämöinen");

        setGraphic("I");
        setColor("#00FF80");

        setRarity(4);

        setHealth(600);
        setSpecial(100);

        setAttack(120);
        setDefense(44);

        setEvade(8);
        setSpeed(12);

        setAction1(new PWind());
        setAction2(new WStorm());
        setAction3(new Hurricane());
        setAction4(new LSteal());
    }
}
