package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the lunar beam action. Mild dmg to everyone.
 */
@Entity
public class LBeam extends Action {

    /**
     * Instantiates a new L beam.
     */
    public LBeam() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("lunar beam");
        setDescription("Little damage to all enemies, but no cooldown");

        setTargets(4);

        setCooldown(0);
    }
}