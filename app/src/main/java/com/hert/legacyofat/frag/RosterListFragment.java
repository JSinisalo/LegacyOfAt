package com.hert.legacyofat.frag;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.AsyncResponse;
import com.hert.legacyofat.activity.FragmentResponse;
import com.hert.legacyofat.activity.MainActivity;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.misc.Debug;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhos on 20.3.2018.
 */

public class RosterListFragment extends Fragment implements AsyncResponse, RosterListAdapter.ItemClickListener, View.OnClickListener {
    
    private RosterListAdapter adapter;

    private static final int NUM_PAGES = 10;
    private TeamPagerAdapter mPagerAdapter;
    
    private List<String> data = new ArrayList<>();

    private long dataChanged = 0;
    
    private int selectedTeamSlot = 0;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    private String getTeamName(int idx) {

        return Guser.getTeams().get(idx).getName();
    }

    public void onClick(View v) {

        if(getView() != null) {

            int c = ((ViewPager)getView().findViewById(R.id.teamPager)).getCurrentItem();

            switch(v.getId()) {

                case R.id.teamRight:

                    if(mPagerAdapter.getItemPosition(c) == 9)
                        ((ViewPager)getView().findViewById(R.id.teamPager)).setCurrentItem(0);
                    else
                        ((ViewPager)getView().findViewById(R.id.teamPager)).setCurrentItem(c + 1);


                    break;

                case R.id.teamLeft:

                    if(mPagerAdapter.getItemPosition(c) == 0)
                        ((ViewPager)getView().findViewById(R.id.teamPager)).setCurrentItem(9);
                    else
                        ((ViewPager)getView().findViewById(R.id.teamPager)).setCurrentItem(c - 1);

                    break;

                default:

                    break;
            }

            c = ((ViewPager)getView().findViewById(R.id.teamPager)).getCurrentItem();
            ((MainActivity)getActivity()).setTeamName(getTeamName(c));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster_roster, container, false);
        
        int numberOfColumns = 5;

        ((RecyclerView)v.findViewById(R.id.rosterList)).setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        adapter = new RosterListAdapter(this.getContext(), data);
        adapter.setClickListener(this);
        ((RecyclerView)v.findViewById(R.id.rosterList)).setAdapter(adapter);

        mPagerAdapter = new RosterListFragment.TeamPagerAdapter(getChildFragmentManager());
        ((ViewPager)v.findViewById(R.id.teamPager)).setAdapter(mPagerAdapter);
        ((ViewPager)v.findViewById(R.id.teamPager)).setOffscreenPageLimit(10);

        ((Button)v.findViewById(R.id.teamLeft)).setOnClickListener(this);
        ((Button)v.findViewById(R.id.teamRight)).setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((MainActivity)getActivity()).setRosterListFragment(this);

        updateData();
    }

    public void updateData() {

        data = new ArrayList<>();
        List<JSONObject> charas = Guser.getCharas();

        for(int i = 0; i < charas.size(); i++) {

            try {

                data.add(charas.get(i).getString("name"));

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        adapter.setData(data);
        adapter.notifyDataSetChanged();

        /*
        for(int i = 0; i < 10; i++) {

            try {

                teamNames.add(teams.getJSONObject(i).getString("name"));

                this.teams[i][0] = teams.getJSONObject(i).getInt("char1");
                this.teams[i][1] = teams.getJSONObject(i).getInt("char2");
                this.teams[i][2] = teams.getJSONObject(i).getInt("char3");
                this.teams[i][3] = teams.getJSONObject(i).getInt("char4");

                RosterTeamFragment f = (RosterTeamFragment)mPagerAdapter.getRegisteredFragment(i);

                if(f != null)
                    f.setSlotTexts(adapter.getItem(this.teams[i][0]),adapter.getItem(this.teams[i][1]),adapter.getItem(this.teams[i][2]),adapter.getItem(this.teams[i][3]));

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        ((MainActivity)getActivity()).setTeamName(teamNames.get(((ViewPager)v.findViewById(R.id.teamPager)).getCurrentItem()));
        */
    }

    @Override
    public void onItemClick(View view, int position) {

        for(View v : getViews()) {

            v.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }

        if(selectedTeamSlot == 0) {

            view.setBackgroundColor(Color.argb(255, 0, 0, 0));

            if(((MainActivity)getActivity()).getSelectedChara() == position)
                ((RosterFragment)getParentFragment()).changePage(0);
            else
                ((MainActivity)getActivity()).setSelectedChara(position);

        } else {

            putCharaToSlot(selectedTeamSlot - 1, position);
        }
    }

    public void putCharaToSlot(int selectedTeamSlot, int chara) {

        int c = ((ViewPager)getView().findViewById(R.id.teamPager)).getCurrentItem();

        Guser.getTeams().get(c).setChar(selectedTeamSlot + 1, chara);

        RosterTeamFragment f = (RosterTeamFragment)mPagerAdapter.getRegisteredFragment(c);

        if(f != null) {

            f.updateNames();
            f.clearBorders();
        }

        ((MainActivity)getActivity()).setSelectedChara(-1);
        this.selectedTeamSlot = 0;

        for(View v : getViews()) {

            v.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }

        new CallBackendTask(CallBackendTask.POST_TEAM_DATA, this).execute("http://91.155.202.223:7500/api/team", Guser.getToken(), Guser.getTeamString(c));
    }

    private View getView(int id) {

        return ((RecyclerView)getView().findViewById(R.id.rosterList)).getChildViewHolder(((RecyclerView)getView().findViewById(R.id.rosterList)).getChildAt(id)).itemView;
    }

    private List<View> getViews() {

        List<View> views = new ArrayList<>();

        for (int childCount = ((RecyclerView)getView().findViewById(R.id.rosterList)).getChildCount(), i = 0; i < childCount; ++i) {

            RecyclerView.ViewHolder holder = ((RecyclerView)getView().findViewById(R.id.rosterList)).getChildViewHolder(((RecyclerView)getView().findViewById(R.id.rosterList)).getChildAt(i));

            views.add(holder.itemView);
        }

        return views;
    }

    private class TeamPagerAdapter extends FragmentStatePagerAdapter {

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public TeamPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle b = new Bundle();
            b.putInt("p", position);

            RosterTeamFragment f = new RosterTeamFragment();
            f.setArguments(b);

            return f;
        }

        @Override
        public int getCount() {

            return NUM_PAGES;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);

            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

    public int getSelectedTeamSlot() {

        return selectedTeamSlot;
    }

    public void setSelectedTeamSlot(int selectedTeamSlot) {

        this.selectedTeamSlot = selectedTeamSlot;
    }

    @Override
    public void showConnectingWidget() {

        ((FragmentResponse)getActivity()).showConnectingWidget();
    }

    @Override
    public void hideConnectingWidget() {

        ((FragmentResponse)getActivity()).hideConnectingWidget();
    }

    @Override
    public void processFinish(int id, String result) {

        ((FragmentResponse)getActivity()).processFinish(id, result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        Debug.log("resume");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Debug.log("att");

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        Debug.log("ress");

    }
}
