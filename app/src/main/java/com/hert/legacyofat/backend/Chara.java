package com.hert.legacyofat.backend;

/**
 * Class for a character, used throughout the game in various places.
 */
public class Chara {

    private int id;

    private String name;
    private String description;

    private int rarity;
    private int maxRarity;

    private int level;
    private int maxLevel;

    private double xp;
    
    private String graphic;
    private String color;

    private double health;
    private double maxHealth;
    private double special;

    private double attack;
    private double defense;

    private double evade;
    private double speed;

    private double sortSpeed;

    private Item armor = null;
    private Item weapon = null;
    private Item trinket1 = null;
    private Item trinket2 = null;

    private Action action1;
    private Action action2;
    private Action action3;
    private Action action4;

    private int selectedTarget = 1;
    private int selectedAction = 1;

    private boolean enemy = true;
    private boolean dead = false;

    private boolean stunned = false;

    /**
     * Instantiates a new Chara.
     */
    public Chara() { maxRarity = 5; maxLevel = 50; xp = 0; level = 1; }

    /**
     * Instantiates a new Chara.
     *
     * @param c the c
     */
    public Chara(Chara c) {

        //beauty
        this(c.graphic, c.color, c.name, c.description, c.rarity, c.maxRarity, c.level, c.maxLevel, c.xp, c.health, c.special, c.attack, c.defense, c.evade, c.speed, c.armor, c.weapon, c.trinket1, c.trinket2, c.action1, c.action2, c.action3, c.action4);
    }

    private Chara(String graphic, String color, String name, String description, int rarity, int maxRarity, int level, int maxLevel, double xp, double health, double special, double attack, double defense, double evade, double speed, Item armor, Item weapon, Item trinket1, Item trinket2, Action action1, Action action2, Action action3, Action action4) {

        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.maxRarity = maxRarity;
        this.level = level;
        this.maxLevel = maxLevel;
        this.xp = xp;
        this.health = health;
        this.special = special;
        this.attack = attack;
        this.defense = defense;
        this.evade = evade;
        this.speed = speed;
        this.armor = armor;
        this.weapon = weapon;
        this.trinket1 = trinket1;
        this.trinket2 = trinket2;
        this.action1 = action1;
        this.action2 = action2;
        this.action3 = action3;
        this.action4 = action4;
        this.graphic = graphic;
        this.color = color;
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
    public Item getArmor() {
        return armor;
    }

    /**
     * Sets armor.
     *
     * @param armor the armor
     */
    public void setArmor(Item armor) {

        if(armor != null) {

            if(this.armor != null)
                removeStats(this.armor);

            applyStats(armor);

        } else {

            removeStats(this.armor);
        }

        this.armor = armor;
    }

    /**
     * Gets weapon.
     *
     * @return the weapon
     */
    public Item getWeapon() {
        return weapon;
    }

    /**
     * Sets weapon.
     *
     * @param weapon the weapon
     */
    public void setWeapon(Item weapon) {

        if(weapon != null) {

            if(this.weapon != null)
                removeStats(this.weapon);

            applyStats(weapon);

        } else {

            removeStats(this.weapon);
        }

        this.weapon = weapon;
    }

    /**
     * Gets trinket 1.
     *
     * @return the trinket 1
     */
    public Item getTrinket1() {

        return trinket1;
    }

    /**
     * Sets trinket 1.
     *
     * @param trinket1 the trinket 1
     */
    public void setTrinket1(Item trinket1) {

        if(trinket1 != null) {

            if(this.trinket1 != null)
                removeStats(this.trinket1);

            applyStats(trinket1);

        } else {

            removeStats(this.trinket1);
        }

        this.trinket1 = trinket1;
    }

    /**
     * Gets trinket 2.
     *
     * @return the trinket 2
     */
    public Item getTrinket2() {
        return trinket2;
    }

    /**
     * Sets trinket 2.
     *
     * @param trinket2 the trinket 2
     */
    public void setTrinket2(Item trinket2) {
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
     * Gets the desired action.
     *
     * @param i the action to get
     * @return the action
     */
    public Action getAction(int i) {

        //TODO: FUCK FUCK FUCK
        switch(i) {

            case 1:
                return action1;
            case 2:
                return action2;
            case 3:
                return action3;
            case 4:
                return action4;
            default:
                return null;
        }
    }

    /**
     * Gets selected target.
     *
     * @return the selected target
     */
    public int getSelectedTarget() {
        return selectedTarget;
    }

    /**
     * Sets selected target.
     *
     * @param selectedTarget the selected target
     */
    public void setSelectedTarget(int selectedTarget) {
        this.selectedTarget = selectedTarget;
    }

    /**
     * Gets selected action.
     *
     * @return the selected action
     */
    public int getSelectedAction() {
        return selectedAction;
    }

    /**
     * Sets selected action.
     *
     * @param selectedAction the selected action
     */
    public void setSelectedAction(int selectedAction) {
        this.selectedAction = selectedAction;
    }

    /**
     * Gets sort speed.
     *
     * @return the sort speed
     */
    public double getSortSpeed() {
        return sortSpeed;
    }

    /**
     * Sets sort speed.
     *
     * @param sortSpeed the sort speed
     */
    public void setSortSpeed(double sortSpeed) {
        this.sortSpeed = sortSpeed;
    }

    /**
     * Is enemy boolean.
     *
     * @return the boolean
     */
    public boolean isEnemy() {
        return enemy;
    }

    /**
     * Sets enemy.
     *
     * @param enemy the enemy
     */
    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }

    /**
     * Gets max health.
     *
     * @return the max health
     */
    public double getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sets max health.
     *
     * @param maxHealth the max health
     */
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
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
     * Is dead boolean.
     *
     * @return the boolean
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Sets dead.
     *
     * @param dead the dead
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Applies the stats of the given item to the character stats.
     *
     * @param item
     */
    private void applyStats(Item item) {

        if(item == null)
            return;

        health += item.getHealth();

        attack += item.getAttack();
        defense += item.getDefense();

        evade += item.getEvade();
        speed += item.getSpeed();
    }

    /**
     * Removes the stats of the given item to the character stats.
     *
     * @param item
     */
    private void removeStats(Item item) {

        if(item == null)
            return;

        health -= item.getHealth();

        attack -= item.getAttack();
        defense -= item.getDefense();

        evade -= item.getEvade();
        speed -= item.getSpeed();
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
     * Is stunned boolean.
     *
     * @return the boolean
     */
    public boolean isStunned() {
        return stunned;
    }

    /**
     * Sets stunned.
     *
     * @param stunned the stunned
     */
    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }
}
