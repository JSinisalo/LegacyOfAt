package com.hert.legacyofat.frag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.FragmentResponse;
import com.hert.legacyofat.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by juhos on 20.3.2018.
 */

public class RosterListFragment extends Fragment implements RosterListAdapter.ItemClickListener, View.OnClickListener {

    private FragmentResponse callback;
    private RosterListAdapter adapter;

    private static final int NUM_PAGES = 10;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private JSONObject initialData;

    private List<Integer> charas;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);

        charas = new ArrayList<>();
        callback = (FragmentResponse) getActivity();
        initialData = ((MainActivity)getActivity()).getInitialData();
    }

    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.details:

                ((RosterFragment)getParentFragment()).changePage(0);
                break;

            case R.id.teamRight:

                if(mPagerAdapter.getItemPosition(mPager.getCurrentItem()) == 9)
                    mPager.setCurrentItem(0);
                else
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);

                break;

            case R.id.teamLeft:

                if(mPagerAdapter.getItemPosition(mPager.getCurrentItem()) == 0)
                    mPager.setCurrentItem(9);
                else
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);


                break;

            default:


        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster_roster, container, false);

        List<String> data = new ArrayList<>();

        int count = 0;
        JSONArray charas = null;

        try {

            charas = initialData.getJSONArray("charas");
            count = charas.length();

        } catch (JSONException e) {

            e.printStackTrace();
        }

        for(int i = 0; i < count; i++) {

            try {

                data.add(charas.getJSONObject(i).getString("name"));
                this.charas.add(i); //TODO: wtf tho

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.rosterList);

        int numberOfColumns = 5;

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        adapter = new RosterListAdapter(this.getContext(), data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        mPager = (ViewPager) v.findViewById(R.id.teamPager);
        mPagerAdapter = new RosterListFragment.TeamPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        ((Button)v.findViewById(R.id.details)).setOnClickListener(this);
        ((Button)v.findViewById(R.id.teamLeft)).setOnClickListener(this);
        ((Button)v.findViewById(R.id.teamRight)).setOnClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(View view, int position) {

        Random rnd = new Random();
        view.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        ((MainActivity)getActivity()).setSelectedChara(position);
    }

    private class TeamPagerAdapter extends FragmentStatePagerAdapter {

        public TeamPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return new RosterTeamFragment();
        }

        @Override
        public int getCount() {

            return NUM_PAGES;
        }
    }
}
