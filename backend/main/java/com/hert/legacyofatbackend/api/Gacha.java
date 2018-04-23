package com.hert.legacyofatbackend.api;

import com.hert.legacyofatbackend.db.Rollable;
import com.hert.legacyofatbackend.db.template.character.Chara;
import com.hert.legacyofatbackend.db.template.item.Item;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gacha static class which provides functions for getting a rollable from the desired banner.
 */
public class Gacha {

    private static final int STAR2 = 70;
    private static final int STAR3 = 28;
    private static final int STAR4 = 2;

    /**
     * The full banner. Contains all characters and items that can be rolled.
     */
    public static Banner fullBanner;

    /**
     * Initializes all banners. Called on spring app start.
     */
    public static void init() {

        List<String> star2 = new ArrayList<>();
        List<String> star3 = new ArrayList<>();
        List<String> star4 = new ArrayList<>();
        List<String> item = new ArrayList<>();

        star2.add("At");
        star2.add("Surma");

        star3.add("Kuu");

        star4.add("Ilmatar");
        star4.add("Vainamoinen");

        item.add("Sword");
        item.add("Ring");
        item.add("Robes");
        item.add("RRobes");

        fullBanner = new Banner(star2, star3, star4, item);
    }

    /**
     * Rolls the desired banner for a desired amount of times.
     *
     * @param banner the banner
     * @param times  the times
     * @return the list of rollables rolled.
     */
    public static List<Rollable> rollBanner(Banner banner, int times) {

        List<Rollable> charas = new ArrayList<>();

        for(int i = 0; i < times; i++) {

            if(randInt(1,100) > 65) {

                if(randInt(1, 100) <= STAR4) {

                    charas.add(pickChar(banner.star4));

                } else if(randInt(1, 100) <= STAR3) {

                    charas.add(pickChar(banner.star3));

                } else {

                    charas.add(pickChar(banner.star2));
                }

            } else {

                charas.add(pickItem(banner.item));
            }
        }

        return charas;
    }

    /**
     * Picks a random item from the list of items.
     *
     * @param items items
     * @return Item
     */
    private static Item pickItem(List<String> items) {

        Random randomizer = new Random();
        String item = items.get(randomizer.nextInt(items.size()));

        return (Item)createObject(item, true);
    }

    /**
     * Picks a random chara from the list of charas.
     *
     * @param charas charas
     * @return chara
     */
    private static Chara pickChar(List<String> charas) {

        Random randomizer = new Random();
        String chara = charas.get(randomizer.nextInt(charas.size()));

        return (Chara)createObject(chara, false);
    }

    /**
     * Returns a random int within min and max
     *
     * @param min the min
     * @param max the max
     * @return the int
     */
    public static int randInt(int min, int max) {

        return min + (int)(Math.random() * ((max - min) + 1));
    }

    /**
     * Creates an object with the desired classname.
     *
     * @param className the class name
     * @param item      whether the object is item or character.
     * @return the object
     */
    public static Object createObject(String className, boolean item) {

        Object object;

        try {

            Class c;

            if(!item)
                c = Class.forName("com.hert.legacyofatbackend.db.template.character." + className);
            else
                c = Class.forName("com.hert.legacyofatbackend.db.template.item." + className);

            Constructor<?> cons = c.getConstructor();
            object = cons.newInstance();

            return object;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
