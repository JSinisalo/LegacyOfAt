package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.hert.legacyofat.backend.Guser;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONException;

/**
 * Fragment which holds the info on the main screen.
 */
public class MainFragment extends Fragment {

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        try {
            ((TextView)v.findViewById(R.id.mainFragmentTest)).setText(Guser.getJson().toString(2));
            ((TextView)v.findViewById(R.id.mainFragmentTest)).setMovementMethod(new ScrollingMovementMethod());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
