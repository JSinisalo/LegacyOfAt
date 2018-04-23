package com.hert.legacyofat.backend;

/**
 * Class for a team, which is a container of 4 characters used in battles.
 */
public class Team {

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
     * @param name  the name
     * @param char1 the char 1
     * @param char2 the char 2
     * @param char3 the char 3
     * @param char4 the char 4
     * @param id    the id
     */
    public Team(String name, int char1, int char2, int char3, int char4, long id) {

        this.name = name;
        this.char1 = char1;
        this.char2 = char2;
        this.char3 = char3;
        this.char4 = char4;
        this.id = id;
    }

    /**
     * Gets char.
     *
     * @param i the
     * @return the char
     */
    public int getChar(int i) { switch(i) {case 1: return char1; case 2: return char2; case 3: return char3; case 4: return char4;} return 0; }

    /**
     * Sets char.
     *
     * @param i the
     * @param j the j
     */
    public void setChar(int i, int j) { switch(i) {case 1: char1 = j; break; case 2: char2 = j; break; case 3: char3 = j; break; case 4: char4 = j;} }

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

    /**
     * Chara exists int.
     *
     * @param chara the chara
     * @return the int
     */
    public int charaExists(int chara) {

        for(int i = 1; i <= 4; i++) {

            if(getChar(i) == chara)
                return i;
        }

        return -1;
    }
}
