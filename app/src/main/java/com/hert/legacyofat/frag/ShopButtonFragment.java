package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.squareup.leakcanary.RefWatcher;

/**
 * Fragment which holds the two buttons on the blacksmith fragment page.
 */
public class ShopButtonFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shop_button, container, false);

        v.findViewById(R.id.inventoryButton).setOnClickListener(this);
        v.findViewById(R.id.shopShopButton).setOnClickListener(this);

        return v;
    }

    /**
     * On click.
     *
     * @param v view
     */
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.shopShopButton:

                ((ShopMainFragment)getParentFragment()).changePage(1);

                break;

            case R.id.inventoryButton:

                ((ShopFragment)((ShopMainFragment)getParentFragment()).getParentFragment()).changePage(0);

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
