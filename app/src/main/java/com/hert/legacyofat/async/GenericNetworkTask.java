package com.hert.legacyofat.async;

import android.os.AsyncTask;

import com.hert.legacyofat.activity.AsyncResponse;

/**
 * Generic network task to maybe extend for future different tasks.
 */
public abstract class GenericNetworkTask extends AsyncTask<String, Integer, String> {

    /**
     * The Activity.
     */
    protected AsyncResponse activity;
    /**
     * The Id.
     */
    protected int id;

    /**
     * Instantiates a new Generic network task.
     *
     * @param id       the id
     * @param activity the activity
     */
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

        activity = null;
    }
}