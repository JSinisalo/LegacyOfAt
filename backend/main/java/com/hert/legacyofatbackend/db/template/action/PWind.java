package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the piercing wind action. Average dmg and pierces.
 */
@Entity
public class PWind extends Action {

    /**
     * Instantiates a new P wind.
     */
    public PWind() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("piercing wind");
        setDescription("Average damage, ignores defense");

        setTargets(1);

        setCooldown(0);
    }
}