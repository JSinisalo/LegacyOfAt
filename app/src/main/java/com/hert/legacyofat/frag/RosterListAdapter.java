package com.hert.legacyofat.frag;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hert.legacyofat.R;
import com.hert.legacyofat.misc.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the roster list recyclerview.
 */
public class RosterListAdapter extends RecyclerView.Adapter<RosterListAdapter.ViewHolder> {

    private List<String> mGraphic = new ArrayList<>();
    private List<String> mColor = new ArrayList<>();

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    /**
     * The M recycler view.
     */
    RecyclerView mRecyclerView;

    /**
     * Instantiates a new Roster list adapter.
     *
     * @param context  the context
     * @param mGraphic the m graphic
     * @param mColor   the m color
     */
    RosterListAdapter(Context context, List<String> mGraphic, List<String> mColor) {

        this.mInflater = LayoutInflater.from(context);
        this.mGraphic = mGraphic;
        this.mColor = mColor;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.characterlist_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(holder != null && holder.graphic != null) {

            holder.graphic.setText(mGraphic.get(position));
            holder.graphic.setTextColor(Color.parseColor(mColor.get(position)));

            holder.bind(position);
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {

        return mGraphic.size();
    }

    /**
     * The type View holder.
     */
// stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * The Graphic.
         */
        TextView graphic;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {

            super(itemView);
            graphic = (TextView) itemView.findViewById(R.id.graphic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());

            if(!itemStateArray.get(getAdapterPosition(), false)) {

                itemStateArray.clear();
                itemStateArray.put(getAdapterPosition(), true);
            }
            else
                itemStateArray.put(getAdapterPosition(), false);

            bindAll();
        }

        /**
         * Updates viewholder graphic state.
         *
         * @param position the position
         */
        void bind(int position) {

            if(itemStateArray.get(position, false))
                graphic.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF00FF00")));
            else
                graphic.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));

            graphic.refreshDrawableState();
        }
    }

    /**
     * Updates all viewholders graphic states.
     */
    public void bindAll() {

        List<RecyclerView.ViewHolder> views = Utils.getRecyclerViewHolders(mRecyclerView);

        for(int i = 0; i < views.size(); i++) {

            ((ViewHolder)views.get(i)).bind(views.get(i).getLayoutPosition());
        }
    }

    /**
     * Sets data.
     *
     * @param mGraphic the m graphic
     * @param mColor   the m color
     */
    public void setData(List<String> mGraphic, List<String> mColor) {

        this.mGraphic = mGraphic;
        this.mColor = mColor;
    }

    /**
     * Sets click listener.
     *
     * @param itemClickListener the item click listener
     */
// allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {

        this.mClickListener = itemClickListener;
    }

    /**
     * The interface Item click listener.
     */
// parent activity will implement this method to respond to click events
    public interface ItemClickListener {

        /**
         * On item click.
         *
         * @param view     the view
         * @param position the position
         */
        void onItemClick(View view, int position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {

        mGraphic = null;
        mColor = null;
        mClickListener = null;
        mInflater = null;

        super.onDetachedFromRecyclerView(recyclerView);
    }
}
