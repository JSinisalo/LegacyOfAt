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
 * Fragment which holds the shop button fragment and the shop shop fragment.
 */
public class ShopMainFragment extends Fragment {

    private static final int NUM_PAGES = 2;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shop_main, container, false);

        PagerAdapter mPagerAdapter = new ShopMainFragment.ShopPagerAdapter(getChildFragmentManager());
        ((ViewPager)v.findViewById(R.id.shopHPager)).setAdapter(mPagerAdapter);
        ((ViewPager)v.findViewById(R.id.shopHPager)).setOffscreenPageLimit(2);

        ((ViewPager)v.findViewById(R.id.shopHPager)).setCurrentItem(0);

        return v;
    }

    /**
     * Changes page.
     *
     * @param position the position
     */
    public void changePage(int position) {

        ((ViewPager)getView().findViewById(R.id.shopHPager)).setCurrentItem(position);

    }

    private class ShopPagerAdapter extends FragmentStatePagerAdapter {

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

                case 0:
                    return new ShopButtonFragment();
                case 1:
                    return new ShopShopFragment();
                default:
                    return new ShopButtonFragment();
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