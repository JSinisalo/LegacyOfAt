package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the time skip action. No damage but sets all cooldowns of the chara to 0.
 */
@Entity
public class TSkip extends Action {

    /**
     * Instantiates a new T skip.
     */
    public TSkip() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("time skip");
        setDescription("No damage, stuns itself, but resets own cooldowns\n8 turn cooldown");

        setTargets(1);

        setCooldown(8);
    }
}