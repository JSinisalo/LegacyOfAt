package com.hert.legacyofat.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class for actions, which are things used by characters in the battle activity.
 */
public abstract class Action implements ActionPerform {

    private String name;
    private String description;

    private int cooldown;
    private int maxCooldown;

    private int targets;

    private int priority;

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
     * Called for the selected action of every character on every turn.
     * <p>
     * Will go through the sources and targets(list) items and call perform() on them
     * Lastly calls perform() on this action, which returns a list of damage values done by the action (one value per target)
     *
     * @param source the source chara
     * @param target the target charas
     * @return the list of damage values
     */
    public List<Double> prePerform(Chara source, List<Chara> target) {

        List<Double> result = new ArrayList<>();

        for(int i = 0; i < target.size(); i++) {

            List<ActionPerform> list = new ArrayList<>();

            if(target.get(i).getArmor() != null)
                list.add(target.get(i).getArmor());

            if(target.get(i).getWeapon() != null)
                list.add(target.get(i).getWeapon());

            if(target.get(i).getTrinket1() != null)
                list.add(target.get(i).getTrinket1());

            if(target.get(i).getTrinket2() != null)
                list.add(target.get(i).getTrinket2());

            if(source.getArmor() != null)
                list.add(source.getArmor());

            if(source.getWeapon() != null)
                list.add(source.getWeapon());

            if(source.getTrinket1() != null)
                list.add(source.getTrinket1());

            if(source.getTrinket2() != null)
                list.add(source.getTrinket2());

            list.add(this);

            //sort
            Collections.sort(list, new Comparator<ActionPerform>(){

                public int compare(ActionPerform o1, ActionPerform o2){

                    if(o1.getPriority() == o2.getPriority())
                        return 0;

                    return o1.getPriority() < o2.getPriority() ? -1 : 1;
                }
            });

            double previous = 0;

            for(int j = 0; j < list.size(); j++) {

                previous += list.get(j).perform(source, target.get(i), previous);
            }

            result.add(previous);
        }

        cooldown = getMaxCooldown();

        return result;
    }

    /**
     * Gets amount of targets.
     *
     * @return the targets
     */
    public int getTargets() {
        return targets;
    }

    /**
     * Sets amount of targets.
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

    /**
     * Gets max cooldown.
     *
     * @return the max cooldown
     */
    public int getMaxCooldown() {
        return maxCooldown;
    }

    /**
     * Sets max cooldown.
     *
     * @param maxCooldown the max cooldown
     */
    public void setMaxCooldown(int maxCooldown) {
        this.maxCooldown = maxCooldown;
    }
}
