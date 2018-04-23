package com.hert.legacyofatbackend.db.template.action;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class for actions that the character has and that the user can use in a battle.
 */
@Entity
public abstract class Action {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    private int priority;
    private String actionId;

    private int targets;

    private int cooldown;

    /**
     * Instantiates a new Action.
     */
    public Action() {
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets action id.
     *
     * @return the action id
     */
    public String getActionId() {
        return actionId;
    }

    /**
     * Sets action id.
     *
     * @param actionId the action id
     */
    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    /**
     * Gets priority.
     *
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets priority.
     *
     * @param priority the priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Gets targets.
     *
     * @return the targets
     */
    public int getTargets() {
        return targets;
    }

    /**
     * Sets targets.
     *
     * @param targets the targets
     */
    public void setTargets(int targets) {
        this.targets = targets;
    }

    /**
     * Gets cooldown.
     *
     * @return the cooldown
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * Sets cooldown.
     *
     * @param cooldown the cooldown
     */
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
