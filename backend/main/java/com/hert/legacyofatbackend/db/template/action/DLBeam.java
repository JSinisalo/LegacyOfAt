package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the dark lunar beam action. Big damage and stun to target.
 */
@Entity
public class DLBeam extends Action {

    /**
     * Instantiates a new Dl beam.
     */
    public DLBeam() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("dark lunar beam");
        setDescription("Massive damage and stun to target\n7 turn cooldown");

        setTargets(1);

        setCooldown(7);
    }
}