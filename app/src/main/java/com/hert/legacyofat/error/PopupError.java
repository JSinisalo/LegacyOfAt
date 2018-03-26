package com.hert.legacyofat.error;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hert.legacyofat.R;
import com.hert.legacyofat.activity.TitleActivity;
import com.hert.legacyofat.misc.Debug;

/**
 * Created by juhos on 24.3.2018.
 */

public class PopupError extends DialogFragment {

    public interface PopupErrorListener {

        public void onDialogClose(DialogFragment dialog);
    }

    private PopupErrorListener mListener;
    private String title;
    private String content;
    private boolean restart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        Debug.log(title + " " + content + " " + restart);

        return v;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        try {

            mListener = (PopupErrorListener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString() + " must implement PopupErrorListener");
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        title = getArguments().getString("title");
        content = getArguments().getString("content");
        restart = getArguments().getBoolean("restart");

        builder
               .setTitle(title).setMessage(content)
               .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                   @Override
                   public void onClick(DialogInterface dialog, int id) {

                       if(restart) {

                           Intent intent = new Intent(PopupError.this.getContext(), TitleActivity.class);
                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           PopupError.this.getActivity().finish(); //TODO: wtf finish first?
                           startActivity(intent);
                           PopupError.this.getActivity().overridePendingTransition(0,0);

                       } else {

                           mListener.onDialogClose(PopupError.this);
                       }
                  }
               });

        Dialog d = builder.create();

        d.setCanceledOnTouchOutside(false);

        return d;
    }
}
