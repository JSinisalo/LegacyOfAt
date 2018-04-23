package com.hert.legacyofat.frag;

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
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.backend.Guser;
import com.squareup.leakcanary.RefWatcher;

/**
 * Fragment which holds the character details page.
 */
public class RosterCharacterFragment extends Fragment implements View.OnClickListener {

    /*
    TextView name;
    TextView rarity;
    TextView health;
    TextView attack;
    TextView defense;
    TextView evade;
    TextView speed;
    TextView description;
    */

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster_character, container, false);

        v.findViewById(R.id.characterDetailsCharacter).setOnClickListener(this);
        v.findViewById(R.id.characterDetailsWeapon).setOnClickListener(this);
        v.findViewById(R.id.characterDetailsTrinket1).setOnClickListener(this);
        v.findViewById(R.id.characterDetailsArmor).setOnClickListener(this);
        v.findViewById(R.id.characterDetailsUpgrade).setOnClickListener(this);

        /*
        name = v.findViewById(R.id.characterDetailsName);
        rarity = v.findViewById(R.id.characterDetailsRarity);
        health = v.findViewById(R.id.characterDetailsHealth);
        attack = v.findViewById(R.id.characterDetailsAttack);
        defense = v.findViewById(R.id.characterDetailsDefense);
        evade = v.findViewById(R.id.characterDetailsEvade);
        speed = v.findViewById(R.id.characterDetailsSpeed);
        description = v.findViewById(R.id.characterDetailsDescription);
        */

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if(getView() != null)
            updateInfo();
    }

    /**
     * Updates details info with data from Guser.
     */
    public void updateInfo() {

        View v = getView();

        TextView name = v.findViewById(R.id.characterDetailsName);

        if(((MainActivity)getActivity()).getSelectedChara() != -1 && name != null) {

            Chara character = Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara());
            
            TextView rarity = v.findViewById(R.id.characterDetailsRarity);
            TextView health = v.findViewById(R.id.characterDetailsHealth);
            TextView attack = v.findViewById(R.id.characterDetailsAttack);
            TextView defense = v.findViewById(R.id.characterDetailsDefense);
            TextView evade = v.findViewById(R.id.characterDetailsEvade);
            TextView speed = v.findViewById(R.id.characterDetailsSpeed);
            TextView description = v.findViewById(R.id.characterDetailsDescription);

            TextView armor = v.findViewById(R.id.characterDetailsArmor);
            TextView weapon = v.findViewById(R.id.characterDetailsWeapon);
            TextView trinket = v.findViewById(R.id.characterDetailsTrinket1);

            TextView big = v.findViewById(R.id.characterDetailsCharacter);

            name.setText(character.getName());

            String r = "";

            for(int i = 0; i < character.getRarity(); i++) {

                r += "*";
            }

            rarity.setText(r);
            health.setText("Health: " + Math.round(character.getHealth()));
            attack.setText("Attack: " + Math.round(character.getAttack()));
            defense.setText("Defense: " + Math.round(character.getDefense()));
            evade.setText("Evade: " + Math.round(character.getEvade()));
            speed.setText("Speed: " + Math.round(character.getSpeed()));
            description.setText(character.getDescription());

            big.setText(character.getGraphic());
            big.setTextColor(Color.parseColor(character.getColor()));

            if(character.getArmor() != null) {

                armor.setText(character.getArmor().getGraphic());
                armor.setTextColor(Color.parseColor(character.getArmor().getColor()));

            } else {

                armor.setText("X");
                armor.setTextColor(Color.parseColor("#FF0000"));
            }


            if(character.getWeapon() != null) {

                weapon.setText(character.getWeapon().getGraphic());
                weapon.setTextColor(Color.parseColor(character.getWeapon().getColor()));

            } else {

                weapon.setText("X");
                weapon.setTextColor(Color.parseColor("#FF0000"));
            }

            if(character.getTrinket1() != null) {

                trinket.setText(character.getTrinket1().getGraphic());
                trinket.setTextColor(Color.parseColor(character.getTrinket1().getColor()));

            } else {

                trinket.setText("X");
                trinket.setTextColor(Color.parseColor("#FF0000"));
            }
        }
    }

    /**
     * On click.
     *
     * @param v view
     */
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.characterDetailsUpgrade:
            case R.id.characterDetailsCharacter:

                ((RosterFragment)((RosterBridgeFragment)getParentFragment()).getParentFragment()).changePage(1);

                break;

            case R.id.characterDetailsArmor:

                ((RosterBridgeFragment)getParentFragment()).setCurrentItem(RosterBridgeFragment.ARMOR);

                ((RosterBridgeFragment)getParentFragment()).changePage(1);

                break;

            case R.id.characterDetailsTrinket1:

                ((RosterBridgeFragment)getParentFragment()).setCurrentItem(RosterBridgeFragment.TRINKET1);

                ((RosterBridgeFragment)getParentFragment()).changePage(1);

                break;

            case R.id.characterDetailsWeapon:

                ((RosterBridgeFragment)getParentFragment()).setCurrentItem(RosterBridgeFragment.WEAPON);

                ((RosterBridgeFragment)getParentFragment()).changePage(1);

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
