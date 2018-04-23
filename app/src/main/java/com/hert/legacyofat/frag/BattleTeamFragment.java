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
import com.hert.legacyofat.activity.BattleActivity;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.backend.Team;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

/**
 * Fragment used in battle activity for the selected team of the user.
 */
public class BattleTeamFragment extends Fragment implements View.OnClickListener {

    private int selectedSlot = 0;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_battle_team, container, false);

        v.findViewById(R.id.slot1).setOnClickListener(this);
        v.findViewById(R.id.slot2).setOnClickListener(this);
        v.findViewById(R.id.slot3).setOnClickListener(this);
        v.findViewById(R.id.slot4).setOnClickListener(this);

        selectedSlot = 0;

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        updateNames();
        //clearBorders();
    }

    /**
     * Updates the team slot names with relevant info.
     */
    public void updateNames() {

        if(getView() != null) {

            List<Team> teams = Guser.getTeams();

            int position = ((BattleActivity)getActivity()).getTeam();

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

        //except it doesnt
        if(getView() != null && false) {

            getView().findViewById(R.id.slot1).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
            getView().findViewById(R.id.slot2).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
            getView().findViewById(R.id.slot3).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
            getView().findViewById(R.id.slot4).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));

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

        switch(v.getId()) {

            case R.id.slot1:

                if(selectedSlot != 1) {

                    selectedSlot = 1;
                    //getView().findViewById(R.id.slot1).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                    //getView().findViewById(R.id.slot1).refreshDrawableState();
                }

                break;

            case R.id.slot2:

                if(selectedSlot != 2) {

                    selectedSlot = 2;
                    //getView().findViewById(R.id.slot2).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                    //getView().findViewById(R.id.slot2).refreshDrawableState();
                }

                break;

            case R.id.slot3:

                if(selectedSlot != 3) {

                    selectedSlot = 3;
                    //getView().findViewById(R.id.slot3).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                    //getView().findViewById(R.id.slot3).refreshDrawableState();
                }

                break;

            case R.id.slot4:

                if(selectedSlot != 4) {

                    selectedSlot = 4;
                    //getView().findViewById(R.id.slot4).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                    //getView().findViewById(R.id.slot4).refreshDrawableState();
                }

                break;
        }

        ((BattleActivity)getActivity()).setSelectedSlot(selectedSlot);
        ((BattleActivity)getActivity()).changePage(1);
    }

    /**
     * Kills (hides) the desired team chara slot.
     *
     * @param slot the slot
     */
    public void kill(int slot) {

        switch(slot) {

            case 1:
                getView().findViewById(R.id.slot1).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.slot1).setOnClickListener(null);
                getView().findViewById(R.id.slot1).setClickable(false);
                break;
            case 2:
                getView().findViewById(R.id.slot2).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.slot2).setOnClickListener(null);
                getView().findViewById(R.id.slot2).setClickable(false);
                break;
            case 3:
                getView().findViewById(R.id.slot3).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.slot3).setOnClickListener(null);
                getView().findViewById(R.id.slot3).setClickable(false);
                break;
            case 4:
                getView().findViewById(R.id.slot4).setVisibility(View.INVISIBLE);
                getView().findViewById(R.id.slot4).setOnClickListener(null);
                getView().findViewById(R.id.slot4).setClickable(false);
                break;
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
