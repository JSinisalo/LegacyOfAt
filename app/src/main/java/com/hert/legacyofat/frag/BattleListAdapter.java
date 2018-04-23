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
import com.hert.legacyofat.backend.Battle;
import com.hert.legacyofat.backend.Guser;

import java.util.List;

/**
 * Adapter for the battle fragment recyclerview.
 */
public class BattleListAdapter extends RecyclerView.Adapter<BattleListAdapter.ViewHolder> {

    private ItemClickListener mClickListener;

    private LayoutInflater mInflater;

    private List<Battle> battles;

    /**
     * Instantiates a new Battle list adapter.
     *
     * @param context the context
     */
    BattleListAdapter(Context context) {

        this.mInflater = LayoutInflater.from(context);
        battles = Guser.getBattles();
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = mInflater.inflate(R.layout.battle_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.char1.setText(battles.get(position).getGraphics().get(0));
        holder.char2.setText(battles.get(position).getGraphics().get(1));
        holder.char3.setText(battles.get(position).getGraphics().get(2));
        holder.char4.setText(battles.get(position).getGraphics().get(3));

        holder.char1.setTextColor(Color.parseColor(battles.get(position).getColors().get(0)));
        holder.char2.setTextColor(Color.parseColor(battles.get(position).getColors().get(1)));
        holder.char3.setTextColor(Color.parseColor(battles.get(position).getColors().get(2)));
        holder.char4.setTextColor(Color.parseColor(battles.get(position).getColors().get(3)));

        holder.name.setText(battles.get(position).getName());

        if(battles.get(position).getCleared())
            holder.name.setTextColor(Color.parseColor("#00FF00"));
        else
            holder.name.setTextColor(Color.parseColor("#FF0000"));
    }

    // total number of cells
    @Override
    public int getItemCount() {

        return battles.size();
    }

    /**
     * The type View holder.
     */
// stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * The Char 1.
         */
        TextView char1;
        /**
         * The Char 2.
         */
        TextView char2;
        /**
         * The Char 3.
         */
        TextView char3;
        /**
         * The Char 4.
         */
        TextView char4;
        /**
         * The Name.
         */
        TextView name;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {

            super(itemView);

            char1 = (TextView) itemView.findViewById(R.id.char1);
            char2 = (TextView) itemView.findViewById(R.id.char2);
            char3 = (TextView) itemView.findViewById(R.id.char3);
            char4 = (TextView) itemView.findViewById(R.id.char4);
            name = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
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
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {

        mInflater = null;

        super.onDetachedFromRecyclerView(recyclerView);
    }
}
