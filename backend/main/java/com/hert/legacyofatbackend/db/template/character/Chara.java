package com.hert.legacyofatbackend.db.template.character;

import com.hert.legacyofatbackend.db.template.item.Item;

import javax.persistence.*;

@Entity
public abstract class Chara {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    private int rarity;
    private int maxRarity;

    private int level;
    private int maxLevel;

    private double xp;

    private double health;
    private double special;

    private double attack;
    private double defense;

    private double evade;
    private double speed;

    @OneToOne
    private Item armor;
    @OneToOne
    private Item weapon;
    @OneToOne
    private Item trinket1;
    @OneToOne
    private Item trinket2;

    public Chara() { maxRarity = 5; maxLevel = 50; xp = 0; level = 1; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getSpecial() {
        return special;
    }

    public void setSpecial(double special) {
        this.special = special;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getEvade() {
        return evade;
    }

    public void setEvade(double evade) {
        this.evade = evade;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getMaxRarity() {
        return maxRarity;
    }

    public void setMaxRarity(int maxRarity) {
        this.maxRarity = maxRarity;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Item getArmor() {
        return armor;
    }

    public void setArmor(Item armor) {
        this.armor = armor;
    }

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public Item getTrinket1() {
        return trinket1;
    }

    public void setTrinket1(Item trinket1) {
        this.trinket1 = trinket1;
    }

    public Item getTrinket2() {
        return trinket2;
    }

    public void setTrinket2(Item trinket2) {
        this.trinket2 = trinket2;
    }
}
