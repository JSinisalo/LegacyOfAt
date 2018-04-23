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

import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.BattleActivity;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.backend.Team;

import java.util.List;

/**
 * Fragment used in battle activity for the actions of the selected character.
 */
public class BattleSkillsFragment extends Fragment implements View.OnClickListener {

    private int selectedSlot = -1;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_battle_skills, container, false);

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

        clearBorders();
    }

    /**
     * Updates the info on the 4 action slots with relevant info.
     *
     * @param selected the selected chara
     */
    public void updateInfo(int selected) {

        selectedSlot = -1;

        if(getView() != null && selected != -1) {

            List<Team> teams = Guser.getTeams();
            List<Chara> charas = Guser.getCharas();
            int position = ((BattleActivity)getActivity()).getTeam();

            if(teams.get(position).getChar(selected) != -1) {

                ((TextView)getView().findViewById(R.id.slot1)).setText(charas.get(teams.get(position).getChar(selected)).getAction1().getName());
                ((TextView)getView().findViewById(R.id.slot2)).setText(charas.get(teams.get(position).getChar(selected)).getAction2().getName());
                ((TextView)getView().findViewById(R.id.slot3)).setText(charas.get(teams.get(position).getChar(selected)).getAction3().getName());
                ((TextView)getView().findViewById(R.id.slot4)).setText(charas.get(teams.get(position).getChar(selected)).getAction4().getName());
            }

            highlightSlot(((BattleActivity)getActivity()).getSelectedAction(selected));
        }
    }

    /**
     * Highlights an action slot.
     *
     * @param i the slot
     */
    public void highlightSlot(int i) {

        clearBorders();

        switch(i) {

            case 1:
                getView().findViewById(R.id.slot1).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                break;
            case 2:
                getView().findViewById(R.id.slot2).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                break;
            case 3:
                getView().findViewById(R.id.slot3).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                break;
            case 4:
                getView().findViewById(R.id.slot4).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
                break;
        }

        getView().findViewById(R.id.slot1).refreshDrawableState();
        getView().findViewById(R.id.slot2).refreshDrawableState();
        getView().findViewById(R.id.slot3).refreshDrawableState();
        getView().findViewById(R.id.slot4).refreshDrawableState();
    }

    /**
     * Clear highlights.
     */
    public void clearBorders() {

        if(getView() != null) {

            getView().findViewById(R.id.slot1).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
            getView().findViewById(R.id.slot2).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
            getView().findViewById(R.id.slot3).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
            getView().findViewById(R.id.slot4).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));

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

        switch (v.getId()) {

            case R.id.slot1:

                if(((BattleActivity)getActivity()).getCooldown(1) != 0) {

                    ((BattleActivity)getActivity()).pushText("This skill is on cooldown for " + ((BattleActivity)getActivity()).getCooldown(1) + " turns");
                    return;
                }

                if(selectedSlot != 1) {

                    selectedSlot = 1;

                    highlightSlot(1);

                    ((BattleActivity)getActivity()).pushActionDescriptionText(selectedSlot, ((BattleActivity)getActivity()).getTargets(selectedSlot));

                } else {

                    ((BattleActivity)getActivity()).changePage(0);
                }

                break;

            case R.id.slot2:

                if(((BattleActivity)getActivity()).getCooldown(2) != 0) {

                    ((BattleActivity)getActivity()).pushText("This skill is on cooldown for " + ((BattleActivity)getActivity()).getCooldown(2) + " turns");
                    return;
                }

                if (selectedSlot != 2) {

                    selectedSlot = 2;

                    highlightSlot(2);

                    ((BattleActivity)getActivity()).pushActionDescriptionText(selectedSlot, ((BattleActivity)getActivity()).getTargets(selectedSlot));

                } else {

                    ((BattleActivity)getActivity()).changePage(0);
                }

                break;

            case R.id.slot3:

                if(((BattleActivity)getActivity()).getCooldown(3) != 0) {

                    ((BattleActivity)getActivity()).pushText("This skill is on cooldown for " + ((BattleActivity)getActivity()).getCooldown(3) + " turns");
                    return;
                }

                if (selectedSlot != 3) {

                    selectedSlot = 3;

                    highlightSlot(3);

                    ((BattleActivity)getActivity()).pushActionDescriptionText(selectedSlot, ((BattleActivity)getActivity()).getTargets(selectedSlot));

                } else {

                    ((BattleActivity)getActivity()).changePage(0);
                }

                break;

            case R.id.slot4:

                if(((BattleActivity)getActivity()).getCooldown(4) != 0) {

                    ((BattleActivity)getActivity()).pushText("This skill is on cooldown for " + ((BattleActivity)getActivity()).getCooldown(4) + " turns");
                    return;
                }

                if (selectedSlot != 4) {

                    selectedSlot = 4;

                    highlightSlot(4);

                    ((BattleActivity)getActivity()).pushActionDescriptionText(selectedSlot, ((BattleActivity)getActivity()).getTargets(selectedSlot));

                } else {

                    ((BattleActivity)getActivity()).changePage(0);
                }

                break;
        }

        ((BattleActivity)getActivity()).setSelectedAction(selectedSlot);
    }
}
