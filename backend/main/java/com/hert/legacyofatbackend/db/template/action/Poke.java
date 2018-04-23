package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the poke action. Average piercing dmg.
 */
@Entity
public class Poke extends Action {

    /**
     * Instantiates a new Poke.
     */
    public Poke() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("poke");
        setDescription("...");

        setTargets(1);

        setCooldown(0);
    }
}
