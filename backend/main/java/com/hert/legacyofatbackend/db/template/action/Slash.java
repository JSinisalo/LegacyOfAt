package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the slash action. Average dmg.
 */
@Entity
public class Slash extends Action {

    /**
     * Instantiates a new Slash.
     */
    public Slash() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("slash");
        setDescription("Average damage, normal attack");

        setTargets(1);

        setCooldown(0);
    }
}
