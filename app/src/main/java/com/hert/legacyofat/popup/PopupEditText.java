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
import com.hert.legacyofat.layout.CustomEditText;

/**
 * Popup that has an edit text in it, puts the value of the edittext into the extras of onCloseDialog.
 */
public class PopupEditText extends DialogFragment {

    private PopupError.PopupErrorListener mListener;
    private String title;
    private String content;
    private CustomEditText edit;
    private int id;

    /**
     * New popup edit text.
     *
     * @param title           the title
     * @param content         the content
     * @param id              the id
     * @param restart         the restart
     * @param fragmentManager the fragment manager
     */
    public static void newPopupEditText(String title, String content, int id, Boolean restart, FragmentManager fragmentManager) {

        Bundle b = new Bundle();

        b.putString("title", title);
        b.putString("content", content);
        b.putBoolean("restart", restart);
        b.putInt("id", id);

        DialogFragment dialog = new PopupEditText();
        dialog.setArguments(b);
        dialog.show(fragmentManager, "PopupEditText");
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
        boolean restart = getArguments().getBoolean("restart");
        id = getArguments().getInt("id");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(dialogView);

        TextView title = (TextView) dialogView.findViewById(R.id.errorTextTitle);

        //https://github.com/square/leakcanary/issues/297
        edit = (CustomEditText) dialogView.findViewWithTag("thisisstupid");


        Button ok = (Button) dialogView.findViewById(R.id.okButton);

        title.setText(this.title);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mListener.onDialogClose(PopupEditText.this, PopupEditText.this.id, edit.getText().toString());

                edit.setCursorVisible(false);

                edit = null;
                mListener = null;

                dismiss();
            }});

        Dialog d = builder.create();

        if(restart)
            d.setCanceledOnTouchOutside(false);

        return d;
    }

    @Override
    public void onDetach() {

        if(edit != null)
            edit.setCursorVisible(false);

        edit = null;

        mListener = null;

        super.onDetach();
    }

    @Override
    public void onDestroy() {

        if(edit != null)
            edit.setCursorVisible(false);

        edit = null;

        mListener = null;

        super.onDestroy();
    }
}
