package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.AsyncResponse;
import com.hert.legacyofat.activity.FragmentResponse;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.popup.PopupError;

/**
 * Created by juhos on 20.3.2018.
 */

public class GachaFragment extends Fragment implements AsyncResponse, PopupError.PopupErrorListener, View.OnClickListener {

    private FragmentResponse callback;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);

        callback = (FragmentResponse) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gacha, container, false);

        ((Button)v.findViewById(R.id.roll1Button)).setOnClickListener(this);
        ((Button)v.findViewById(R.id.roll10Button)).setOnClickListener(this);

        return v;
    }

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

    private void attemptRoll(int times, int jims) {

        int userJims = Guser.getJims();

        if(userJims < jims) {

            PopupError.newPopupError("Gacha roll",
                    "You need " + jims + " to roll this banner \nYou currently only own " + userJims + " jims",
                    0, false, getChildFragmentManager());

        } else {

            PopupError.newPopupError("Gacha roll",
                    "Roll banner for " + jims + " ? \nClick outside the box to dismiss",
                    (times>1) ? 2 : 1, false, getChildFragmentManager());
        }
    }

    @Override
    public void showConnectingWidget() {

        callback.showConnectingWidget();
    }

    @Override
    public void hideConnectingWidget() {

        callback.hideConnectingWidget();
    }

    @Override
    public void processFinish(int id, String result) {

        callback.processFinish(id, result);
    }

    @Override
    public void onDialogClose(DialogFragment dialog, int id) {

    }
}
