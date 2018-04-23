package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.AsyncResponse;
import com.hert.legacyofat.activity.FragmentResponse;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.popup.PopupError;
import com.hert.legacyofat.popup.PopupYesNo;
import com.squareup.leakcanary.RefWatcher;

/**
 * Fragment that holds the gacha view with the rolling buttons and fancy banner.
 */
public class GachaFragment extends Fragment implements AsyncResponse, PopupError.PopupErrorListener, View.OnClickListener {

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gacha, container, false);

        ((Button)v.findViewById(R.id.roll1Button)).setOnClickListener(this);
        ((Button)v.findViewById(R.id.roll10Button)).setOnClickListener(this);

        return v;
    }

    /**
     * On click.
     *
     * @param v view
     */
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.roll1Button:

                attemptRoll(1, 25);

                break;

            case R.id.roll10Button:

                attemptRoll(11, 250);

                break;

            default:

                break;
        }
    }

    /**
     * Prompts the user with different popups depending on the jim situation.
     *
     * @param times amount of rolls to do
     * @param jims jims the user has
     */
    private void attemptRoll(int times, int jims) {

        int userJims = Guser.getJims();

        if(userJims < jims) {

            PopupError.newPopupError("Gacha roll",
                    "You need " + jims + " to roll this banner \nYou currently only own " + userJims + " jims",
                    0, false, getChildFragmentManager());

        } else {

            PopupYesNo.newPopupYesNo("Gacha roll",
                    "Roll banner for " + jims + " ?",
                    (times>1) ? 2 : 1, false, getChildFragmentManager());
        }
    }

    @Override
    public void showConnectingWidget() {

        ((FragmentResponse)getActivity()).showConnectingWidget();
    }

    @Override
    public void hideConnectingWidget() {

        ((FragmentResponse)getActivity()).hideConnectingWidget();
    }

    @Override
    public void processFinish(int id, String result) {

        ((FragmentResponse)getActivity()).processFinish(id, result);
    }

    @Override
    public void onDialogClose(DialogFragment dialog, int id, String extra) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
