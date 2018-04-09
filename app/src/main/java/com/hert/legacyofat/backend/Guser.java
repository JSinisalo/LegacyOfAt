package com.hert.legacyofat.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public abstract class Guser {

    private static String token;

    private static String name;

    private static Integer loginAmount;

    private static Integer jims;

    private static Integer rank;

    private static Integer gold;

    private static List<JSONObject> charas = new ArrayList<>();

    private static List<Team> teams = new ArrayList<>();

    private static List<JSONObject> items = new ArrayList<>();

    private static JSONObject json;

    public static void setFullData(String str) {

        try {

            json = new JSONObject(str);

            setName(json.getString("name"));
            setJims(json.getInt("jims"));
            setLoginAmount(json.getInt("loginAmount"));
            setRank(json.getInt("rank"));
            setCharas(JSONToCharas(json.getJSONArray("charas")));
            setTeams(JSONToTeams(json.getJSONArray("teams")));
            setGold(json.getInt("gold"));
            setItems(JSONToItems(json.getJSONArray("items")));

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    public static String getNameFromPosition(int chara) {

        if(chara == -1)
            return "";

        try {

            return charas.get(chara).getString("name");

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return "";
    }

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

    private static List<JSONObject> JSONToCharas(JSONArray jArray) {

        ArrayList<JSONObject> list = new ArrayList<JSONObject>();

        if (jArray != null) {

            for (int i=0;i<jArray.length();i++){

                try {

                    list.add(jArray.getJSONObject(i));

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    private static List<JSONObject> JSONToItems(JSONArray jArray) {

        ArrayList<JSONObject> list = new ArrayList<JSONObject>();

        if (jArray != null) {

            for (int i=0;i<jArray.length();i++){

                try {

                    list.add(jArray.getJSONObject(i));

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }

        return list;
    }

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

    public static String getName() {
        return name;
    }

    public static void setName(String name) { Guser.name = name; }

    public static List<JSONObject> getCharas() {
        return charas;
    }

    public static void setCharas(List<JSONObject> charas) {
        Guser.charas = charas;
    }

    public static int getLoginAmount() {
        return loginAmount;
    }

    public static void setLoginAmount(Integer loginAmount) {
        Guser.loginAmount = loginAmount;
    }

    public static Integer getJims() {
        return jims;
    }

    public static void setJims(Integer jims) {
        Guser.jims = jims;
    }

    public static Integer getRank() {
        return rank;
    }

    public static void setRank(Integer rank) {
        Guser.rank = rank;
    }

    public static List<Team> getTeams() {
        return teams;
    }

    public static void setTeams(List<Team> teams) {
        Guser.teams = teams;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Guser.token = token;
    }

    public static JSONObject getJson() { return json; }

    public static Integer getGold() {
        return gold;
    }

    public static void setGold(Integer gold) {
        Guser.gold = gold;
    }

    public static List<JSONObject> getItems() {
        return items;
    }

    public static void setItems(List<JSONObject> items) {
        Guser.items = items;
    }
}
