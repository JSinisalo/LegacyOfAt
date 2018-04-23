package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the hurricane action. Big damage to everoyne.
 */
@Entity
public class Hurricane extends Action {

    /**
     * Instantiates a new Hurricane.
     */
    public Hurricane() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("hurricane");
        setDescription("Insane damage to all enemies, ignores defense\n8 turn cooldown");

        setTargets(4);

        setCooldown(8);
    }
}