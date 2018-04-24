package com.hert.legacyofat.layout;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/**
 * Custom edit text to fix a memory leak https://github.com/square/leakcanary/issues/297
 */
public class CustomEditText extends android.support.v7.widget.AppCompatTextView {

    /**
     * Instantiates a new Custom edit text.
     *
     * @param context the context
     */
    public CustomEditText(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new Custom edit text.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    /**
     * Instantiates a new Custom edit text.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return null;
    }

    @Override
    protected boolean getDefaultEditable() {
        return true;
    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return ArrowKeyMovementMethod.getInstance();
    }

    @Override
    public Editable getText() {
        return (Editable) super.getText();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, BufferType.EDITABLE);
    }


    /**
     * Sets selection.
     *
     * @param start the start
     * @param stop  the stop
     */
    public void setSelection(int start, int stop) {
        Selection.setSelection(getText(), start, stop);
    }

    /**
     * Sets selection.
     *
     * @param index the index
     */
    public void setSelection(int index) {
        Selection.setSelection(getText(), index);
    }

    /**
     * Select all.
     */
    public void selectAll() {
        Selection.selectAll(getText());
    }

    /**
     * Extend selection.
     *
     * @param index the index
     */
    public void extendSelection(int index) {
        Selection.extendSelection(getText(), index);
    }

    @Override
    public void setEllipsize(TextUtils.TruncateAt ellipsis) {
        if (ellipsis == TextUtils.TruncateAt.MARQUEE) {
            throw new IllegalArgumentException("EditText cannot use the ellipsize mode "
                    + "TextUtils.TruncateAt.MARQUEE");
        }
        super.setEllipsize(ellipsis);
    }
}