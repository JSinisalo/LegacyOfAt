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
 * Fragment which holds the guser inventory, there are two of these in use. One in roster bridge fragment and other in shop fragment.
 */
public class ShopInventoryFragment extends Fragment implements View.OnClickListener, RosterListAdapter.ItemClickListener, PopupError.PopupErrorListener, AsyncResponse {

    private RosterListAdapter adapter;

    private List<String> mGraphic = new ArrayList<>();
    private List<String> mColor = new ArrayList<>();

    private int selectedItem;
    private int reserved;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shop_inventory, container, false);

        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());

        ((RecyclerView)v.findViewById(R.id.itemList)).setLayoutManager(new GridAutofitLayoutManager(ShopInventoryFragment.this.getContext(), Math.round(px)));
        adapter = new RosterListAdapter(ShopInventoryFragment.this.getContext(), mGraphic, mColor);
        adapter.setClickListener(ShopInventoryFragment.this);
        ((RecyclerView)v.findViewById(R.id.itemList)).setAdapter(adapter);

        v.findViewById(R.id.itemButton).setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if(getParentFragment() instanceof RosterBridgeFragment){

            ((MainActivity)getActivity()).setInventoryListFragment(this);

        } else {

            ((MainActivity)getActivity()).setInventoryListFragment2(this);
            ((TextView)getView().findViewById(R.id.itemButton)).setText("Back");
        }

        updateData();
    }

    /**
     * Updates the inventory recyclerview with relevant data.
     */
    public void updateData() {

        mGraphic = new ArrayList<>();
        mColor = new ArrayList<>();
        List<Item> charas = Guser.getItems();

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

            case R.id.itemButton:

                if(getParentFragment() instanceof  ShopFragment)
                    ((ShopFragment)getParentFragment()).changePage(1);
                else {

                    Item item = Guser.getItems().get(selectedItem);

                    switch(((RosterBridgeFragment)getParentFragment()).getCurrentItem()) {

                        case RosterBridgeFragment.WEAPON:

                            if(item.getItemClass().equals("Dummy")) {

                                Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara()).setWeapon(null);

                            } else if(!item.getItemClass().equals("Weapon")) {

                                PopupError.newPopupError("Select a weapon",
                                        "This item is not a weapon.",
                                        0, false, getChildFragmentManager());

                                return;

                            } else {

                                reserved = Guser.isItemInUse(item);

                                if(reserved == -1 || reserved == ((MainActivity)getActivity()).getSelectedChara())
                                    Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara()).setWeapon(item);
                                else {

                                    PopupYesNo.newPopupYesNo("Item in use",
                                            "This item is already in use by " + Guser.getCharaFromPosition(reserved).getName() +
                                                    "\nClick OK to remove it from " + Guser.getCharaFromPosition(reserved).getName() + " and give to current character",
                                            11, false, getChildFragmentManager());

                                    return;
                                }
                            }

                            break;

                        case RosterBridgeFragment.ARMOR:

                            if(item.getItemClass().equals("Dummy")) {

                                Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara()).setArmor(null);

                            } else if(!item.getItemClass().equals("Armor")) {

                                PopupError.newPopupError("Select an armor",
                                        "This item is not an armor.",
                                        0, false, getChildFragmentManager());

                                return;

                            } else {

                                reserved = Guser.isItemInUse(item);

                                if(reserved == -1 || reserved == ((MainActivity)getActivity()).getSelectedChara())
                                    Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara()).setArmor(item);
                                else {

                                    PopupYesNo.newPopupYesNo("Item in use",
                                            "This item is already in use by " + Guser.getCharaFromPosition(reserved).getName() +
                                                    "\nClick OK to remove it from " + Guser.getCharaFromPosition(reserved).getName() + " and give to current character",
                                            12, false, getChildFragmentManager());

                                    return;
                                }
                            }

                            break;

                        case RosterBridgeFragment.TRINKET1:

                            if(item.getItemClass().equals("Dummy")) {

                                Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara()).setTrinket1(null);

                            } else if(!item.getItemClass().equals("Trinket")) {

                                PopupError.newPopupError("Select a trinket",
                                        "This item is not a trinket.",
                                        0, false, getChildFragmentManager());

                                return;

                            } else {

                                reserved = Guser.isItemInUse(item);

                                if(reserved == -1 || reserved == ((MainActivity)getActivity()).getSelectedChara())
                                    Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara()).setTrinket1(item);
                                else {

                                    PopupYesNo.newPopupYesNo("Item in use",
                                            "This item is already in use by " + Guser.getCharaFromPosition(reserved).getName() +
                                                    "\nClick OK to remove it from " + Guser.getCharaFromPosition(reserved).getName() + " and give to current character",
                                            13, false, getChildFragmentManager());

                                    return;
                                }
                            }

                            break;
                    }

                    ((RosterBridgeFragment)getParentFragment()).changePage(0);
                    new CallBackendTask(CallBackendTask.POST_CHARA_ITEMS, this).execute("api/items", Guser.getToken(), Guser.getItemString(((MainActivity)getActivity()).getSelectedChara()));
        }

                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        selectedItem = position;
        updateInfo();
    }

    /**
     * Updates the info of the details.
     */
    public void updateInfo() {

        /*
        List<View> vs = getViews();

        for(int i = 0; i < vs.size(); i++) {

            if(i == selectedItem) {

                vs.get(i).setBackgroundColor(Color.argb(255, 0, 0, 0));
                continue;
            }


            Item item = Guser.getItems().get(i);

            if(Guser.isItemInUse(item) != -1)
                vs.get(i).setBackgroundColor(Color.argb(255, 255, 0, 0));
            else
                vs.get(i).setBackgroundColor(Color.argb(0, 0, 0, 0));
        }*/

        View v = getView();

        TextView name = v.findViewById(R.id.itemDetailsName);

        if(name != null) {

            Item item = Guser.getItems().get(selectedItem);

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
                description.setText(item.getDescription());

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

    /**
     * Switches an item that was already in use by some other character.
     *
     * @param id the id
     */
    public void switchItem(int id) {

        switch (id) {

            case 11:

                Guser.getCharaFromPosition(reserved).setWeapon(null);
                Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara()).setWeapon(Guser.getItems().get(selectedItem));
                ((RosterBridgeFragment)getParentFragment()).changePage(0);

                break;

            case 12:

                Guser.getCharaFromPosition(reserved).setArmor(null);
                Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara()).setArmor(Guser.getItems().get(selectedItem));
                ((RosterBridgeFragment)getParentFragment()).changePage(0);

                break;

            case 13:

                Guser.getCharaFromPosition(reserved).setTrinket1(null);
                Guser.getCharas().get(((MainActivity)getActivity()).getSelectedChara()).setTrinket1(Guser.getItems().get(selectedItem));
                ((RosterBridgeFragment)getParentFragment()).changePage(0);

                break;
        }

        new CallBackendTask(CallBackendTask.POST_CHARA_ITEMS, this).execute("api/items", Guser.getToken(), Guser.getItemString(((MainActivity)getActivity()).getSelectedChara()));
        new CallBackendTask(CallBackendTask.POST_CHARA_ITEMS, this).execute("api/items", Guser.getToken(), Guser.getItemString(reserved));
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