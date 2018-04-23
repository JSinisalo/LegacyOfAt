package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the rattle action. Average dmg.
 */
@Entity
public class Rattle extends Action {

    /**
     * Instantiates a new Rattle.
     */
    public Rattle() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("rattle");
        setDescription("...");

        setTargets(1);

        setCooldown(0);
    }
}
