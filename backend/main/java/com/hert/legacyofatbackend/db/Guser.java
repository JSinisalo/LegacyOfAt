package com.hert.legacyofatbackend.db;

import com.hert.legacyofatbackend.db.template.character.Chara;
import com.hert.legacyofatbackend.db.template.item.Item;
import com.hert.legacyofatbackend.db.template.item.Ring;
import com.hert.legacyofatbackend.db.template.item.Robes;
import com.hert.legacyofatbackend.db.template.item.Sword;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    public Guser() {
    }

    public Guser(String gId) {

        this.name = "unknown";
        this.gId = gId;
        this.loginAmount = 1;
        this.jims = 250;
        this.rank = 0;
        this.gold = 0;

        this.items.add(new Sword());
        this.items.add(new Robes());
        this.items.add(new Ring());

        for(int i = 0; i < 10; i++) {

            teams.add(new Team("team " + i));
        }
    }

    public void addItem(Item i) {

        items.add(i);
    }

    public void addItems(List<Item> i) {

        items.addAll(i);
    }

    public void addCharacter(Chara c) {

        charas.add(c);
    }

    public void addCharacters(List<Chara> c) {

        charas.addAll(c);
    }

    public void setTeam(long id, Team team) {

        //this is shit but w/e
        team.setId(teams.get((int)id).getId());

        teams.set((int)id, team);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public List<Chara> getCharas() {
        return charas;
    }

    public void setCharas(List<Chara> charas) {
        this.charas = charas;
    }

    public int getLoginAmount() {
        return loginAmount;
    }

    public void setLoginAmount(Integer loginAmount) {
        this.loginAmount = loginAmount;
    }

    public Integer getJims() {
        return jims;
    }

    public void setJims(Integer jims) {
        this.jims = jims;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
