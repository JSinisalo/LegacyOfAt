package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the lullaby action. Mild dmg and stun to everyone.
 */
@Entity
public class Lullaby extends Action {

    /**
     * Instantiates a new Lullaby.
     */
    public Lullaby() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("lullaby");
        setDescription("Little damage, but stuns all enemies\n4 turn cooldown");

        setTargets(4);

        setCooldown(4);
    }
}