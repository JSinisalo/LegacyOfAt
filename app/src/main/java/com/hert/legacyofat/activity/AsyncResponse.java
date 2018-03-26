package com.hert.legacyofat.activity;

/**
 * Created by juhos on 19.3.2018.
 */

public interface AsyncResponse {

    public void showConnectingWidget();
    public void hideConnectingWidget();
    public void processFinish(int id, String result);
}
