package com.hert.legacyofat.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hert.legacyofat.R;

/**
 * PopUp with yes and no buttons.
 */
public class PopupYesNo extends DialogFragment {

    private PopupError.PopupErrorListener mListener;
    private String title;
    private String content;
    private int id;

    /**
     * New popup yes no.
     *
     * @param title           the title
     * @param content         the content
     * @param id              the id
     * @param restart         the restart
     * @param fragmentManager the fragment manager
     */
    public static void newPopupYesNo(String title, String content, int id, Boolean restart, FragmentManager fragmentManager) {

        Bundle b = new Bundle();

        b.putString("title", title);
        b.putString("content", content);
        b.putBoolean("restart", restart);
        b.putInt("id", id);

        DialogFragment dialog = new PopupYesNo();
        dialog.setArguments(b);
        dialog.show(fragmentManager, "PopupYesNo");
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        try {

            mListener = (PopupError.PopupErrorListener) context;

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
        id = getArguments().getInt("id");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_yesno, null);
        builder.setView(dialogView);

        TextView title = (TextView) dialogView.findViewById(R.id.errorTextTitle);
        TextView content = (TextView) dialogView.findViewById(R.id.errorText);
        Button ok = (Button) dialogView.findViewById(R.id.okButton);
        Button notOk = (Button) dialogView.findViewById(R.id.notOkButton);

        title.setText(this.title);
        content.setText(this.content);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mListener.onDialogClose(PopupYesNo.this, PopupYesNo.this.id, "");
                mListener = null;
                dismiss();
            }});

        notOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mListener = null;
                dismiss();
            }});

        Dialog d = builder.create();

        return d;
    }
}
