package com.example.wawan.foodmood2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DetailsFragment extends Fragment {

    private MyActivityCallback activity;
    private GooglePlace restaurant;
    private TextView textRating;
    private TextView textName;
    private TextView textOpenhours;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_details, container, false);
        // rootView.findViewById...
        System.out.println("salut c est DetailsFragment");

        Bundle bundle = getArguments();
        restaurant = (GooglePlace) bundle.getSerializable("cle");

        Toast.makeText(getContext(), restaurant.getName(), Toast.LENGTH_SHORT).show();

        textRating = rootview.findViewById(R.id.textViewRatingValue);
        textName = rootview.findViewById(R.id.textViewNameValue);
        textOpenhours = rootview.findViewById(R.id.textViewOpenhoursValue);

        textRating.setText(restaurant.getRating());
        textOpenhours.setText(restaurant.getOpenNow());
        textName.setText(restaurant.getName());


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
