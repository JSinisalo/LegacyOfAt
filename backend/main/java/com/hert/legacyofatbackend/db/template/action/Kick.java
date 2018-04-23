package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the kick action. Mild dmg and stun.
 */
@Entity
public class Kick extends Action {

    /**
     * Instantiates a new Kick.
     */
    public Kick() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("kick");
        setDescription("Deals mild damage, but stuns for a turn\n2 turn cooldown");

        setTargets(1);

        setCooldown(2);
    }
}
