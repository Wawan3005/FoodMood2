package com.example.wawan.foodmood2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView txvName;

    public RestaurantViewHolder(View rootView) {
        super(rootView);
        this.txvName = rootView.findViewById(R.id.r_restaurant_txv_name);
    }
}

