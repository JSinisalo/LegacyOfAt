package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the wind storm action. Stuns all targets but no dmg.
 */
@Entity
public class WStorm extends Action {

    /**
     * Instantiates a new W storm.
     */
    public WStorm() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("wind storm");
        setDescription("No damage, but stuns all enemies\n3 turn cooldown");

        setTargets(4);

        setCooldown(3);
    }
}