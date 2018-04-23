package com.hert.legacyofat.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hert.legacyofat.LegacyOfAtApplication;
import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.AsyncResponse;
import com.hert.legacyofat.activity.FragmentResponse;
import com.hert.legacyofat.activity.MainActivity;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.popup.PopupError;
import com.hert.legacyofat.popup.PopupYesNo;
import com.squareup.leakcanary.RefWatcher;

/**
 * Battle menu fragment. Holds the battle starter viewholders, which are used to initiate a new battle.
 */
public class BattleFragment extends Fragment implements AsyncResponse, View.OnClickListener, PopupError.PopupErrorListener, BattleListAdapter.ItemClickListener {

    private BattleListAdapter adapter;

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_battle, container, false);

        int numberOfColumns = 1;

        adapter = new BattleListAdapter(this.getContext());
        adapter.setClickListener(this);
        ((RecyclerView)v.findViewById(R.id.battleList)).setAdapter(adapter);
        ((RecyclerView)v.findViewById(R.id.battleList)).setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        //remove true to prevent non full teams to battle
        if(true || Guser.isFullTeam(((MainActivity)getActivity()).getTeam())) {

            new CallBackendTask(CallBackendTask.START_BATTLE, this).execute("http://91.155.202.223:7500/api/battlestart", Guser.getToken());

        } else {

            PopupError.newPopupError("team?",
                    "Your team needs to have 4 characters to battle.",
                    0, false, getChildFragmentManager());
        }
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

    @Override
    public void onDialogClose(DialogFragment dialog, int id, String extra) {

    }

    @Override
    public void onItemClick(View view, int position) {

        PopupYesNo.newPopupYesNo("Battle",
                "Do you want to start this battle?",
                100 + position, false, getChildFragmentManager());
    }
}
