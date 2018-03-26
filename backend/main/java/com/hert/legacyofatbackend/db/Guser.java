package com.hert.legacyofatbackend.db;

import com.hert.legacyofatbackend.db.template.character.Chara;

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

    //should never remove from the characters
    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    private List<Chara> charas = new ArrayList<>();

    //should never remove from teams either
    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    public Guser() {
    }

    public Guser(String gId) {

        this.name = "unknown";
        this.gId = gId;
        this.loginAmount = 1;

        for(int i = 0; i < 10; i++) {

            teams.add(new Team("team " + i));
        }
    }

    public void addCharacter(Chara c) {

        charas.add(c);
    }

    public void setTeam(long id, Team team) {

        //this is shit but w/e
        team.setId(teams.get((int)id).getId());

        teams.set((int)id, team);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
