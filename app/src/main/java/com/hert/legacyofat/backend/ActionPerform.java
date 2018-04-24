package com.hert.legacyofat.backend;

import java.util.List;

/**
 * Interface for all objects that can perform() (Items and Actions)
 */
public interface ActionPerform {

    /**
     * Perform the action from the source onto the targets and return the damage of the perform.
     *
     * @param source   the source
     * @param target   the target
     * @param previous the previous damage value of the performs
     * @param team the sources team
     * @return the damage value
     */
    double perform(Chara source, Chara target, double previous, List<Chara> team);

    /**
     * Gets priority.
     *
     * @return the priority
     */
    int getPriority();
}
