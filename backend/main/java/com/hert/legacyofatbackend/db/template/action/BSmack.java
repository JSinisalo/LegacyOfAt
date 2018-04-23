package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the big smash action. Average dmg to everyone.
 */
@Entity
public class BSmack extends Action {

    /**
     * Instantiates a new B smack.
     */
    public BSmack() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("big smash");
        setDescription("Average damage to every target\n6 turn cooldown");

        setTargets(4);

        setCooldown(6);
    }
}
