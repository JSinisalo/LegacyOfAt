package com.hert.legacyofat.frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.MainActivity;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.backend.Team;
import com.hert.legacyofat.misc.Debug;

import java.util.List;

/**
 * Created by juhos on 20.3.2018.
 */

public class RosterTeamFragment extends Fragment implements View.OnClickListener {

    private int selectedSlot = 0;

    private TextView slot1;
    private TextView slot2;
    private TextView slot3;
    private TextView slot4;

    private View slot1b;
    private View slot2b;
    private View slot3b;
    private View slot4b;

    private RosterListFragment parent;

    private MainActivity mainActivity;

    private int position;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);

        parent = (RosterListFragment)getParentFragment();
        mainActivity = ((MainActivity)getActivity());

        position = getArguments().getInt("p");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster_team, container, false);

        slot1 = (v.findViewById(R.id.slot1));
        slot2 = (v.findViewById(R.id.slot2));
        slot3 = (v.findViewById(R.id.slot3));
        slot4 = (v.findViewById(R.id.slot4));

        slot1b = (v.findViewById(R.id.slot1b));
        slot2b = (v.findViewById(R.id.slot2b));
        slot3b = (v.findViewById(R.id.slot3b));
        slot4b = (v.findViewById(R.id.slot4b));

        slot1.setOnClickListener(this);
        slot2.setOnClickListener(this);
        slot3.setOnClickListener(this);
        slot4.setOnClickListener(this);

        updateNames();

        selectedSlot = 0;
        parent.setSelectedTeamSlot(selectedSlot);

        return v;
    }

    public void updateNames() {

        List<Team> teams = Guser.getTeams();

        slot1.setText(Guser.getNameFromPosition(teams.get(position).getChar1()));
        slot2.setText(Guser.getNameFromPosition(teams.get(position).getChar2()));
        slot3.setText(Guser.getNameFromPosition(teams.get(position).getChar3()));
        slot4.setText(Guser.getNameFromPosition(teams.get(position).getChar4()));
    }

    public void clearBorders() {

        slot1b.setBackgroundColor(Color.argb(0, 0, 0, 0));
        slot2b.setBackgroundColor(Color.argb(0, 0, 0, 0));
        slot3b.setBackgroundColor(Color.argb(0, 0, 0, 0));
        slot4b.setBackgroundColor(Color.argb(0, 0, 0, 0));
    }

    public void onClick(View v) {

        clearBorders();

        boolean remove = false;

        if(mainActivity.getSelectedChara() == -1) {

            Debug.log(selectedSlot);

            switch(v.getId()) {

                case R.id.slot1:

                    if(selectedSlot != 1) {

                        selectedSlot = 1;
                        slot1b.setBackgroundColor(Color.argb(255, 0, 0, 0));
                    }
                    else
                        remove = true;

                    break;

                case R.id.slot2:

                    if(selectedSlot != 2) {

                        selectedSlot = 2;
                        slot2b.setBackgroundColor(Color.argb(255, 0, 0, 0));
                    }
                    else
                        remove = true;

                    break;

                case R.id.slot3:

                    if(selectedSlot != 3) {

                        selectedSlot = 3;
                        slot3b.setBackgroundColor(Color.argb(255, 0, 0, 0));
                    }
                    else
                        remove = true;

                    break;

                case R.id.slot4:

                    if(selectedSlot != 4) {

                        selectedSlot = 4;
                        slot4b.setBackgroundColor(Color.argb(255, 0, 0, 0));
                    }
                    else
                        remove = true;

                    break;
            }

            if(!remove) {

            } else {

                selectedSlot = 0;
            }

            parent.setSelectedTeamSlot(selectedSlot);

        } else {

            selectedSlot = 0;
            parent.setSelectedTeamSlot(selectedSlot);

            switch(v.getId()) {

                case R.id.slot1:

                    parent.putCharaToSlot(0, mainActivity.getSelectedChara());

                    break;

                case R.id.slot2:

                    parent.putCharaToSlot(1, mainActivity.getSelectedChara());

                    break;

                case R.id.slot3:

                    parent.putCharaToSlot(2, mainActivity.getSelectedChara());

                    break;

                case R.id.slot4:

                    parent.putCharaToSlot(3, mainActivity.getSelectedChara());

                    break;
            }
        }
    }
}
