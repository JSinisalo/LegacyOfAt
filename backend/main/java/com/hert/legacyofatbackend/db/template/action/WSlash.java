package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the wide slash action. Mild dmg to all enemies.
 */
@Entity
public class WSlash extends Action {

    /**
     * Instantiates a new W slash.
     */
    public WSlash() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("wide slash");
        setDescription("Mild damage to all enemies\n3 turn cooldown");

        setTargets(4);

        setCooldown(3);
    }
}
