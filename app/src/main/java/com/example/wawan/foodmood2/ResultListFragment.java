package com.example.wawan.foodmood2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ResultListFragment extends Fragment {

    private RecyclerView rcvRestaurants;
    private MyActivityCallback activity;
    private ArrayList<GooglePlace> restaurants;
    private RestaurantAdapter restaurantsAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_result_list, container, false);
        // rootView.findViewById...
        System.out.println("salut c est ResultListFragment");

        rcvRestaurants = rootview.findViewById(R.id.a_main_rcv_restaurants);

        Bundle bundle = getArguments();
        restaurants = (ArrayList<GooglePlace>) bundle.getSerializable("cle");


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rcvRestaurants.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvRestaurants.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        restaurantsAdapter = new RestaurantAdapter(restaurants);
        rcvRestaurants.setAdapter(restaurantsAdapter);



        return rootview;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyActivityCallback) {
            activity = (MyActivityCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}
