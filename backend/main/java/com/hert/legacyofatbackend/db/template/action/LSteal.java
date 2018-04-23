package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;

/**
 * Class for the life steal action. Mild dmg and heals the same for source as did damage to target.
 */
@Entity
public class LSteal extends Action {

    /**
     * Instantiates a new L steal.
     */
    public LSteal() {

        super();

        setActionId(getClass().getSimpleName());
        setPriority(0);

        setName("life steal");
        setDescription("Mild damage, but heals the damage dealt to itself\n2 turn cooldown");

        setTargets(1);

        setCooldown(2);
    }
}