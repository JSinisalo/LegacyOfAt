package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the rage action. Big damage but self stun.
 */
@Entity
public class Rage extends Action {

    /**
     * Instantiates a new Rage.
     */
    public Rage() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("rage");
        setDescription("Insane damage on a target, but stuns itself\n10 turn cooldown");

        setTargets(1);

        setCooldown(10);
    }
}
