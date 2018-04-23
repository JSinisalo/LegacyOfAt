package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the voice of väinämöinen. Stuns and mild dmg to all.
 */
@Entity
public class Voice extends Action {

    /**
     * Instantiates a new Voice.
     */
    public Voice() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("voice of väinämöinen");
        setDescription("Little damage, stuns target");

        setTargets(1);

        setCooldown(0);
    }
}