package com.hert.legacyofat.popup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.hert.legacyofat.activity.TitleActivity;

/**
 * Popup that indicates some kind of error, although also used for simple OK boxes... Has the ability to restart the application back to the title screen when desired.
 */
public class PopupError extends DialogFragment {

    /**
     * The constant GACHA_NOT.
     */
    public static final int GACHA_NOT = 0;
    /**
     * The constant GACHA_ENOUGH_1.
     */
    public static final int GACHA_ENOUGH_1 = 1;
    /**
     * The constant GACHA_ENOUGH_10.
     */
    public static final int GACHA_ENOUGH_10 = 2;

    /**
     * The interface Popup error listener.
     */
    public interface PopupErrorListener {

        /**
         * On dialog close.
         *
         * @param dialog the dialog
         * @param id     the id
         * @param extra  the extra
         */
        public void onDialogClose(DialogFragment dialog, int id, String extra);
    }

    private PopupErrorListener mListener;
    private String title;
    private String content;
    private int id;
    private boolean restart;

    /**
     * New popup error.
     *
     * @param title           the title
     * @param content         the content
     * @param id              the id
     * @param restart         the restart
     * @param fragmentManager the fragment manager
     */
    public static void newPopupError(String title, String content, int id, Boolean restart, FragmentManager fragmentManager) {

        Bundle b = new Bundle();

        b.putString("title", title);
        b.putString("content", content);
        b.putBoolean("restart", restart);
        b.putInt("id", id);

        DialogFragment dialog = new PopupError();
        dialog.setArguments(b);
        dialog.show(fragmentManager, "PopupError");
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
        id = getArguments().getInt("id");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_error, null);
        builder.setView(dialogView);

        TextView title = (TextView) dialogView.findViewById(R.id.errorTextTitle);
        TextView content = (TextView) dialogView.findViewById(R.id.errorText);
        Button ok = (Button) dialogView.findViewById(R.id.okButton);

        title.setText(this.title);
        content.setText(this.content);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (restart) {

                    Intent intent = new Intent(PopupError.this.getContext(), TitleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PopupError.this.getActivity().finish(); //TODO: wtf finish first?
                    startActivity(intent);
                    PopupError.this.getActivity().overridePendingTransition(0, 0);

                    mListener = null;
                    dismiss();

                } else {

                    mListener.onDialogClose(PopupError.this, PopupError.this.id, "");
                    mListener = null;
                    dismiss();
                }
            }});

        Dialog d = builder.create();

        if(restart)
            d.setCanceledOnTouchOutside(false);

        return d;
    }
}
