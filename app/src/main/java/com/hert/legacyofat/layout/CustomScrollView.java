package com.hert.legacyofat.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Custom scrollview used in battle activity to get the fading edges like they are.
 */
public class CustomScrollView extends ScrollView {

    /**
     * Instantiates a new Custom scroll view.
     *
     * @param context the context
     */
    public CustomScrollView(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Custom scroll view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Instantiates a new Custom scroll view.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Instantiates a new Custom scroll view.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     * @param defStyleRes  the def style res
     */
    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return 0f;
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return 2.0f;
    }
}
