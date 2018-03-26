package com.hert.legacyofat.async;

import android.os.AsyncTask;

import com.hert.legacyofat.activity.AsyncResponse;

/**
 * Created by juhos on 19.3.2018.
 */

public abstract class GenericNetworkTask extends AsyncTask<String, Integer, String> {

    protected AsyncResponse activity;
    protected int id;

    public GenericNetworkTask(int id, AsyncResponse activity) {

        this.activity = activity;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        activity.showConnectingWidget();
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);

        activity.hideConnectingWidget();
        activity.processFinish(id, result);
    }
}