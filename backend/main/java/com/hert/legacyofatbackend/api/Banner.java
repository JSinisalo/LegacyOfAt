package com.hert.legacyofatbackend.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Banner object which holds lists of strings(classnames) for their respective rarities.
 */
public class Banner {

    /**
     * 2 star charas.
     */
    public List<String> star2 = new ArrayList<>();
    /**
     * 3 star charas.
     */
    public List<String> star3 = new ArrayList<>();
    /**
     * 4 star charas.
     */
    public List<String> star4 = new ArrayList<>();
    /**
     * Items in the banner.
     */
    public List<String> item = new ArrayList<>();

    /**
     * Instantiates a new Banner.
     *
     * @param star2 the star 2 charas
     * @param star3 the star 3 charas
     * @param star4 the star 4 charas
     * @param item  the items in the banner
     */
    public Banner(List<String> star2, List<String> star3, List<String> star4, List<String> item) {

        this.star2.addAll(star2);
        this.star3.addAll(star3);
        this.star4.addAll(star4);
        this.item.addAll(item);
    }
}
