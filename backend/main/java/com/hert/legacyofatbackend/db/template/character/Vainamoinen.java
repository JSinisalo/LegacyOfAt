package com.hert.legacyofatbackend.db.template.character;

import com.hert.legacyofatbackend.db.template.action.*;

import javax.persistence.Entity;

/**
 * Vainamoinen chara. Another hugely overpowered character that has a 0 cooldown stun skill and other op stuff.
 */
@Entity
public class Vainamoinen extends Chara {

    /**
     * Instantiates a new Vainamoinen.
     */
    public Vainamoinen() {

        super();

        setName("Väinämöinen");
        setDescription("The old and wise man, who possesses a potent, magical voice");

        setGraphic("V");
        setColor("#7f4000");

        setRarity(4);

        setHealth(750);
        setSpecial(100);

        setAttack(110);
        setDefense(60);

        setEvade(12);
        setSpeed(12);

        setAction1(new Voice());
        setAction2(new Lullaby());
        setAction3(new DVoice());
        setAction4(new PVoice());
    }
}
