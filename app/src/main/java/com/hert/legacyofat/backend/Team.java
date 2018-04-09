package com.hert.legacyofat.backend;

public class Team {

    private long id;

    private String name;

    private int char1;
    private int char2;
    private int char3;
    private int char4;

    public Team() {
    }

    public Team(String name, int char1, int char2, int char3, int char4, long id) {

        this.name = name;
        this.char1 = char1;
        this.char2 = char2;
        this.char3 = char3;
        this.char4 = char4;
        this.id = id;
    }

    public int getChar(int i) { switch(i) {case 1: return char1; case 2: return char2; case 3: return char3; case 4: return char4;} return 0; }
    public void setChar(int i, int j) { switch(i) {case 1: char1 = j; break; case 2: char2 = j; break; case 3: char3 = j; break; case 4: char4 = j;} }

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
