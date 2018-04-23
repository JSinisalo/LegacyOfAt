package com.hert.legacyofat.misc;

/**
 * Class for a few functions related to random numbers
 */
public class Random {

    /**
     * Gets a random integer between min and max (inclusive)
     *
     * @param min the min
     * @param max the max
     * @return the int
     */
    public static int randInt(int min, int max) {

        return min + (int)(Math.random() * ((max - min) + 1));
    }

    /**
     * Gets a random double between min and max (inclusive)
     *
     * @param min the min
     * @param max the max
     * @return the double
     */
    public static double randDouble(double min, double max) {

        return min + (Math.random() * ((max - min) + 1));
    }
}
