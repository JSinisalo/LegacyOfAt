package com.hert.legacyofat.frag;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.MainActivity;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.backend.Team;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

/**
 * Fragment which holds the teams of characters.
 */
public class RosterTeamFragment extends Fragment implements View.OnClickListener {

    private int selectedSlot = 0;
    
    private int position;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
        
        position = getArguments().getInt("p");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster_team, container, false);

        v.findViewById(R.id.slot1).setOnClickListener(this);
        v.findViewById(R.id.slot2).setOnClickListener(this);
        v.findViewById(R.id.slot3).setOnClickListener(this);
        v.findViewById(R.id.slot4).setOnClickListener(this);

        selectedSlot = 0;
        ((RosterListFragment)getParentFragment()).setSelectedTeamSlot(selectedSlot);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        updateNames();
    }

    /**
     * Updates the graphics on the teams.
     */
    public void updateNames() {

        if(getView() != null) {

            List<Team> teams = Guser.getTeams();

            ((TextView)getView().findViewById(R.id.slot1)).setText(Guser.getGraphicFromPosition(teams.get(position).getChar1()));
            ((TextView)getView().findViewById(R.id.slot2)).setText(Guser.getGraphicFromPosition(teams.get(position).getChar2()));
            ((TextView)getView().findViewById(R.id.slot3)).setText(Guser.getGraphicFromPosition(teams.get(position).getChar3()));
            ((TextView)getView().findViewById(R.id.slot4)).setText(Guser.getGraphicFromPosition(teams.get(position).getChar4()));

            ((TextView)getView().findViewById(R.id.slot1)).setTextColor(Color.parseColor(Guser.getColorFromPosition(teams.get(position).getChar1())));
            ((TextView)getView().findViewById(R.id.slot2)).setTextColor(Color.parseColor(Guser.getColorFromPosition(teams.get(position).getChar2())));
            ((TextView)getView().findViewById(R.id.slot3)).setTextColor(Color.parseColor(Guser.getColorFromPosition(teams.get(position).getChar3())));
            ((TextView)getView().findViewById(R.id.slot4)).setTextColor(Color.parseColor(Guser.getColorFromPosition(teams.get(position).getChar4())));

            getView().findViewById(R.id.slot1).refreshDrawableState();
            getView().findViewById(R.id.slot2).refreshDrawableState();
            getView().findViewById(R.id.slot3).refreshDrawableState();
            getView().findViewById(R.id.slot4).refreshDrawableState();
        }
    }

    /**
     * Clear highlight borders.
     */
    public void clearBorders() {

        if(getView() != null) {

            getView().findViewById(R.id.slot1).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
            getView().findViewById(R.id.slot2).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
            getView().findViewById(R.id.slot3).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
            getView().findViewById(R.id.slot4).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));

            getView().findViewById(R.id.slot1).refreshDrawableState();
            getView().findViewById(R.id.slot2).refreshDrawableState();
            getView().findViewById(R.id.slot3).refreshDrawableState();
            getView().findViewById(R.id.slot4).refreshDrawableState();
        }
    }

    /**
     * On click.
     *
     * @param v view
     */
    public void onClick(View v) {

        clearBorders();

        boolean remove = false;

        if(((MainActivity)getActivity()).getSelectedChara() == -1) {

            switch(v.getId()) {

                case R.id.slot1:

                    if(selectedSlot != 1) {

                        selectedSlot = 1;
                        getView().findViewById(R.id.slot1).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                        getView().findViewById(R.id.slot1).refreshDrawableState();
                    }
                    else
                        remove = true;

                    break;

                case R.id.slot2:

                    if(selectedSlot != 2) {

                        selectedSlot = 2;
                        getView().findViewById(R.id.slot2).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                        getView().findViewById(R.id.slot2).refreshDrawableState();
                    }
                    else
                        remove = true;

                    break;

                case R.id.slot3:

                    if(selectedSlot != 3) {

                        selectedSlot = 3;
                        getView().findViewById(R.id.slot3).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                        getView().findViewById(R.id.slot3).refreshDrawableState();
                    }
                    else
                        remove = true;

                    break;

                case R.id.slot4:

                    if(selectedSlot != 4) {

                        selectedSlot = 4;
                        getView().findViewById(R.id.slot4).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                        getView().findViewById(R.id.slot4).refreshDrawableState();
                    }
                    else
                        remove = true;

                    break;
            }

            if(!remove) {

            } else {

                selectedSlot = 0;
            }

            ((RosterListFragment)getParentFragment()).setSelectedTeamSlot(selectedSlot);

        } else {

            selectedSlot = 0;
            ((RosterListFragment)getParentFragment()).setSelectedTeamSlot(selectedSlot);

            switch(v.getId()) {

                case R.id.slot1:

                    ((RosterListFragment)getParentFragment()).putCharaToSlot(0, ((MainActivity)getActivity()).getSelectedChara());

                    break;

                case R.id.slot2:

                    ((RosterListFragment)getParentFragment()).putCharaToSlot(1, ((MainActivity)getActivity()).getSelectedChara());

                    break;

                case R.id.slot3:

                    ((RosterListFragment)getParentFragment()).putCharaToSlot(2, ((MainActivity)getActivity()).getSelectedChara());

                    break;

                case R.id.slot4:

                    ((RosterListFragment)getParentFragment()).putCharaToSlot(3, ((MainActivity)getActivity()).getSelectedChara());

                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
