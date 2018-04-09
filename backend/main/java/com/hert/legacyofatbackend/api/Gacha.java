package com.hert.legacyofatbackend.api;

import com.hert.legacyofatbackend.db.template.character.Chara;
import com.hert.legacyofatbackend.db.template.character.Ilmatar;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gacha {

    private static final int STAR2 = 70;
    private static final int STAR3 = 28;
    private static final int STAR4 = 2;

    public static Banner fullBanner;

    public static void init() {

        List<String> star2 = new ArrayList<>();
        List<String> star3 = new ArrayList<>();
        List<String> star4 = new ArrayList<>();

        star2.add("Kuu");
        star2.add("Surma");

        star3.add("Ilmatar");

        star4.add("At");

        fullBanner = new Banner(star2, star3, star4);
    }

    public static List<Chara> rollBanner(Banner banner, int times) {

        List<Chara> charas = new ArrayList<>();

        for(int i = 0; i < times; i++) {

            if(randInt(1, 100) <= STAR4) {

                charas.add(pickChar(banner.star4));

            } else if(randInt(1, 100) <= STAR3) {

                charas.add(pickChar(banner.star3));

            } else {

                charas.add(pickChar(banner.star2));
            }
        }

        return charas;
    }

    private static Chara pickChar(List<String> charas) {

        Random randomizer = new Random();
        String chara = charas.get(randomizer.nextInt(charas.size()));

        return (Chara)createObject(chara);
    }

    public static int randInt(int min, int max) {

        return min + (int)(Math.random() * ((max - min) + 1));
    }

    private static Object createObject(String className) {

        Object object;

        try {

            Class c = Class.forName("com.hert.legacyofatbackend.db.template.character." + className);
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
