package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the smack action. More dmg than slash.
 */
@Entity
public class Smack extends Action {

    /**
     * Instantiates a new Smack.
     */
    public Smack() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("smack");
        setDescription("Slightly higher than average damage, normal attack");

        setTargets(1);

        setCooldown(0);
    }
}
