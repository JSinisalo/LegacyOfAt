package com.hert.legacyofat.async;

import com.hert.legacyofat.activity.AsyncResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by juhos on 19.3.2018.
 */

public class CallBackendTask extends GenericNetworkTask {

    public CallBackendTask(int id, AsyncResponse activity) {

        super(id, activity);
    }

    @Override
    protected String doInBackground(String... strings) {

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        HttpURLConnection urlConnection = null;
        String result = null;

        try {

            urlConnection = (HttpURLConnection) (new URL(strings[0]).openConnection());
            urlConnection.setRequestProperty("Authorization","Bearer " + strings[1]);
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(30000);
            urlConnection.connect();

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
