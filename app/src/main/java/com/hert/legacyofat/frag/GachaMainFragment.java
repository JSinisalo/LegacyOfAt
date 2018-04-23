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
 * Fragment which holds the viewpager that switches between the results and main gacha pages.
 */
public class GachaMainFragment extends Fragment {

    private static final int NUM_PAGES = 2;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gacha_main, container, false);

        PagerAdapter mPagerAdapter = new GachaPagerAdapter(getChildFragmentManager());
        ((ViewPager)v.findViewById(R.id.gachaPager)).setAdapter(mPagerAdapter);
        ((ViewPager)v.findViewById(R.id.gachaPager)).setOffscreenPageLimit(2);

        ((ViewPager)v.findViewById(R.id.gachaPager)).setCurrentItem(1);

        return v;
    }

    /**
     * Changes page to the desired position.
     *
     * @param position the position
     */
    public void changePage(int position) {

        ((ViewPager)getView().findViewById(R.id.gachaPager)).setCurrentItem(position);

    }

    private class GachaPagerAdapter extends FragmentStatePagerAdapter {

        /**
         * Instantiates a new Gacha pager adapter.
         *
         * @param fm the fm
         */
        public GachaPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {

                case 1:
                    return new GachaFragment();
                case 0:
                    return new GachaResultFragment();
                default:
                    return new GachaFragment();
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