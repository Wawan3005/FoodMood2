package com.example.wawan.foodmood2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("ValidFragment")
class ResearchFragment extends Fragment {

    private Button btnPass;
    private Button btnRechercher;
    private EditText edtVille;
    private EditText edtTypeCuisine;
    private MyActivityCallback activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_research, container, false);
        // rootView.findViewById...


        btnPass = rootview.findViewById(R.id.a_pass_btn_ok);
        btnRechercher = rootview.findViewById(R.id.button_rechercher);
        edtVille = rootview.findViewById(R.id.edit_text_ville);
        edtTypeCuisine = rootview.findViewById(R.id.edit_text_type_cuisine);

        Bundle bundle = getArguments();

        btnPass.setOnClickListener((view) -> {
            activity.onPassButtonPressed();

        });

        btnRechercher.setOnClickListener((view) -> {
            String ville = edtVille.getText().toString();
            String typeCuisine = edtTypeCuisine.getText().toString();
            activity.onRechercherButtonPressed(ville, typeCuisine);

        });

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

