package com.hert.legacyofat.backend;

/**
 * Class for an item, used throughout the game by characters in various places.
 */
public abstract class Item implements ActionPerform {

    private int id;

    private String name;
    private String description;

    private String itemClass;

    private double health;

    private double attack;
    private double defense;

    private double evade;
    private double speed;

    private int priority;

    private int price;

    private String itemId;

    private String graphic;
    private String color;

    /**
     * Instantiates a new Item.
     */
    public Item() {
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
     * Gets health.
     *
     * @return the health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets health.
     *
     * @param health the health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Gets attack.
     *
     * @return the attack
     */
    public double getAttack() {
        return attack;
    }

    /**
     * Sets attack.
     *
     * @param attack the attack
     */
    public void setAttack(double attack) {
        this.attack = attack;
    }

    /**
     * Gets defense.
     *
     * @return the defense
     */
    public double getDefense() {
        return defense;
    }

    /**
     * Sets defense.
     *
     * @param defense the defense
     */
    public void setDefense(double defense) {
        this.defense = defense;
    }

    /**
     * Gets evade.
     *
     * @return the evade
     */
    public double getEvade() {
        return evade;
    }

    /**
     * Sets evade.
     *
     * @param evade the evade
     */
    public void setEvade(double evade) {
        this.evade = evade;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets speed.
     *
     * @param speed the speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Gets item class.
     *
     * @return the item class
     */
    public String getItemClass() {
        return itemClass;
    }

    /**
     * Sets item class.
     *
     * @param itemClass the item class
     */
    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
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
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets item id.
     *
     * @param itemId the item id
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets graphic.
     *
     * @return the graphic
     */
    public String getGraphic() {
        return graphic;
    }

    /**
     * Sets graphic.
     *
     * @param graphic the graphic
     */
    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }
}
