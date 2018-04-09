package com.hert.legacyofat.activity;

/**
 * Created by juhos on 20.3.2018.
 */

public interface FragmentResponse {

    public void showConnectingWidget();
    public void hideConnectingWidget();
    public void processFinish(int id, String result);
}
