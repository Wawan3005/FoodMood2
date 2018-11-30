package com.example.wawan.foodmood2;

import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements MyActivityCallback, OnMapReadyCallback {

    private FragmentManager fragmentManager;
    private ArrayList<GooglePlace> restaurants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Ca commence !");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Correction exception "application attempts to perform a networking operation in the main thread"
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Création d'un fragment manager pour la gestion des fragments
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ResearchFragment fragment = new ResearchFragment();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();

        Bundle bundle = new Bundle();
        //bundle.putSerializable("cle", bottles);
        fragment.setArguments(bundle);

    }

    public void setupTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("cle", restaurants.get(1));
        Bundle bundle = new Bundle();
        bundle.putSerializable("cle", restaurants);
        System.out.println("Liste restaurants dans setupTabs");
        for (GooglePlace poi : restaurants){
            poi.afficher();
        }

        ActionBar.Tab tab1 = actionBar
                .newTab();
        System.out.println("salut c est tab1");
                tab1.setText("Résultats")
                .setTabListener(new SupportFragmentTabListener<ResultListFragment>(R.id.result, this,
                        "first", ResultListFragment.class, bundle));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Carte")
                //.setIcon(R.drawable.ic_mentions)
                .setTabListener(new SupportFragmentTabListener<SupportMapFragment>(R.id.result, this,
                        "second", SupportMapFragment.class, bundle));
        actionBar.addTab(tab2);

        ActionBar.Tab tab3 = actionBar
                .newTab()
                .setText("Détails")
                //.setIcon(R.drawable.ic_mentions)
                .setTabListener(new SupportFragmentTabListener<DetailsFragment>(R.id.result, this,
                        "third", DetailsFragment.class, bundle2));
        actionBar.addTab(tab3);
    }

    public void onPassButtonPressed() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ResultFragment fragment = new ResultFragment();

        fragmentTransaction.addToBackStack("tag");

        fragmentTransaction.replace(R.id.container, fragment);

        fragmentTransaction.commit();

        Bundle bundle = new Bundle();
        //bundle.putSerializable("cle", bottles);
        fragment.setArguments(bundle);
    }

    public void onRechercherButtonPressed(String ville, String typeCuisine) {

        String stringUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+"+ville+"+"+typeCuisine+"&key=AIzaSyDWbc0JpJv5KF7ReuOw13icvSTV4NA4Igw";
        System.out.println(stringUrl);

        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("création url");
        HttpURLConnection httpconn = null;
        try {
            httpconn = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("url connection");
        StringBuilder response = new StringBuilder();
        try {
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null)
                {
                    response.append(strLine);
                }
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("réception réponse");
        String jsonOutput = response.toString();

        restaurants = parseGoogleParse(jsonOutput);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResultFragment fragment = new ResultFragment();
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

        Bundle bundle = new Bundle();
        //bundle.putSerializable("cle", bottles);
        fragment.setArguments(bundle);
    }

    private static ArrayList parseGoogleParse(final String response) {

        ArrayList temp = new ArrayList();

        try {

            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);

            // make an jsonObject in order to parse the response
            if (jsonObject.has("results")) {

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    GooglePlace poi = new GooglePlace();
                    //récupération du nom et de l'avis
                    if (jsonArray.getJSONObject(i).has("name")) {
                        //récupération du nom
                        poi.setName(jsonArray.getJSONObject(i).optString("name"));
                        //récupération de l'avis
                        poi.setRating(jsonArray.getJSONObject(i).optString("rating", " "));
                        //récuparation de la géolocalisation
                        poi.setLat((float) jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
                        poi.setLng((float) jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));

                        //récupération du booléen indiquant si le restaurant est ouvert
                        if (jsonArray.getJSONObject(i).has("opening_hours")) {
                            if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").has("open_now")) {
                                if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").getString("open_now").equals("true")) {
                                    poi.setOpenNow("YES");
                                } else {
                                    poi.setOpenNow("NO");
                                }
                            }
                        } else {
                            poi.setOpenNow("Not Known");
                        }
                        if (jsonArray.getJSONObject(i).has("types")) {
                            JSONArray typesArray = jsonArray.getJSONObject(i).getJSONArray("types");

                            for (int j = 0; j < typesArray.length(); j++) {
                                poi.setCategory(typesArray.getString(j) + ", " + poi.getCategory());
                            }
                        }
                    }
                    temp.add(poi);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
        System.out.println("c'est temp");
        for (Object poi : temp){
            afficher((GooglePlace) poi);
        }
        return temp;

    }

    private static void afficher(GooglePlace poi) {
        System.out.println(poi.getName());
        System.out.println(poi.getLat());
        System.out.println(poi.getLng());
        System.out.println(poi.getCategory());
        System.out.println(poi.getRating());
        System.out.println(poi.getOpenNow());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng gardanne = new LatLng(43.45, 5.4667);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.addMarker(new MarkerOptions().position(gardanne).title("Marker in Gardanne"));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(gardanne));

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            /*case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item as a favorite...
                return true;*/
            /*case R.id.action_map:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_details:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            /*case R.id.action_clean:
                bottles.clear();

                //Mise à jour du fragment de la liste de bouteilles
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                BottleListFragment fragment = new BottleListFragment();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();

                Bundle bundle = new Bundle();
                bundle.putSerializable("cle", bottles);
                fragment.setArguments(bundle);

                return true;*/
            /*default: // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }*/


            public void OnClickItem(GooglePlace restaurant) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                DetailsFragment fragment = new DetailsFragment();
                fragmentTransaction.add(R.id.container, fragment);
                fragmentTransaction.commit();

                Bundle bundle = new Bundle();
                bundle.putSerializable("cle", restaurant);
                fragment.setArguments(bundle);
            }

}
