package com.hert.legacyofatbackend.api;

import java.util.ArrayList;
import java.util.List;

public class Banner {

    public List<String> star2 = new ArrayList<>();
    public List<String> star3= new ArrayList<>();
    public List<String> star4 = new ArrayList<>();

    public Banner(List<String> star2, List<String> star3, List<String> star4) {

        this.star2.addAll(star2);
        this.star3.addAll(star3);
        this.star4.addAll(star4);
    }
}
