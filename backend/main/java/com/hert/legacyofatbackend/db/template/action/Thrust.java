package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the thrust action. Good piercing damage.
 */
@Entity
public class Thrust extends Action {

    /**
     * Instantiates a new Thrust.
     */
    public Thrust() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("thrust");
        setDescription("Deals huge damage, pierces defense\n3 turn cooldown");

        setTargets(1);

        setCooldown(3);
    }
}
