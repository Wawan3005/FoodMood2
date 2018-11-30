package com.example.wawan.foodmood2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {
    private ArrayList<GooglePlace> restaurants;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RestaurantAdapter(ArrayList<GooglePlace> restaurants) {
        this.restaurants = restaurants;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_restaurant, parent, false);
        return new RestaurantViewHolder(rootView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        GooglePlace restaurantToDisplay = this.restaurants.get(position);

        holder.txvName.setText(restaurantToDisplay.getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.restaurants.size();
    }
}
