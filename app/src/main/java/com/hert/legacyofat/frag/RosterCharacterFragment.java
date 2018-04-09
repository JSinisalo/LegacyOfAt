package com.hert.legacyofat.frag;

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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by juhos on 20.3.2018.
 */

public class RosterCharacterFragment extends Fragment implements View.OnClickListener {

    TextView name;
    TextView rarity;
    TextView health;
    TextView attack;
    TextView defense;
    TextView evade;
    TextView speed;
    TextView description;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster_character, container, false);

        v.findViewById(R.id.characterDetailsCharacter).setOnClickListener(this);

        name = v.findViewById(R.id.characterDetailsName);
        rarity = v.findViewById(R.id.characterDetailsRarity);
        health = v.findViewById(R.id.characterDetailsHealth);
        attack = v.findViewById(R.id.characterDetailsAttack);
        defense = v.findViewById(R.id.characterDetailsDefense);
        evade = v.findViewById(R.id.characterDetailsEvade);
        speed = v.findViewById(R.id.characterDetailsSpeed);
        description = v.findViewById(R.id.characterDetailsDescription);

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if(getView() != null)
            updateInfo();
    }

    public void updateInfo() {

        if(((MainActivity)getActivity()).getSelectedChara() != -1 && name != null) {

            JSONObject character = Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara());

            try {

                name.setText(character.getString("name") + " - " + character.getString("level"));

                String r = "";

                for(int i = 0; i < character.getInt("rarity"); i++) {

                    r += "*";
                }

                rarity.setText(r);
                health.setText("Health: " + character.getString("health"));
                attack.setText("Attack: " + character.getString("attack"));
                defense.setText("Defense: " + character.getString("defense"));
                evade.setText("Evade: " + character.getString("evade"));
                speed.setText("Speed: " + character.getString("speed"));
                description.setText(character.getString("description"));

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
    }

    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.characterDetailsCharacter:

                ((RosterFragment)getParentFragment()).changePage(1);

                break;
        }
    }
}
