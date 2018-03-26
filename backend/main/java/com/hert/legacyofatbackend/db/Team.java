package com.hert.legacyofatbackend.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;

    private int char1;
    private int char2;
    private int char3;
    private int char4;

    public Team() {
    }

    public Team(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChar1() {
        return char1;
    }

    public void setChar1(int char1) {
        this.char1 = char1;
    }

    public int getChar2() {
        return char2;
    }

    public void setChar2(int char2) {
        this.char2 = char2;
    }

    public int getChar3() {
        return char3;
    }

    public void setChar3(int char3) {
        this.char3 = char3;
    }

    public int getChar4() {
        return char4;
    }

    public void setChar4(int char4) {
        this.char4 = char4;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
