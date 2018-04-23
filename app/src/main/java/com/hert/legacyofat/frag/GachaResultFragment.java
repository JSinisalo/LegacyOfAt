package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.MainActivity;
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.backend.Item;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment which holds the results of the gacha roll.
 */
public class GachaResultFragment extends Fragment implements View.OnClickListener {

    private GachaListAdapter adapter;

    private List<String> mGraphic = new ArrayList<>();
    private List<String> mColor = new ArrayList<>();
    private List<String> mDescription = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gacha_result, container, false);

        int numberOfColumns = 1;

        ((RecyclerView)v.findViewById(R.id.resultList)).setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        adapter = new GachaListAdapter(this.getContext());
        ((RecyclerView)v.findViewById(R.id.resultList)).setAdapter(adapter);

        v.findViewById(R.id.backButton).setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((MainActivity)getActivity()).setResultFragment(this);
    }

    /**
     * Sets results of the gacha roll.
     *
     * @param result the result
     */
    public void setResults(String result) {

        mGraphic.clear();
        mColor.clear();
        mDescription.clear();

        List<Chara> charaDifference = Guser.getCharaDifference(result);
        List<Item> itemDifference = Guser.getItemDifference(result);
        int goldDifference = Guser.getGoldDifference(result);

        for(int i = 0; i < charaDifference.size(); i++) {

            String description = "You got a new character: " + charaDifference.get(i).getName() + "!\n" + charaDifference.get(i).getDescription();
            String color = charaDifference.get(i).getColor();
            String graphic = charaDifference.get(i).getGraphic();

            mGraphic.add(graphic);
            mColor.add(color);
            mDescription.add(description);
        }

        for(int i = 0; i < itemDifference.size(); i++) {

            String description = "You got a new item: " + itemDifference.get(i).getName() + "!\n" + itemDifference.get(i).getDescription();
            String color = itemDifference.get(i).getColor();
            String graphic = itemDifference.get(i).getGraphic();

            mGraphic.add(graphic);
            mColor.add(color);
            mDescription.add(description);
        }

        if(goldDifference > 0) {

            String description = "You got " + goldDifference + " gold!\n" + "Sweet gold";
            String color = "#FFFF00";
            String graphic = "$";

            mGraphic.add(graphic);
            mColor.add(color);
            mDescription.add(description);
        }

        updateData();
    }

    /**
     * Changes page.
     */
    public void changePage() {

        ((GachaMainFragment)getParentFragment()).changePage(0);
    }

    /**
     * Updates data.
     */
    public void updateData() {

        adapter.setData(mGraphic, mColor, mDescription);
        adapter.notifyDataSetChanged();
    }

    /**
     * On click.
     *
     * @param v view
     */
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.backButton:

                ((GachaMainFragment)getParentFragment()).changePage(1);

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