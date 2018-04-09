package com.hert.legacyofat.frag;

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

import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.AsyncResponse;
import com.hert.legacyofat.activity.FragmentResponse;
import com.hert.legacyofat.activity.MainActivity;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.backend.Guser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhos on 20.3.2018.
 */

public class RosterListFragment extends Fragment implements AsyncResponse, RosterListAdapter.ItemClickListener, View.OnClickListener {

    private FragmentResponse callback;
    private RosterListAdapter adapter;

    private static final int NUM_PAGES = 10;
    private ViewPager mPager;
    private TeamPagerAdapter mPagerAdapter;

    private RecyclerView recyclerView;

    private List<String> data = new ArrayList<>();

    private long dataChanged = 0;
    
    private MainActivity mainActivity;

    private int selectedTeamSlot = 0;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);

        callback = (FragmentResponse) getActivity();
        mainActivity = ((MainActivity)getActivity());
    }

    private String getTeamName(int idx) {

        return Guser.getTeams().get(idx).getName();
    }

    public void onClick(View v) {

        int c = mPager.getCurrentItem();

        switch(v.getId()) {

            case R.id.teamRight:

                if(mPagerAdapter.getItemPosition(c) == 9)
                    mPager.setCurrentItem(0);
                else
                    mPager.setCurrentItem(c + 1);


                break;

            case R.id.teamLeft:

                if(mPagerAdapter.getItemPosition(c) == 0)
                    mPager.setCurrentItem(9);
                else
                    mPager.setCurrentItem(c - 1);

                break;

            default:

                break;
        }

        c = mPager.getCurrentItem();
        mainActivity.setTeamName(getTeamName(c));

        /*
        RosterTeamFragment f = (RosterTeamFragment)mPagerAdapter.getRegisteredFragment(c);

        if(f != null)
            f.setSlotTexts(adapter.getItem(this.teams[c][0]),adapter.getItem(this.teams[c][1]),adapter.getItem(this.teams[c][2]),adapter.getItem(this.teams[c][3]));
            */
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster_roster, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.rosterList);

        int numberOfColumns = 5;

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        adapter = new RosterListAdapter(this.getContext(), data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        mPager = (ViewPager) v.findViewById(R.id.teamPager);
        mPagerAdapter = new RosterListFragment.TeamPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        ((Button)v.findViewById(R.id.teamLeft)).setOnClickListener(this);
        ((Button)v.findViewById(R.id.teamRight)).setOnClickListener(this);

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if(getView() != null && isVisibleToUser) {

             updateData();
        }

        if(recyclerView != null && recyclerView.getChildCount() > 0 && mainActivity.getSelectedChara() != -1)
            getView(mainActivity.getSelectedChara()).setBackgroundColor(Color.argb(255, 0, 0, 0));

        int c = 0;

        if(mPager != null) {

            c = mPager.getCurrentItem();

            /*
            RosterTeamFragment f = (RosterTeamFragment)mPagerAdapter.getRegisteredFragment(c);

            if(f != null)
                f.setSlotTexts(adapter.getItem(this.teams[c][0]),adapter.getItem(this.teams[c][1]),adapter.getItem(this.teams[c][2]),adapter.getItem(this.teams[c][3]));
                */
        }
    }

    private void updateData() {

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

        mainActivity.setTeamName(teamNames.get(mPager.getCurrentItem()));
        */
    }

    @Override
    public void onItemClick(View view, int position) {

        for(View v : getViews()) {

            v.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }

        if(selectedTeamSlot == 0) {

            view.setBackgroundColor(Color.argb(255, 0, 0, 0));

            if(mainActivity.getSelectedChara() == position)
                ((RosterFragment)getParentFragment()).changePage(0);
            else
                mainActivity.setSelectedChara(position);

        } else {

            putCharaToSlot(selectedTeamSlot - 1, position);
        }
    }

    public void putCharaToSlot(int selectedTeamSlot, int chara) {

        int c = mPager.getCurrentItem();

        Guser.getTeams().get(c).setChar(selectedTeamSlot + 1, chara);

        RosterTeamFragment f = (RosterTeamFragment)mPagerAdapter.getRegisteredFragment(c);

        if(f != null) {

            f.updateNames();
            f.clearBorders();
        }

        mainActivity.setSelectedChara(-1);
        this.selectedTeamSlot = 0;

        for(View v : getViews()) {

            v.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }

        new CallBackendTask(CallBackendTask.POST_TEAM_DATA, this).execute("http://91.155.202.223:7500/api/team", Guser.getToken(), Guser.getTeamString(c));
    }

    private View getView(int id) {

        return recyclerView.getChildViewHolder(recyclerView.getChildAt(id)).itemView;
    }

    private List<View> getViews() {

        List<View> views = new ArrayList<>();

        for (int childCount = recyclerView.getChildCount(), i = 0; i < childCount; ++i) {

            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));

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

        callback.showConnectingWidget();
    }

    @Override
    public void hideConnectingWidget() {

        callback.hideConnectingWidget();
    }

    @Override
    public void processFinish(int id, String result) {

        callback.processFinish(id, result);
    }
}
