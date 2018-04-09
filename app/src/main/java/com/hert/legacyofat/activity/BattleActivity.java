package com.hert.legacyofat.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hert.legacyofat.R;
import com.hert.legacyofat.async.CallBackendTask;
import com.hert.legacyofat.backend.Guser;
import com.hert.legacyofat.popup.PopupError;

import org.json.JSONException;
import org.json.JSONObject;

public class BattleActivity extends AppCompatActivity implements AsyncResponse, FragmentResponse, PopupError.PopupErrorListener {

    private boolean battleOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_battle);

        ((TextView)findViewById(R.id.battleText)).setText("you are fighting " + getIntent().getExtras().getString("json") + "\nclick to continue...");
    }

    public void onClick(View v) {

        if(!battleOver) {

            new CallBackendTask(CallBackendTask.START_BATTLERESULTS, this).execute("http://91.155.202.223:7500/api/battleresult", Guser.getToken());

        } else {

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            overridePendingTransition(0,0);
        }
    }

    @Override
    public void showConnectingWidget() {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        findViewById(R.id.battleConnecting).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideConnectingWidget() {

        findViewById(R.id.battleConnecting).setVisibility(View.INVISIBLE);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void processFinish(int id, String result) {

        switch(id) {

            case CallBackendTask.START_BATTLERESULTS:

                int jims = 0;
                int gold = 0;

                try {

                    JSONObject json = new JSONObject(result);

                    gold = json.getInt("gold");
                    jims = json.getInt("jims");

                } catch (JSONException e) {

                    e.printStackTrace();
                }

                Guser.setJims(Guser.getJims() + jims);
                Guser.setGold(Guser.getGold() + gold);

                ((TextView)findViewById(R.id.battleText)).setText("you got " + jims + " jims \nand " + gold + " gold \n click to continue...");

                battleOver = true;

                break;

            case -1:
            default:

                PopupError.newPopupError("Error: Backend connectivity",
                        "Error connecting to backend. Going back to title screen.",
                        0, true, getSupportFragmentManager());

                break;
        }
    }

    @Override
    public void onDialogClose(DialogFragment dialog, int id) {

    }
}
