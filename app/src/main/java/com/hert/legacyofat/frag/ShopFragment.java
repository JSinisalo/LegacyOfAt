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
 * Fragment which holds a viewpager that holds the shop inventory fragment and shop main fragment.
 */
public class ShopFragment extends Fragment {

    private static final int NUM_PAGES = 2;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shop, container, false);

        PagerAdapter mPagerAdapter = new ShopFragment.ShopPagerAdapter(getChildFragmentManager());
        ((ViewPager)v.findViewById(R.id.shopPager)).setAdapter(mPagerAdapter);
        ((ViewPager)v.findViewById(R.id.shopPager)).setOffscreenPageLimit(2);

        ((ViewPager)v.findViewById(R.id.shopPager)).setCurrentItem(1);

        return v;
    }

    /**
     * Change page.
     *
     * @param position the position
     */
    public void changePage(int position) {

        ((ViewPager)getView().findViewById(R.id.shopPager)).setCurrentItem(position);

        if(position == 0) {

            ShopInventoryFragment f = (ShopInventoryFragment)((ShopPagerAdapter)((ViewPager)getView().findViewById(R.id.shopPager)).getAdapter()).getRegisteredFragment(0);

            f.updateInfo();
        }
    }

    private class ShopPagerAdapter extends FragmentStatePagerAdapter {

        /**
         * The Registered fragments.
         */
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        /**
         * Instantiates a new Shop pager adapter.
         *
         * @param fm the fm
         */
        public ShopPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {

                case 1:
                    return new ShopMainFragment();
                case 0:
                    return new ShopInventoryFragment();
                default:
                    return new ShopMainFragment();
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
