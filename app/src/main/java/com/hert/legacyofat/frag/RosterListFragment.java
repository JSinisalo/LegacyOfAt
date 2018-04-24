package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
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
import com.hert.legacyofat.backend.Chara;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.backend.Team;
import com.hert.legacyofat.layout.GridAutofitLayoutManager;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment which holds the roster of characters of the user and the roster team fragments.
 */
public class RosterListFragment extends Fragment implements AsyncResponse, RosterListAdapter.ItemClickListener, View.OnClickListener {
    
    private RosterListAdapter adapter;

    private static final int NUM_PAGES = 10;
    private TeamPagerAdapter mPagerAdapter;

    private List<String> mGraphic = new ArrayList<>();
    private List<String> mColor = new ArrayList<>();

    private int selectedTeamSlot = 0;

    private String getTeamName(int idx) {

        if(Guser.getTeams().size() > 0)
            return Guser.getTeams().get(idx).getName();
        else
            return "??";
    }

    /**
     * On click.
     *
     * @param v view
     */
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
            ((MainActivity)getActivity()).setTeamName(getTeamName(c), c);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster_roster, container, false);

        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());

        ((RecyclerView)v.findViewById(R.id.rosterList)).setLayoutManager(new GridAutofitLayoutManager(this.getContext(), Math.round(px)));
        adapter = new RosterListAdapter(this.getContext(), mGraphic, mColor);
        adapter.setClickListener(this);
        ((RecyclerView)v.findViewById(R.id.rosterList)).setAdapter(adapter);

        mPagerAdapter = new RosterListFragment.TeamPagerAdapter(getChildFragmentManager());
        ((ViewPager)v.findViewById(R.id.teamPager)).setAdapter(mPagerAdapter);
        ((ViewPager)v.findViewById(R.id.teamPager)).setOffscreenPageLimit(10);

        ((Button)v.findViewById(R.id.teamLeft)).setOnClickListener(this);
        ((Button)v.findViewById(R.id.teamRight)).setOnClickListener(this);

        ((MainActivity)getActivity()).setTeamName(getTeamName(0), 0);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((MainActivity)getActivity()).setRosterListFragment(this);

        updateData();
    }

    /**
     * Updates the recyclerview data with relevant info.
     */
    public void updateData() {

        mGraphic = new ArrayList<>();
        mColor = new ArrayList<>();
        List<Chara> charas = Guser.getCharas();

        for (int i = 0; i < charas.size(); i++) {

            mGraphic.add(charas.get(i).getGraphic());
        }

        for (int i = 0; i < charas.size(); i++) {

            mColor.add(charas.get(i).getColor());
        }

        adapter.setData(mGraphic, mColor);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

        if(selectedTeamSlot == 0) {

            //view.setBackgroundColor(Color.argb(255, 0, 0, 0));

            if(((MainActivity)getActivity()).getSelectedChara() == position)
                ((RosterFragment)getParentFragment()).changePage(0);
            else
                ((MainActivity)getActivity()).setSelectedChara(position);

        } else {

            putCharaToSlot(selectedTeamSlot - 1, position);
        }
    }

    /**
     * Puts a character to the team slot.
     *
     * @param selectedTeamSlot the selected team slot
     * @param chara            the chara
     */
    public void putCharaToSlot(int selectedTeamSlot, int chara) {

        int c = ((ViewPager)getView().findViewById(R.id.teamPager)).getCurrentItem();

        Team team = Guser.getTeams().get(c);

        if(team.charaExists(chara) != -1) {

            team.setChar(team.charaExists(chara), -1);
        }

        team.setChar(selectedTeamSlot + 1, chara);

        RosterTeamFragment f = (RosterTeamFragment)mPagerAdapter.getRegisteredFragment(c);

        if(f != null) {

            f.updateNames();
            f.clearBorders();
        }

        ((MainActivity)getActivity()).setSelectedChara(-1);
        this.selectedTeamSlot = 0;

        new CallBackendTask(CallBackendTask.POST_TEAM_DATA, this).execute("api/team", Guser.getToken(), Guser.getTeamString(c));
    }

    private class TeamPagerAdapter extends FragmentStatePagerAdapter {

        /**
         * The Registered fragments.
         */
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        /**
         * Instantiates a new Team pager adapter.
         *
         * @param fm the fm
         */
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

        /**
         * Gets registered fragment.
         *
         * @param position the position
         * @return the registered fragment
         */
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

    /**
     * Sets selected team slot.
     *
     * @param selectedTeamSlot the selected team slot
     */
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
}
