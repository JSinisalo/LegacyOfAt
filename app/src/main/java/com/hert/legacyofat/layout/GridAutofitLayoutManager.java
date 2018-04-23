package com.hert.legacyofat.layout;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

/**
 * Custom GridLayoutManager to automatically fill a recyclerview with enough viewholders to span the entire width.
 * <p>
 * Stolen from https://stackoverflow.com/questions/26666143/recyclerview-gridlayoutmanager-how-to-auto-detect-span-count/30256880
 */
public class GridAutofitLayoutManager extends GridLayoutManager
{
    private int mColumnWidth;
    private boolean mColumnWidthChanged = true;

    /**
     * Instantiates a new Grid autofit layout manager.
     *
     * @param context     the context
     * @param columnWidth the column width
     */
    public GridAutofitLayoutManager(Context context, int columnWidth)
    {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    /**
     * Instantiates a new Grid autofit layout manager.
     *
     * @param context       the context
     * @param columnWidth   the column width
     * @param orientation   the orientation
     * @param reverseLayout the reverse layout
     */
    public GridAutofitLayoutManager(Context context, int columnWidth, int orientation, boolean reverseLayout)
    {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1, orientation, reverseLayout);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    private int checkedColumnWidth(Context context, int columnWidth)
    {
        if (columnWidth <= 0)
        {
            /* Set default columnWidth value (48dp here). It is better to move this constant
            to static constant on top, but we need context to convert it to dp, so can't really
            do so. */
            columnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48,
                    context.getResources().getDisplayMetrics());
        }
        return columnWidth;
    }

    /**
     * Sets column width.
     *
     * @param newColumnWidth the new column width
     */
    public void setColumnWidth(int newColumnWidth)
    {
        if (newColumnWidth > 0 && newColumnWidth != mColumnWidth)
        {
            mColumnWidth = newColumnWidth;
            mColumnWidthChanged = true;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state)
    {
        int width = getWidth();
        int height = getHeight();
        if (mColumnWidthChanged && mColumnWidth > 0 && width > 0 && height > 0)
        {
            int totalSpace;

            if (getOrientation() == VERTICAL)
            {
                totalSpace = width - getPaddingRight() - getPaddingLeft();
            }
            else
            {
                totalSpace = height - getPaddingTop() - getPaddingBottom();
            }

            int spanCount = Math.max(1, totalSpace / mColumnWidth);

            setSpanCount(spanCount);
            mColumnWidthChanged = false;
        }
        super.onLayoutChildren(recycler, state);
    }
}