package com.hert.legacyofatbackend.db.template.battle;

import com.hert.legacyofatbackend.db.template.character.Chara;

import javax.persistence.*;

/**
 * Class for battle starts that the user receives when a battle is about to start.
 */
@Entity
public class BattleStart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade= CascadeType.ALL)
    private Chara char1;
    @OneToOne(cascade= CascadeType.ALL)
    private Chara char2;
    @OneToOne(cascade= CascadeType.ALL)
    private Chara char3;
    @OneToOne(cascade= CascadeType.ALL)
    private Chara char4;

    /**
     * Instantiates a new Battle start.
     */
    public BattleStart() {
    }

    /**
     * Instantiates a new Battle start.
     *
     * @param c1 the c 1
     * @param c2 the c 2
     * @param c3 the c 3
     * @param c4 the c 4
     */
    public BattleStart(Chara c1, Chara c2, Chara c3, Chara c4) {

        char1 = c1;
        char2 = c2;
        char3 = c3;
        char4 = c4;
    }

    /**
     * Gets char 1.
     *
     * @return the char 1
     */
    public Chara getChar1() {
        return char1;
    }

    /**
     * Sets char 1.
     *
     * @param char1 the char 1
     */
    public void setChar1(Chara char1) {
        this.char1 = char1;
    }

    /**
     * Gets char 2.
     *
     * @return the char 2
     */
    public Chara getChar2() {
        return char2;
    }

    /**
     * Sets char 2.
     *
     * @param char2 the char 2
     */
    public void setChar2(Chara char2) {
        this.char2 = char2;
    }

    /**
     * Gets char 3.
     *
     * @return the char 3
     */
    public Chara getChar3() {
        return char3;
    }

    /**
     * Sets char 3.
     *
     * @param char3 the char 3
     */
    public void setChar3(Chara char3) {
        this.char3 = char3;
    }

    /**
     * Gets char 4.
     *
     * @return the char 4
     */
    public Chara getChar4() {
        return char4;
    }

    /**
     * Sets char 4.
     *
     * @param char4 the char 4
     */
    public void setChar4(Chara char4) {
        this.char4 = char4;
    }
}
