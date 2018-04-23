package com.hert.legacyofat.activity;

/**
 * Created by juhos on 19.3.2018.
 */

/**
 * Interface for handling responses from backend calls
 */
public interface AsyncResponse {

    /**
     * Show connecting widget.
     */
    public void showConnectingWidget();

    /**
     * Hide connecting widget.
     */
    public void hideConnectingWidget();

    /**
     * Process finish.
     *
     * @param id     the id
     * @param result the result
     */
    public void processFinish(int id, String result);
}
