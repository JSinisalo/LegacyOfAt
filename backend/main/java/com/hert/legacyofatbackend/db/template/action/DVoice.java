package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the draining voice action. Mild lifesteal to everyone.
 */
@Entity
public class DVoice extends Action {

    /**
     * Instantiates a new D voice.
     */
    public DVoice() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("draining voice");
        setDescription("Mild damage to all enemies and steals life from all enemies\n4 turn cooldown");

        setTargets(4);

        setCooldown(4);
    }
}