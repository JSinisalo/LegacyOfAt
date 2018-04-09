package com.hert.legacyofat.frag;

import android.content.Context;
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
 * Created by juhos on 20.3.2018.
 */

public class RosterListAdapter extends RecyclerView.Adapter<RosterListAdapter.ViewHolder> {

    private List<String> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    RosterListAdapter(Context context, List<String> data) {

        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
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

        String text = mData.get(position);
        holder.myTextView.setText(text);
    }

    // total number of cells
    @Override
    public int getItemCount() {

        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myTextView;

        ViewHolder(View itemView) {

            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.sampleText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {

        if(id != -1)
            return mData.get(id);
        else
            return "";
    }

    public void setData(List<String> data) {

        this.mData = data;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {

        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {

        void onItemClick(View view, int position);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {

        mData = null;
        mClickListener = null;
        mInflater = null;

        super.onDetachedFromRecyclerView(recyclerView);
    }
}
