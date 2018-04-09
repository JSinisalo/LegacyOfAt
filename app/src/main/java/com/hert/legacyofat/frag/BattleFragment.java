package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.AsyncResponse;
import com.hert.legacyofat.activity.FragmentResponse;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.backend.Guser;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by juhos on 20.3.2018.
 */

public class BattleFragment extends Fragment implements AsyncResponse, View.OnClickListener {

    private FragmentResponse callback;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);

        callback = (FragmentResponse) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_battle, container, false);

        ((Button)v.findViewById(R.id.mockButton)).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        new CallBackendTask(CallBackendTask.START_BATTLE, this).execute("http://91.155.202.223:7500/api/battlestart", Guser.getToken());
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
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
