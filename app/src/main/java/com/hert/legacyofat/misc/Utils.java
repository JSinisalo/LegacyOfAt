package com.hert.legacyofat.misc;

import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for a few utility functions
 */
public class Utils {

    private static final Field TEXT_LINE_CACHED;

    static {
        Field textLineCached = null;
        try {
            textLineCached = Class.forName("android.text.TextLine").getDeclaredField("sCached");
            textLineCached.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        TEXT_LINE_CACHED = textLineCached;
    }

    /**
     * Clear text line cache. Used to fix a memory leak related to this https://stackoverflow.com/questions/30397356/android-memory-leak-on-textview-leakcanary-leak-can-be-ignored
     */
    public static void clearTextLineCache() {
        // If the field was not found for whatever reason just return.
        if (TEXT_LINE_CACHED == null) return;

        Object cached = null;
        try {
            // Get reference to the TextLine sCached array.
            cached = TEXT_LINE_CACHED.get(null);
        } catch (Exception ex) {
            //
        }
        if (cached != null) {
            // Clear the array.
            for (int i = 0, size = Array.getLength(cached); i < size; i ++) {
                Array.set(cached, i, null);
            }
        }
    }

    /**
     * Gets recycler view holders.
     *
     * @param v the recyclerview
     * @return the recycler view holders
     */
    public static List<RecyclerView.ViewHolder> getRecyclerViewHolders(RecyclerView v) {

        List<RecyclerView.ViewHolder> views = new ArrayList<>();

        for (int childCount = v.getChildCount(), i = 0; i < childCount; ++i) {

            RecyclerView.ViewHolder holder = v.getChildViewHolder((v).getChildAt(i));

            views.add(holder);
        }

        return views;
    }

    private Utils() {}
}