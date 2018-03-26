package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.FragmentResponse;

/**
 * Created by juhos on 20.3.2018.
 */

public class BattleFragment extends Fragment {

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

        return v;
    }
}
