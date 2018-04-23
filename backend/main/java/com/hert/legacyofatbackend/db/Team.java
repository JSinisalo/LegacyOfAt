package com.hert.legacyofatbackend.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Team entity for storing user charas in groups of 4, that the user will use for battles.
 */
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

    /**
     * Instantiates a new Team.
     */
    public Team() {
    }

    /**
     * Instantiates a new Team.
     *
     * @param name the name
     */
    public Team(String name) {

        this.name = name;
        char1 = 0;
        char2 = -1;
        char3 = -1;
        char4 = -1;
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
     * Gets char 1.
     *
     * @return the char 1
     */
    public int getChar1() {
        return char1;
    }

    /**
     * Sets char 1.
     *
     * @param char1 the char 1
     */
    public void setChar1(int char1) {
        this.char1 = char1;
    }

    /**
     * Gets char 2.
     *
     * @return the char 2
     */
    public int getChar2() {
        return char2;
    }

    /**
     * Sets char 2.
     *
     * @param char2 the char 2
     */
    public void setChar2(int char2) {
        this.char2 = char2;
    }

    /**
     * Gets char 3.
     *
     * @return the char 3
     */
    public int getChar3() {
        return char3;
    }

    /**
     * Sets char 3.
     *
     * @param char3 the char 3
     */
    public void setChar3(int char3) {
        this.char3 = char3;
    }

    /**
     * Gets char 4.
     *
     * @return the char 4
     */
    public int getChar4() {
        return char4;
    }

    /**
     * Sets char 4.
     *
     * @param char4 the char 4
     */
    public void setChar4(int char4) {
        this.char4 = char4;
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
