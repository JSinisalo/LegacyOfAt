package com.hert.legacyofatbackend.db.template.character;

import com.hert.legacyofatbackend.db.template.action.*;

import javax.persistence.Entity;

/**
 * At chara. The titular character of the game. Fairly average stats but quite bad actions.
 */
@Entity
public class At extends Chara {

    /**
     * Instantiates a new At.
     */
    public At() {

        super();

        setName("At");
        setDescription("The legends tell a tale of a mysterious character set to bring balance to the world... This character is you!");

        setGraphic("@");
        setColor("#FFFFFF");

        setRarity(1);

        setHealth(425);
        setSpecial(100);

        setAttack(75);
        setDefense(25);

        setEvade(5);
        setSpeed(12);

        setAction1(new Slash());
        setAction2(new WSlash());
        setAction3(new Kick());
        setAction4(new Thrust());
    }
}
