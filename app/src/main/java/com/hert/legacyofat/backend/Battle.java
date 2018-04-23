package com.hert.legacyofat.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for receiving battle data for the main menu from the backend.
 */
public class Battle {

    private String name;
    private boolean cleared = false;

    private List<String> graphics = new ArrayList<>();
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
