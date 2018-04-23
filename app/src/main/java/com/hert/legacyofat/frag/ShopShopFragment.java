package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.AsyncResponse;
import com.hert.legacyofat.activity.FragmentResponse;
import com.hert.legacyofat.activity.MainActivity;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.backend.Item;
import com.hert.legacyofat.layout.GridAutofitLayoutManager;
import com.hert.legacyofat.popup.PopupError;
import com.hert.legacyofat.popup.PopupYesNo;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment which holds the shop of the user.
 */
public class ShopShopFragment extends Fragment implements View.OnClickListener, RosterListAdapter.ItemClickListener, PopupError.PopupErrorListener, AsyncResponse {

    private RosterListAdapter adapter;

    private List<String> mGraphic = new ArrayList<>();
    private List<String> mColor = new ArrayList<>();

    private int selectedItem;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shop_shop, container, false);

        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());

        ((RecyclerView)v.findViewById(R.id.itemList)).setLayoutManager(new GridAutofitLayoutManager(this.getContext(), Math.round(px)));
        adapter = new RosterListAdapter(this.getContext(), mGraphic, mColor);
        adapter.setClickListener(this);
        ((RecyclerView)v.findViewById(R.id.itemList)).setAdapter(adapter);

        v.findViewById(R.id.itemButton).setOnClickListener(this);
        v.findViewById(R.id.backButton).setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((MainActivity)getActivity()).setShopFragment(this);

        updateData();
    }

    /**
     * Updates the shop recyclerview data.
     */
    public void updateData() {

        mGraphic = new ArrayList<>();
        mColor = new ArrayList<>();
        List<Item> charas = Guser.getShop();

        for (int i = 0; i < charas.size(); i++) {

            mGraphic.add(charas.get(i).getGraphic());
        }

        for (int i = 0; i < charas.size(); i++) {

            mColor.add(charas.get(i).getColor());
        }

        adapter.setData(mGraphic, mColor);
        adapter.notifyDataSetChanged();
    }

    /**
     * On click.
     *
     * @param v view
     */
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.backButton:

                ((ShopMainFragment)getParentFragment()).changePage(0);

                break;

            case R.id.itemButton:

                Item item = Guser.getShop().get(selectedItem);

                if(Guser.getGold() < item.getPrice()) {

                    PopupError.newPopupError("Shop",
                            "You need " + item.getPrice() + " gold to buy this item",
                            0, false, getChildFragmentManager());

                    return;

                } else {

                    PopupYesNo.newPopupYesNo("Shop",
                            "Do you want to buy this item?\nIt will cost " + item.getPrice() + " gold.",
                            21, false, getChildFragmentManager());
                }
        }
    }

    /**
     * Calls the backend to buy an item.
     *
     * @param id the id
     */
    public void buyItem(int id) {

        new CallBackendTask(CallBackendTask.BUY_ITEM, this).execute("api/buy", Guser.getToken(), Guser.getBuyString(selectedItem));
    }

    @Override
    public void onItemClick(View view, int position) {

        selectedItem = position;
        updateInfo();
    }

    /**
     * Updates the details info.
     */
    public void updateInfo() {

        View v = getView();

        TextView name = v.findViewById(R.id.itemDetailsName);

        if(name != null) {

            Item item = Guser.getShop().get(selectedItem);

            TextView health = v.findViewById(R.id.itemDetailsHealth);
            TextView attack = v.findViewById(R.id.itemDetailsAttack);
            TextView defense = v.findViewById(R.id.itemDetailsDefense);
            TextView evade = v.findViewById(R.id.itemDetailsEvade);
            TextView speed = v.findViewById(R.id.itemDetailsSpeed);
            TextView description = v.findViewById(R.id.itemDetailsDescription);

            if(!item.getItemClass().equals("Dummy")) {

                name.setText(item.getName() + " - " + item.getItemClass());

                health.setText("Health: " + Math.round(item.getHealth()));
                attack.setText("Attack: " + Math.round(item.getAttack()));
                defense.setText("Defense: " + Math.round(item.getDefense()));
                evade.setText("Evade: " + Math.round(item.getEvade()));
                speed.setText("Speed: " + Math.round(item.getSpeed()));
                description.setText(item.getDescription() + "\n" + "Cost: " + item.getPrice() + " gold");

            } else {

                name.setText("");

                health.setText("");
                attack.setText("");
                defense.setText("");
                evade.setText("");
                speed.setText("");
                description.setText("Select this to remove the current item.");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LegacyOfAtApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onDialogClose(DialogFragment dialog, int id, String extra) {

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
}