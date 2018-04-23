package com.hert.legacyofat.async;

import com.hert.legacyofat.activity.AsyncResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Easy to use async task to call to the backend and receive data.
 */
public class CallBackendTask extends GenericNetworkTask {

    /**
     * The constant CHECK_GUSER.
     */
    public static final int CHECK_GUSER = 1;
    /**
     * The constant GET_GUSER_DATA.
     */
    public static final int GET_GUSER_DATA = 2;
    /**
     * The constant GET_GUSER_ROLL.
     */
    public static final int GET_GUSER_ROLL = 3;
    /**
     * The constant POST_TEAM_DATA.
     */
    public static final int POST_TEAM_DATA = 4;
    /**
     * The constant START_BATTLE.
     */
    public static final int START_BATTLE = 5;
    /**
     * The constant START_BATTLERESULTS_WIN.
     */
    public static final int START_BATTLERESULTS_WIN = 6;
    /**
     * The constant START_BATTLERESULTS_LOSE.
     */
    public static final int START_BATTLERESULTS_LOSE = 7;
    /**
     * The constant POST_CHARA_ITEMS.
     */
    public static final int POST_CHARA_ITEMS = 8;
    /**
     * The constant BUY_ITEM.
     */
    public static final int BUY_ITEM = 9;
    /**
     * The constant SELL_ITEM.
     */
    public static final int SELL_ITEM = 10;
    /**
     * The constant POST_NAME.
     */
    public static final int POST_NAME = 11;

    /**
     * Instantiates a new Call backend task.
     *
     * @param id       the id (constants above) which can be checked once the task ends and sends back data
     * @param activity the activity that instantiated this task
     */
    public CallBackendTask(int id, AsyncResponse activity) {

        super(id, activity);
    }

    @Override
    protected String doInBackground(String... strings) {

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        OutputStream outputStream = null;
        BufferedWriter writer = null;

        HttpURLConnection urlConnection = null;
        String result = null;

        try {

            //http://91.155.202.223:7500/
            //http://legacyofat.herokuapp.com/
            urlConnection = (HttpURLConnection) (new URL("http://legacyofat.herokuapp.com/" + strings[0]).openConnection());
            urlConnection.setRequestProperty("Authorization","Bearer " + strings[1]);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            if(strings.length == 2)
                urlConnection.setRequestMethod("GET");
            else
                urlConnection.setRequestMethod("POST");

            urlConnection.setConnectTimeout(30000);
            urlConnection.connect();

            if(strings.length == 3) {

                outputStream = urlConnection.getOutputStream();
                writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(strings[2]);
                writer.close();
                outputStream.close();
            }

            inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {

                sb.append(line);
            }

            result = sb.toString();

            return result;

        } catch (UnsupportedEncodingException e) {

            id = -1;
            e.printStackTrace();

        } catch (IOException e) {

            id = -1;
            e.printStackTrace();

        } finally {

            try {

                if(bufferedReader != null)
                    bufferedReader.close();

                if(inputStream != null)
                    inputStream.close();

                if(outputStream != null)
                    outputStream.close();

                if(writer != null)
                    writer.close();

            } catch (IOException e) {

                id = -1;
                e.printStackTrace();
            }

            if(urlConnection != null)
                urlConnection.disconnect();
        }

        return result;
    }
}
