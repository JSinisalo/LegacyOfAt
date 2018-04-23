package com.hert.legacyofatbackend.db.template.item;

/**
 * Payload received by the application when changing items from characters to another.
 */
public class ItemPayload {

    /**
     * The Armor.
     */
    int armor;
    /**
     * The Weapon.
     */
    int weapon;
    /**
     * The Trinket 1.
     */
    int trinket1;

    /**
     * The Id.
     */
    int id;

    /**
     * Instantiates a new Item payload.
     *
     * @param armor    the armor
     * @param weapon   the weapon
     * @param trinket1 the trinket 1
     * @param id       the id
     */
    public ItemPayload(int armor, int weapon, int trinket1, int id) {
        this.armor = armor;
        this.weapon = weapon;
        this.trinket1 = trinket1;
        this.id = id;
    }

    /**
     * Gets armor.
     *
     * @return the armor
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Sets armor.
     *
     * @param armor the armor
     */
    public void setArmor(int armor) {
        this.armor = armor;
    }

    /**
     * Gets weapon.
     *
     * @return the weapon
     */
    public int getWeapon() {
        return weapon;
    }

    /**
     * Sets weapon.
     *
     * @param weapon the weapon
     */
    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    /**
     * Gets trinket 1.
     *
     * @return the trinket 1
     */
    public int getTrinket1() {
        return trinket1;
    }

    /**
     * Sets trinket 1.
     *
     * @param trinket1 the trinket 1
     */
    public void setTrinket1(int trinket1) {
        this.trinket1 = trinket1;
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
}

