package com.hert.legacyofat.activity;

/**
 * Basically a copy of asyncresponse because I had other plans once but then things happened and now im not sure whether i can even remove this one.
 */
public interface FragmentResponse {

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
