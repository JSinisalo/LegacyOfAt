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
 * Created by juhos on 19.3.2018.
 */

public class CallBackendTask extends GenericNetworkTask {

    public static final int CHECK_GUSER = 1;
    public static final int GET_GUSER_DATA = 2;
    public static final int GET_GUSER_ROLL = 3;
    public static final int POST_TEAM_DATA = 4;
    public static final int START_BATTLE = 5;
    public static final int START_BATTLERESULTS = 6;

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

            urlConnection = (HttpURLConnection) (new URL(strings[0]).openConnection());
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
