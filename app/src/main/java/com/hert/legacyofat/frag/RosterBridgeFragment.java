package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.squareup.leakcanary.RefWatcher;

/**
 * Fragment which holds the viewpager between roster character fragment and the first shop inventory fragment.
 */
public class RosterBridgeFragment extends Fragment {

    private static final int NUM_PAGES = 2;

    /**
     * The constant WEAPON.
     */
    public static final int WEAPON = 1;
    /**
     * The constant ARMOR.
     */
    public static final int ARMOR = 2;
    /**
     * The constant TRINKET1.
     */
    public static final int TRINKET1 = 3;
    /**
     * The constant TRINKET2.
     */
    public static final int TRINKET2 = 4;

    private int currentItem = 0;

    /**
     * Sets current item.
     *
     * @param item the item
     */
    public void setCurrentItem(int item) {

        currentItem = item;
    }

    /**
     * Gets current item.
     *
     * @return the current item
     */
    public int getCurrentItem() {

        return currentItem;
    }

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_roster_bridge, container, false);

        PagerAdapter mPagerAdapter = new RosterBridgeFragment.RosterPagerAdapter(getChildFragmentManager());
        ((ViewPager)v.findViewById(R.id.rosterHPager)).setAdapter(mPagerAdapter);
        ((ViewPager)v.findViewById(R.id.rosterHPager)).setOffscreenPageLimit(2);

        ((ViewPager)v.findViewById(R.id.rosterHPager)).setCurrentItem(0);

        return v;
    }

    /**
     * Updates info on the character roster fragment.
     */
    public void updateInfo() {

        RosterCharacterFragment f = (RosterCharacterFragment)((RosterPagerAdapter)((ViewPager)getView().findViewById(R.id.rosterHPager)).getAdapter()).getRegisteredFragment(0);

        f.updateInfo();
    }

    /**
     * Changes page.
     *
     * @param position the position
     */
    public void changePage(int position) {

        ((ViewPager)getView().findViewById(R.id.rosterHPager)).setCurrentItem(position);

        if(position == 0)
            updateInfo();
        else {

            ShopInventoryFragment f = (ShopInventoryFragment)((RosterPagerAdapter)((ViewPager)getView().findViewById(R.id.rosterHPager)).getAdapter()).getRegisteredFragment(1);

            f.updateInfo();
        }
    }

    private class RosterPagerAdapter extends FragmentStatePagerAdapter {

        /**
         * The Registered fragments.
         */
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        /**
         * Instantiates a new Roster pager adapter.
         *
         * @param fm the fm
         */
        public RosterPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {

                case 0:
                    return new RosterCharacterFragment();
                case 1:
                    return new ShopInventoryFragment();
                default:
                    return new RosterCharacterFragment();
            }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}