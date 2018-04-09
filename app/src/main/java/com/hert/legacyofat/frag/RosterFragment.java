package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by juhos on 20.3.2018.
 */

public class RosterFragment extends Fragment {

    private static final int NUM_PAGES = 2;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster, container, false);

        PagerAdapter mPagerAdapter = new RosterPagerAdapter(getChildFragmentManager());
        ((ViewPager)v.findViewById(R.id.rosterPager)).setAdapter(mPagerAdapter);
        ((ViewPager)v.findViewById(R.id.rosterPager)).setOffscreenPageLimit(2);

        ((ViewPager)v.findViewById(R.id.rosterPager)).setCurrentItem(1);

        return v;
    }

    public void changePage(int position) {

        ((ViewPager)getView().findViewById(R.id.rosterPager)).setCurrentItem(position);
    }

    private class RosterPagerAdapter extends FragmentStatePagerAdapter {

        public RosterPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {

                case 1:
                    return new RosterListFragment();
                case 0:
                    return new RosterCharacterFragment();
                default:
                    return new RosterListFragment();
            }
        }

        @Override
        public int getCount() {

            return NUM_PAGES;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
