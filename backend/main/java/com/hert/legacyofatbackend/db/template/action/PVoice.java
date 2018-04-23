package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the piercing voice action. Big piercing dmg but self stun.
 */
@Entity
public class PVoice extends Action {

    /**
     * Instantiates a new P voice.
     */
    public PVoice() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("piercing voice");
        setDescription("Insane damage, ignores defense, but stuns itself\n4 turn cooldown");

        setTargets(1);

        setCooldown(4);
    }
}