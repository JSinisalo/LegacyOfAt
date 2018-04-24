package com.hert.legacyofat.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Guser of backend, this static class holds all the data from the backend. Since this data is basically used everywhere I decided to make it a big static class, not sure if its the best idea.
 */
public abstract class Guser {

    private static String token;

    private static String name;

    private static Integer loginAmount;

    private static Integer jims;

    private static Integer rank;

    private static Integer gold;

    private static List<Chara> charas = new ArrayList<>();

    private static List<Team> teams = new ArrayList<>();

    private static List<Item> items = new ArrayList<>();

    private static List<Item> shop = new ArrayList<>();

    private static List<Battle> battles = new ArrayList<>();

    private static JSONObject json;

    /**
     * Checks whether an item is already in use by another chara.
     *
     * @param item the item to check
     * @return the position of the character that uses it, or -1 if no users
     */
    public static int isItemInUse(Item item) {

        for(int i = 0; i < charas.size(); i++) {

            if(charas.get(i).getArmor() == item || charas.get(i).getWeapon() == item || charas.get(i).getTrinket1() == item)
                return i;
        }

        return -1;
    }

    /**
     * Is the desired team full or not.
     *
     * @param team the team
     * @return the boolean
     */
    public static boolean isFullTeam(int team) {

        for(int i = 1; i <= 4; i++) {

            if(teams.get(team).charaExists(1) == -1)
                return false;
        }

        return true;
    }

    /**
     * Gets the difference of charas between the given data and the current data the Guser holds.
     *
     * @param str the data
     * @return the chara difference
     */
    public static List<Chara> getCharaDifference(String str) {

        List<Chara> ch = new ArrayList<>();

        try {

            json = new JSONObject(str);

            List<Chara> result = JSONToCharas(json.getJSONArray("charas"));

            if(result.size() <= charas.size())
                return ch;

            for(int i = charas.size(); i < result.size(); i++) {

                ch.add(result.get(i));
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return ch;
    }

    /**
     * Gets the difference of items between the given data and the current data the Guser holds.
     *
     * @param str the data
     * @return the item difference
     */
    public static List<Item> getItemDifference(String str) {

        List<Item> ch = new ArrayList<>();

        try {

            json = new JSONObject(str);

            List<Item> result = JSONToItems(json.getJSONArray("items"));

            if(result.size() <= items.size())
                return ch;

            for(int i = items.size(); i < result.size(); i++) {

                ch.add(result.get(i));
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return ch;
    }

    /**
     * Gets the difference of gold between the given data and the current data the Guser holds.
     *
     * @param str the data
     * @return the gold difference
     */
    public static int getGoldDifference(String str) {

        int ch = 0;

        try {

            json = new JSONObject(str);

            ch = json.getInt("gold") - gold;

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return ch;
    }

    /**
     * Sets all the data the Guser holds from the given data.
     *
     * @param str the data
     */
    public static void setFullData(String str) {

        charas.clear();
        teams.clear();
        items.clear();

        try {

            json = new JSONObject(str);

            setName(json.getString("name"));
            setJims(json.getInt("jims"));
            setLoginAmount(json.getInt("loginAmount"));
            setRank(json.getInt("rank"));
            setTeams(JSONToTeams(json.getJSONArray("teams")));
            setGold(json.getInt("gold"));
            setItems(JSONToItems(json.getJSONArray("items")));
            setCharas(JSONToCharas(json.getJSONArray("charas")));
            setShop(JSONToItems(json.getJSONArray("shop")));
            setBattles(JSONToBattles(json.getJSONArray("battles")));

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    /**
     * Gets the chara object from the position in the chara list.
     *
     * @param chara the chara
     * @return the chara from position
     */
    public static Chara getCharaFromPosition(int chara) {

        if(chara == -1)
            return null;

        return charas.get(chara);
    }

    /**
     * Gets graphic from from the position in the chara list.
     *
     * @param chara the chara
     * @return the graphic from position
     */
    public static String getGraphicFromPosition(int chara) {

        if(chara == -1)
            return "";

        return charas.get(chara).getGraphic();
    }

    /**
     * Gets color from the position in the chara list.
     *
     * @param chara the chara
     * @return the color from position
     */
    public static String getColorFromPosition(int chara) {

        if(chara == -1)
            return "#000000";

        return charas.get(chara).getColor();
    }

    /**
     * Gets a json string of the team desired. (for backend)
     *
     * @param team the team
     * @return the team string
     */
    public static String getTeamString(int team) {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = null;

        try {

            json = ow.writeValueAsString(teams.get(team));

        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }

        return json;
    }

    /**
     * Gets a json string of the items on a character. (for backend)
     *
     * @param chara the chara
     * @return the item string
     */
    public static String getItemString(int chara) {

        Chara c = getCharaFromPosition(chara);

        int armor = 0;
        int weapon = 0;
        int trinket = 0;

        if(c.getArmor() != null)
            armor = c.getArmor().getId();

        if(c.getWeapon() != null)
            weapon = c.getWeapon().getId();

        if(c.getTrinket1() != null)
            trinket = c.getTrinket1().getId();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = null;

        try {

            json = ow.writeValueAsString(new ItemPayload(armor,weapon,trinket,chara));

        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }

        return json;
    }

    /**
     * Gets the name of the desired item. (for backend)
     *
     * @param item the item
     * @return the buy string
     */
    public static String getBuyString(int item) {

        Item i = shop.get(item);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = null;

        try {

            json = ow.writeValueAsString(i.getItemId());

        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }

        return json;
    }

    /**
     * Turns a json object into a character
     *
     * @param json  the json
     * @param chara the chara
     * @return the chara
     */
    public static Chara JSONToChara(JSONObject json, String chara) {

        Chara c = new Chara();
        JSONObject o = new JSONObject();

        try {

            o = json.getJSONObject(chara);

        } catch (JSONException e) {

            return null;
        }

        return JSONToChara(o);
    }

    /**
     * Turns a json object into a character
     *
     * @param o the o
     * @return the chara
     */
    public static Chara JSONToChara(JSONObject o) {

        Chara c = new Chara();

        try {

            c.setName(o.getString("name"));
            c.setDescription(o.getString("description"));

            c.setRarity(o.getInt("rarity"));
            c.setMaxRarity(o.getInt("maxRarity"));
            c.setLevel(o.getInt("level"));
            c.setMaxLevel(o.getInt("maxLevel"));

            c.setXp(o.getDouble("xp"));
            c.setMaxHealth(o.getDouble("health"));
            c.setHealth(o.getDouble("health"));
            c.setSpecial(o.getDouble("special"));
            c.setAttack(o.getDouble("attack"));
            c.setDefense(o.getDouble("defense"));
            c.setEvade(o.getDouble("evade"));
            c.setSpeed(o.getDouble("speed"));

            if(o.getInt(("armor")) != 0)
                c.setArmor(items.get(o.getInt(("armor"))));
            else
                c.setArmor(null);

            if(o.getInt(("weapon")) != 0)
                c.setWeapon(items.get(o.getInt(("weapon"))));
            else
                c.setWeapon(null);

            if(o.getInt(("trinket1")) != 0)
                c.setTrinket1(items.get(o.getInt(("trinket1"))));
            else
                c.setTrinket1(null);

            c.setAction1(JSONToAction(o.getJSONObject("action1")));
            c.setAction2(JSONToAction(o.getJSONObject("action2")));
            c.setAction3(JSONToAction(o.getJSONObject("action3")));
            c.setAction4(JSONToAction(o.getJSONObject("action4")));

            c.setGraphic(o.getString("graphic"));
            c.setColor(o.getString("color"));

        } catch (JSONException e) {

            return null;
        }

        return c;
    }

    /**
     * Turns a json array into a list of characters
     *
     * @param jArray the array
     * @return the chara
     */
    private static List<Chara> JSONToCharas(JSONArray jArray) {

        ArrayList<Chara> list = new ArrayList<Chara>();

        if (jArray != null) {

            for (int i = 0; i < jArray.length(); i++){

                try {

                    JSONObject o = jArray.getJSONObject(i);

                    Chara c = JSONToChara(o);

                    list.add(c);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    /**
     * Turns json object into an action
     *
     * @param o json object
     * @return action
     */
    private static Action JSONToAction(JSONObject o) {

        Action a = null;

        try {

            a = (Action)createObject(o.getString("actionId"), false);

            a.setName(o.getString("name"));
            a.setDescription(o.getString("description"));

            a.setPriority(o.getInt("priority"));

            a.setTargets(o.getInt("targets"));

            a.setCooldown(0);
            a.setMaxCooldown(o.getInt("cooldown"));

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return a;
    }

    /**
     * Turns json object into a battle
     *
     * @param o json object
     * @return battle
     */
    private static Battle JSONToBattle(JSONObject o) {

        Battle b = null;

        try {

            b = new Battle();

            b.setName(o.getString("name"));
            b.setCleared(o.getBoolean("cleared"));

            JSONArray g = o.getJSONArray("graphics");
            List<String> gr = new ArrayList<>();

            for(int i = 0; i < g.length(); i++)
                gr.add((String)g.get(i));

            b.setGraphics(gr);

            JSONArray c = o.getJSONArray("colors");
            List<String> co = new ArrayList<>();

            for(int i = 0; i < c.length(); i++)
                co.add((String)c.get(i));

            b.setColors(co);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return b;
    }

    /**
     * Turns json array into a battle list
     *
     * @param jArray json object
     * @return battles
     */
    private static List<Battle> JSONToBattles(JSONArray jArray) {

        ArrayList<Battle> list = new ArrayList<>();

        if (jArray != null) {

            for (int i = 0; i < jArray.length(); i++){

                try {

                    list.add(JSONToBattle(jArray.getJSONObject(i)));

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    /**
     * Turns json object into an item
     *
     * @param o json object
     * @return item
     */
    private static Item JSONToItem(JSONObject o) {

        Item i = null;

        try {

            i = (Item)createObject(o.getString("itemId"), true);

            i.setName(o.getString("name"));
            i.setDescription(o.getString("description"));

            i.setHealth(o.getDouble("health"));
            i.setAttack(o.getDouble("attack"));
            i.setDefense(o.getDouble("defense"));
            i.setEvade(o.getDouble("evade"));
            i.setSpeed(o.getDouble("speed"));

            i.setItemClass(o.getString("itemClass"));

            i.setPrice(o.getInt("price"));

            i.setItemId(o.getString("itemId"));

            i.setGraphic(o.getString("graphic"));
            i.setColor(o.getString("color"));

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return i;
    }

    /**
     * Turns json array into an item list
     *
     * @param jArray json array
     * @return item
     */
    private static List<Item> JSONToItems(JSONArray jArray) {

        ArrayList<Item> list = new ArrayList<Item>();

        if (jArray != null) {

            for (int i = 0; i < jArray.length(); i++){

                try {

                    list.add(JSONToItem(jArray.getJSONObject(i)));
                    list.get(i).setId(i);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    /**
     * Turns json array into a list of teams
     *
     * @param jArray json array
     * @return teams
     */
    private static List<Team> JSONToTeams(JSONArray jArray) {

        ArrayList<Team> list = new ArrayList<Team>();

        if (jArray != null) {

            for (int i=0;i<jArray.length();i++){

                try {

                    JSONObject o = jArray.getJSONObject(i);
                    Team t = new Team(o.getString("name"),o.getInt("char1"),o.getInt("char2"),o.getInt("char3"),o.getInt("char4"),i);

                    list.add(t);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public static String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public static void setName(String name) { Guser.name = name; }

    /**
     * Gets charas.
     *
     * @return the charas
     */
    public static List<Chara> getCharas() {
        return charas;
    }

    /**
     * Sets charas.
     *
     * @param charas the charas
     */
    public static void setCharas(List<Chara> charas) {
        Guser.charas = charas;
    }

    /**
     * Gets login amount.
     *
     * @return the login amount
     */
    public static int getLoginAmount() {
        return loginAmount;
    }

    /**
     * Sets login amount.
     *
     * @param loginAmount the login amount
     */
    public static void setLoginAmount(Integer loginAmount) {
        Guser.loginAmount = loginAmount;
    }

    /**
     * Gets jims.
     *
     * @return the jims
     */
    public static Integer getJims() {
        return jims;
    }

    /**
     * Sets jims.
     *
     * @param jims the jims
     */
    public static void setJims(Integer jims) {
        Guser.jims = jims;
    }

    /**
     * Gets rank.
     *
     * @return the rank
     */
    public static Integer getRank() {
        return rank;
    }

    /**
     * Sets rank.
     *
     * @param rank the rank
     */
    public static void setRank(Integer rank) {
        Guser.rank = rank;
    }

    /**
     * Gets teams.
     *
     * @return the teams
     */
    public static List<Team> getTeams() {
        return teams;
    }

    /**
     * Sets teams.
     *
     * @param teams the teams
     */
    public static void setTeams(List<Team> teams) {
        Guser.teams = teams;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public static String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public static void setToken(String token) {
        Guser.token = token;
    }

    /**
     * Gets json.
     *
     * @return the json
     */
    public static JSONObject getJson() { return json; }

    /**
     * Gets gold.
     *
     * @return the gold
     */
    public static Integer getGold() {
        return gold;
    }

    /**
     * Sets gold.
     *
     * @param gold the gold
     */
    public static void setGold(Integer gold) {
        Guser.gold = gold;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public static List<Item> getItems() {
        return items;
    }

    /**
     * Sets items.
     *
     * @param items the items
     */
    public static void setItems(List<Item> items) {
        Guser.items = items;
    }

    /**
     * Gets shop.
     *
     * @return the shop
     */
    public static List<Item> getShop() {
        return shop;
    }

    /**
     * Sets shop.
     *
     * @param shop the shop
     */
    public static void setShop(List<Item> shop) {
        Guser.shop = shop;
    }

    /**
     * Gets battles.
     *
     * @return the battles
     */
    public static List<Battle> getBattles() {
        return battles;
    }

    /**
     * Sets battles.
     *
     * @param battles the battles
     */
    public static void setBattles(List<Battle> battles) {
        Guser.battles = battles;
    }


    /**
     * Creates an object with the set classname and whether its an item or an action
     *
     * @param className name of the object
     * @param item item or action
     * @return the object
     */
    private static Object createObject(String className, boolean item) {

        Object object;

        try {

            Class c;

            if(!item)
                c = Class.forName("com.hert.legacyofat.game.action." + className);
            else
                c = Class.forName("com.hert.legacyofat.game.item." + className);

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
