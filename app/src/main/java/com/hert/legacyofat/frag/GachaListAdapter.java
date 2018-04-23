package com.hert.legacyofat.frag;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hert.legacyofat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the gacha results recyclerview.
 */
public class GachaListAdapter extends RecyclerView.Adapter<GachaListAdapter.ViewHolder> {

    private List<String> mGraphic = new ArrayList<>();
    private List<String> mColor = new ArrayList<>();
    private List<String> mDescription = new ArrayList<>();

    private LayoutInflater mInflater;

    /**
     * Instantiates a new Gacha list adapter.
     *
     * @param context the context
     */
    GachaListAdapter(Context context) {

        this.mInflater = LayoutInflater.from(context);
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.gacharesult_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.graphic.setText(mGraphic.get(position));
        holder.graphic.setTextColor(Color.parseColor(mColor.get(position)));
        holder.description.setText(mDescription.get(position));
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
    public class ViewHolder extends RecyclerView.ViewHolder{

        /**
         * The Graphic.
         */
        TextView graphic;
        /**
         * The Description.
         */
        TextView description;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {

            super(itemView);
            graphic = (TextView) itemView.findViewById(R.id.resultGraphic);
            description = (TextView) itemView.findViewById(R.id.resultDescription);
        }
    }

    /**
     * Sets data.
     *
     * @param mGraphic     the m graphic
     * @param mColor       the m color
     * @param mDescription the m description
     */
    public void setData(List<String> mGraphic, List<String> mColor, List<String> mDescription ) {

        this.mGraphic = mGraphic;
        this.mColor = mColor;
        this.mDescription = mDescription;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {

        this.mGraphic = null;
        this.mColor = null;
        this.mDescription =null;
        mInflater = null;

        super.onDetachedFromRecyclerView(recyclerView);
    }
}
