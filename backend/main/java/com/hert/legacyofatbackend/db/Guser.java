package com.hert.legacyofatbackend.db;

import com.hert.legacyofatbackend.db.template.battle.Battle;
import com.hert.legacyofatbackend.db.template.battle.battles.Battle1;
import com.hert.legacyofatbackend.db.template.battle.battles.Battle2;
import com.hert.legacyofatbackend.db.template.battle.battles.Battle3;
import com.hert.legacyofatbackend.db.template.battle.battles.Battle4;
import com.hert.legacyofatbackend.db.template.character.Chara;
import com.hert.legacyofatbackend.db.template.item.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Guser, which holds everything needed for an user of the application.
 */
@Entity
public class Guser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String gId;

    @NotNull
    private Integer loginAmount;

    @NotNull
    private Integer jims;

    @NotNull
    private Integer gold;

    @NotNull
    private Integer rank;

    //should never remove from the characters
    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    private List<Chara> charas = new ArrayList<>();

    //should never remove from teams either
    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    private List<Item> shop = new ArrayList<>();

    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    private List<Battle> battles = new ArrayList<>();

    private int cBattle;

    /**
     * Instantiates a new Guser.
     */
    public Guser() {
    }

    /**
     * Instantiates a new Guser.
     *
     * @param gId the g id
     */
    public Guser(String gId) {

        this(gId, "unknown");
    }

    /**
     * Instantiates a new Guser. Sets up all the basic data such as shop items and battles and the remove item item.
     *
     * @param gId  the g id
     * @param name the name
     */
    public Guser(String gId, String name) {

        this.name = name;
        this.gId = gId;
        this.loginAmount = 1;
        this.jims = 10000;
        this.rank = 0;
        this.gold = 100000;

        this.items.add(new Dummy());

        shop.add(new Sword());
        shop.add(new Robes());
        shop.add(new RRobes());
        shop.add(new Ring());

        for(int i = 0; i < 10; i++) {

            teams.add(new Team("team " + i));
        }

        this.battles.add(new Battle1("Skeleton forest edge"));
        this.battles.add(new Battle2("Skeleton forest path I"));
        this.battles.add(new Battle3("Skeleton forest ambush"));
        this.battles.add(new Battle4("Skeleton forest path II"));
    }

    /**
     * Checks whether a character already exists in this guser.
     *
     * @param c the chara
     * @return the boolean
     */
    public boolean charaExists(Chara c) {

        for(int i = 0; i < charas.size(); i++) {

            if(c.getClass().equals(charas.get(i).getClass()))
                return true;
        }

        return false;
    }

    /**
     * Add item.
     *
     * @param i the
     */
    public void addItem(Item i) {

        items.add(i);
    }

    /**
     * Add items.
     *
     * @param i the
     */
    public void addItems(List<Item> i) {

        items.addAll(i);
    }

    /**
     * Add character.
     *
     * @param c the c
     */
    public void addCharacter(Chara c) {

        charas.add(c);
    }

    /**
     * Add characters.
     *
     * @param c the c
     */
    public void addCharacters(List<Chara> c) {

        charas.addAll(c);
    }

    /**
     * Sets team.
     *
     * @param id   the id
     * @param team the team
     */
    public void setTeam(long id, Team team) {

        //this is shit but w/e
        team.setId(teams.get((int)id).getId());

        teams.set((int)id, team);
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
     * Gets id.
     *
     * @return the id
     */
    public String getgId() {
        return gId;
    }

    /**
     * Sets id.
     *
     * @param gId the g id
     */
    public void setgId(String gId) {
        this.gId = gId;
    }

    /**
     * Gets charas.
     *
     * @return the charas
     */
    public List<Chara> getCharas() {
        return charas;
    }

    /**
     * Sets charas.
     *
     * @param charas the charas
     */
    public void setCharas(List<Chara> charas) {
        this.charas = charas;
    }

    /**
     * Gets login amount.
     *
     * @return the login amount
     */
    public int getLoginAmount() {
        return loginAmount;
    }

    /**
     * Sets login amount.
     *
     * @param loginAmount the login amount
     */
    public void setLoginAmount(Integer loginAmount) {
        this.loginAmount = loginAmount;
    }

    /**
     * Gets jims.
     *
     * @return the jims
     */
    public Integer getJims() {
        return jims;
    }

    /**
     * Sets jims.
     *
     * @param jims the jims
     */
    public void setJims(Integer jims) {
        this.jims = jims;
    }

    /**
     * Gets rank.
     *
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Sets rank.
     *
     * @param rank the rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * Gets teams.
     *
     * @return the teams
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Sets teams.
     *
     * @param teams the teams
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    /**
     * Gets gold.
     *
     * @return the gold
     */
    public Integer getGold() {
        return gold;
    }

    /**
     * Sets gold.
     *
     * @param gold the gold
     */
    public void setGold(Integer gold) {
        this.gold = gold;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Sets items.
     *
     * @param items the items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Gets shop.
     *
     * @return the shop
     */
    public List<Item> getShop() {
        return shop;
    }

    /**
     * Sets shop.
     *
     * @param shop the shop
     */
    public void setShop(List<Item> shop) {
        this.shop = shop;
    }

    /**
     * Gets battles.
     *
     * @return the battles
     */
    public List<Battle> getBattles() {
        return battles;
    }

    /**
     * Sets battles.
     *
     * @param battles the battles
     */
    public void setBattles(List<Battle> battles) {
        this.battles = battles;
    }

    /**
     * Gets battle.
     *
     * @return the battle
     */
    public int getcBattle() {
        return cBattle;
    }

    /**
     * Sets battle.
     *
     * @param cBattle the c battle
     */
    public void setcBattle(int cBattle) {
        this.cBattle = cBattle;
    }
}
