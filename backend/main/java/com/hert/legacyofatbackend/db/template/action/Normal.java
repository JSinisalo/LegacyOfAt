package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the normal action. Average.
 */
@Entity
public class Normal extends Action {

    /**
     * Instantiates a new Normal.
     */
    public Normal() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("Normal Attack");
        setDescription("A normal attack.");

        setTargets(1);

        setCooldown(0);
    }
}
