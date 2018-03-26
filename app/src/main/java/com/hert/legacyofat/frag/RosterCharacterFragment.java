package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.FragmentResponse;
import com.hert.legacyofat.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by juhos on 20.3.2018.
 */

public class RosterCharacterFragment extends Fragment implements View.OnClickListener {

    private FragmentResponse callback;
    private JSONObject initialData;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);

        callback = (FragmentResponse) getActivity();
        initialData = ((MainActivity)getActivity()).getInitialData();
    }

    public void onClick(View v) {

        ((RosterFragment)getParentFragment()).changePage(1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster_character, container, false);

        v.findViewById(R.id.characterDetailsTest).setOnClickListener(this);

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if(getView() != null)
            ((TextView)getView().findViewById(R.id.characterDetailsTest)).setText(getCharaData(((MainActivity)getActivity()).getSelectedChara()));
    }

    public String getCharaData(int id) {

        try {

            return initialData.getJSONArray("charas").getJSONObject(id).toString(2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
