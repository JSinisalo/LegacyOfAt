package com.hert.legacyofatbackend.db.template.character;

import javax.persistence.Entity;

@Entity
public class Ilmatar extends Chara {

    public Ilmatar() {

        super();

        setName("Ilmatar");
        setDescription("Female spirit of air; the daughter of primeval substance of creative spirit. Mother of Väinämöinen.");

        setRarity(3);

        setHealth(1111);
        setSpecial(100);

        setAttack(333);
        setDefense(100);

        setEvade(5);
        setSpeed(12);
    }
}
