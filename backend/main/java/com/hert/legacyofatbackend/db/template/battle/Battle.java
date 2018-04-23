package com.hert.legacyofatbackend.db.template.battle;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for a battle which is the data the user is shown on the main menu screen. Backend can call for battle starts and results of the battles when required.
 */
@Entity
public abstract class Battle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;
    private boolean cleared = false;

    @ElementCollection
    private List<String> graphics = new ArrayList<>();
    @ElementCollection
    private List<String> colors = new ArrayList<>();

    /**
     * Instantiates a new Battle.
     */
    public Battle() {
    }

    /**
     * Instantiates a new Battle.
     *
     * @param name the name
     */
    public Battle(String name) {

        this.name = name;
    }

    /**
     * Start battle start.
     *
     * @return the battle start
     */
    @JsonIgnore
    public abstract BattleStart start();

    /**
     * Result battle result.
     *
     * @return the battle result
     */
    @JsonIgnore
    public abstract BattleResult result();

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
     * Gets cleared.
     *
     * @return the cleared
     */
    public boolean getCleared() {
        return cleared;
    }

    /**
     * Sets cleared.
     *
     * @param cleared the cleared
     */
    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    /**
     * Gets graphics.
     *
     * @return the graphics
     */
    public List<String> getGraphics() {
        return graphics;
    }

    /**
     * Sets graphics.
     *
     * @param graphics the graphics
     */
    public void setGraphics(List<String> graphics) {
        this.graphics = graphics;
    }

    /**
     * Gets colors.
     *
     * @return the colors
     */
    public List<String> getColors() {
        return colors;
    }

    /**
     * Sets colors.
     *
     * @param colors the colors
     */
    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
