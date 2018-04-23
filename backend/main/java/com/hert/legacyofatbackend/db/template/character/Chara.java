package com.hert.legacyofatbackend.db.template.character;

import com.hert.legacyofatbackend.db.Rollable;
import com.hert.legacyofatbackend.db.template.action.Action;

import javax.persistence.*;

/**
 * Class for the characters in the game.
 */
@Entity
public abstract class Chara implements Rollable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    private int rarity;

    /**
     * Not actually used.
     */
    private int maxRarity;

    /**
     * Not actually used.
     */
    private int level;
    /**
     * Not actually used.
     */
    private int maxLevel;

    /**
     * Not actually used.
     */
    private double xp;

    private double health;

    /**
     * Not actually used.
     */
    private double special;

    private double attack;
    private double defense;

    private double evade;
    private double speed;

    private int armor;
    private int weapon;
    private int trinket1;

    /**
     * Not actually used.
     */
    private int trinket2;

    private String graphic;
    private String color;

    @OneToOne(cascade=CascadeType.ALL)
    private Action action1;
    @OneToOne(cascade=CascadeType.ALL)
    private Action action2;
    @OneToOne(cascade=CascadeType.ALL)
    private Action action3;
    @OneToOne(cascade=CascadeType.ALL)
    private Action action4;

    /**
     * Instantiates a new Chara.
     */
    public Chara() { maxRarity = 5; maxLevel = 50; xp = 0; level = 1; }

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
     * Gets rarity.
     *
     * @return the rarity
     */
    public int getRarity() {
        return rarity;
    }

    /**
     * Sets rarity.
     *
     * @param rarity the rarity
     */
    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets xp.
     *
     * @return the xp
     */
    public double getXp() {
        return xp;
    }

    /**
     * Sets xp.
     *
     * @param xp the xp
     */
    public void setXp(double xp) {
        this.xp = xp;
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
     * Gets special.
     *
     * @return the special
     */
    public double getSpecial() {
        return special;
    }

    /**
     * Sets special.
     *
     * @param special the special
     */
    public void setSpecial(double special) {
        this.special = special;
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
     * Gets max rarity.
     *
     * @return the max rarity
     */
    public int getMaxRarity() {
        return maxRarity;
    }

    /**
     * Sets max rarity.
     *
     * @param maxRarity the max rarity
     */
    public void setMaxRarity(int maxRarity) {
        this.maxRarity = maxRarity;
    }

    /**
     * Gets max level.
     *
     * @return the max level
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Sets max level.
     *
     * @param maxLevel the max level
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
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
     * Gets trinket 2.
     *
     * @return the trinket 2
     */
    public int getTrinket2() {
        return trinket2;
    }

    /**
     * Sets trinket 2.
     *
     * @param trinket2 the trinket 2
     */
    public void setTrinket2(int trinket2) {
        this.trinket2 = trinket2;
    }

    /**
     * Gets action 1.
     *
     * @return the action 1
     */
    public Action getAction1() {
        return action1;
    }

    /**
     * Sets action 1.
     *
     * @param action1 the action 1
     */
    public void setAction1(Action action1) {
        this.action1 = action1;
    }

    /**
     * Gets action 2.
     *
     * @return the action 2
     */
    public Action getAction2() {
        return action2;
    }

    /**
     * Sets action 2.
     *
     * @param action2 the action 2
     */
    public void setAction2(Action action2) {
        this.action2 = action2;
    }

    /**
     * Gets action 3.
     *
     * @return the action 3
     */
    public Action getAction3() {
        return action3;
    }

    /**
     * Sets action 3.
     *
     * @param action3 the action 3
     */
    public void setAction3(Action action3) {
        this.action3 = action3;
    }

    /**
     * Gets action 4.
     *
     * @return the action 4
     */
    public Action getAction4() {
        return action4;
    }

    /**
     * Sets action 4.
     *
     * @param action4 the action 4
     */
    public void setAction4(Action action4) {
        this.action4 = action4;
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }
}
