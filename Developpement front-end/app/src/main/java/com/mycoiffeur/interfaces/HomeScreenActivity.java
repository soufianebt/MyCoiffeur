package com.mycoiffeur.interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.mycoiffeur.R;
import com.mycoiffeur.adapter.AdapterPost;
import com.mycoiffeur.adapter.CoiffureAdapter;
import com.mycoiffeur.api.APIurls;
import com.mycoiffeur.models.Client;
import com.mycoiffeur.models.User;
import com.mycoiffeur.models.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {


    private Client user;
    private ImageView searchBtn,homeBtn,profileBtn,notificationBtn,addButton;
    private List<Client> listOfCoiffures,searchTmpList;
    private RecyclerView recyclerViewCoiffuresSearch;
    private SearchView searchView;
    private AppCompatButton searchBtnCoiffure;
    private GridLayout gridLayout;
    private LinearLayout linearLayoutbarBottom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        searchView = findViewById(R.id.searchViewCoiffeur);

        // GET NAVIGATION BAR ICONS
        searchBtnCoiffure = findViewById(R.id.button_search_home);
        gridLayout = findViewById(R.id.gradLayoutHomeAcitivite);
        linearLayoutbarBottom = findViewById(R.id.nav_bar_bottom);
        addButton = findViewById(R.id.add_icon);
        searchBtn = findViewById(R.id.search_icon);
        homeBtn = findViewById(R.id.home_icon);
        profileBtn = findViewById(R.id.profile_icon);
        notificationBtn = findViewById(R.id.notification_icon);
        recyclerViewCoiffuresSearch = findViewById(R.id.coiffures_search_recycle_view);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navCtrl(getApplicationContext(),LoginScreenActivity.class);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navCtrl(getApplicationContext(),HomeScreenActivity.class);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navCtrl(getApplicationContext(),ProfileActivity.class);
            }
        });

        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navCtrl(getApplicationContext(),ProfileActivity.class);
            }
        });

        //..

        user = new Client();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DATA", MODE_PRIVATE);
        user.setUserId(prefs.getString("userId",""));
        user.setUserType(prefs.getString("userType",""));
        user.setEmail(prefs.getString("email",""));
        // to do : first not firs
        user.setPrenom(prefs.getString("firstName",""));
        user.setNom(prefs.getString("lastName",""));
        user.setAddress(prefs.getString("address",""));

        System.out.println("username:"+user.getPrenom());

        if(user.getUserId() == ""){

            navCtrl(getApplicationContext(),LoginScreenActivity.class);
        }else{

           // Deja le user a fait un signIn

            if(user.getUserType().equals("COIFFURE")){
                System.out.println("uuu"+user.getUserId());
                System.out.println("user name:"+user.getNom()+"--"+user.getPrenom());

                if(user.getNom().equals("")||user.getPrenom().equals("")){

                    // information not complete for coiffeur
                    navCtrl(getApplicationContext(),CompleteProfile.class);

                }

                // GET ALL COIFFURES FOR SEARCH AND RECOMENDATION
                listOfCoiffures = new ArrayList<>();
               /* getAllCoiffure(getApplicationContext(), new VolleyCallBack() {
                    @Override
                    public void onSuccess(String response) {

                    }

                    @Override
                    public void onSuccess(JSONObject response) {

                    }
                });*/
                // GET ALL COIFFURE FROM THE SERVER MOONGODB

                getAllCoiffure(getApplicationContext(), new VolleyCallBack() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONArray jsonArrayCoiffures = new JSONArray(response);
                            for(int index=0;index<jsonArrayCoiffures.length();index++){
                                Log.d("TEST",jsonArrayCoiffures.getJSONObject(index).getString("lastName"));
                                Client coiffeur = new Client();
                                coiffeur.setNom(jsonArrayCoiffures.getJSONObject(index).getString("lastName"));
                                coiffeur.setPrenom(jsonArrayCoiffures.getJSONObject(index).getString("firstName"));
                                coiffeur.setAddress(jsonArrayCoiffures.getJSONObject(index).getString("address"));
                                coiffeur.setUserId(jsonArrayCoiffures.getJSONObject(index).getString("userId"));
                                coiffeur.setEmail(jsonArrayCoiffures.getJSONObject(index).getString("email"));
                                if(!user.getPrenom().equals(coiffeur.getPrenom())|| !user.getNom().equals(coiffeur.getNom())){
                                    listOfCoiffures.add(coiffeur);
                                    Log.d("INDEXXXXX",index+"--");
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(JSONObject response) {

                    }
                });
                /*Client client1 = new Client("Lahraoui","Ahmed","ahmed@gmail.com","","COIFFURE");
                client1.setAddress("Rue Badiss, Antalia, N12, Nador");
                Client client2 = new Client("Salmi","Youness","youness@gmail.com","","COIFFURE");
                client2.setAddress("Rue Salmani, N24, Fess");
                Client client3 = new Client("Hassani","Amine","Amine@gmail.com","","COIFFURE");
                client3.setAddress("Rue Bachiri, Etage 2, N3, Rabat");

                listOfCoiffures.add(client1);
                listOfCoiffures.add(client2);
                listOfCoiffures.add(client3);*/

                searchView.setOnSearchClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        searchBtnCoiffure.setVisibility(View.GONE);
                        gridLayout.setVisibility(View.GONE);
                        linearLayoutbarBottom.setVisibility(View.GONE);
                        recyclerViewCoiffuresSearch.setVisibility(View.VISIBLE);
                    }
                });


                Log.d("XXX","BERRACHDI MOHAMED");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Log.d("TEXT: ",query.toString());
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        searchBtnCoiffure.setVisibility(View.GONE);
                        gridLayout.setVisibility(View.GONE);
                        linearLayoutbarBottom.setVisibility(View.GONE);
                        recyclerViewCoiffuresSearch.setVisibility(View.VISIBLE);
                        if(newText.equals("")){
                            searchBtnCoiffure.setVisibility(View.VISIBLE);
                            gridLayout.setVisibility(View.VISIBLE);
                            linearLayoutbarBottom.setVisibility(View.VISIBLE);
                            recyclerViewCoiffuresSearch.setVisibility(View.GONE);

                        }

                        searchTmpList = new ArrayList<>();
                        Log.d("TEXT: ",newText.toString());
                        Log.d("SIZEEEE",listOfCoiffures.size()+"--");

                        // search query if exist in listCoiffeur
                       for(int position=0;position<listOfCoiffures.size();position++){

                            String searchItem = listOfCoiffures.get(position).getNom()+" "+listOfCoiffures.get(position).getPrenom();
                            System.out.println("SERCH XX: "+searchItem);
                           System.out.println(searchItem.toLowerCase().contains(newText.toLowerCase()));
                            if(searchItem.toLowerCase().contains(newText.toLowerCase())){

                                searchTmpList.add(listOfCoiffures.get(position));

                            }
                        }
                        CoiffureAdapter adapterCoiffeur = new CoiffureAdapter(getApplicationContext(),searchTmpList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerViewCoiffuresSearch.setLayoutManager(layoutManager);
                        recyclerViewCoiffuresSearch.setAdapter(adapterCoiffeur);
                        return true;
                    }
                });



            }else{
                // is just simple client
                Log.d("VILLE",user.getAddress()+".");
                if(user.getAddress().equals("")){
                    // ajouter firstName,lastName,address==city
                    navCtrl(getApplicationContext(),CompleteProfile.class);
                }else{
                    addButton.setVisibility(View.GONE);

                    // GET ALL COIFFURES FOR SEARCH AND RECOMENDATION
                    listOfCoiffures = new ArrayList<>();
                    getAllCoiffure(getApplicationContext(), new VolleyCallBack() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONArray jsonArrayCoiffures = new JSONArray(response);
                                for(int index=0;index<jsonArrayCoiffures.length();index++){
                                    Log.d("TEST",jsonArrayCoiffures.getJSONObject(index).getString("lastName"));
                                    Client coiffeur = new Client();
                                    coiffeur.setNom(jsonArrayCoiffures.getJSONObject(index).getString("lastName"));
                                    coiffeur.setPrenom(jsonArrayCoiffures.getJSONObject(index).getString("firstName"));
                                    coiffeur.setAddress(jsonArrayCoiffures.getJSONObject(index).getString("address"));
                                    coiffeur.setUserId(jsonArrayCoiffures.getJSONObject(index).getString("userId"));
                                    coiffeur.setEmail(jsonArrayCoiffures.getJSONObject(index).getString("email"));
                                    coiffeur.setImageProfile(jsonArrayCoiffures.getJSONObject(index).getString("imageUrl"));
                                    if(!user.getPrenom().equals(coiffeur.getPrenom())|| !user.getNom().equals(coiffeur.getNom())){
                                        listOfCoiffures.add(coiffeur);
                                        Log.d("INDEXXXXX",index+"--");
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onSuccess(JSONObject response) {

                        }
                    });
                    searchView.setOnSearchClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            searchBtnCoiffure.setVisibility(View.GONE);
                            gridLayout.setVisibility(View.GONE);
                            linearLayoutbarBottom.setVisibility(View.GONE);
                            recyclerViewCoiffuresSearch.setVisibility(View.VISIBLE);
                        }
                    });
                    Log.d("XXX","BERRACHDI MOHAMED");
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            Log.d("TEXT: ",query.toString());
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {

                            searchBtnCoiffure.setVisibility(View.GONE);
                            gridLayout.setVisibility(View.GONE);
                            linearLayoutbarBottom.setVisibility(View.GONE);
                            recyclerViewCoiffuresSearch.setVisibility(View.VISIBLE);
                            if(newText.equals("")){
                                searchBtnCoiffure.setVisibility(View.VISIBLE);
                                gridLayout.setVisibility(View.VISIBLE);
                                linearLayoutbarBottom.setVisibility(View.VISIBLE);
                                recyclerViewCoiffuresSearch.setVisibility(View.GONE);

                            }

                            searchTmpList = new ArrayList<>();
                            Log.d("TEXT: ",newText.toString());
                            Log.d("SIZEEEE",listOfCoiffures.size()+"--");

                            // search query if exist in listCoiffeur
                            for(int position=0;position<listOfCoiffures.size();position++){

                                String searchItem = listOfCoiffures.get(position).getNom()+" "+listOfCoiffures.get(position).getPrenom();
                                System.out.println("SERCH XX: "+searchItem);
                                System.out.println(searchItem.toLowerCase().contains(newText.toLowerCase()));
                                if(searchItem.toLowerCase().contains(newText.toLowerCase())){

                                    searchTmpList.add(listOfCoiffures.get(position));

                                }
                            }
                            CoiffureAdapter adapterCoiffeur = new CoiffureAdapter(getApplicationContext(),searchTmpList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerViewCoiffuresSearch.setLayoutManager(layoutManager);
                            recyclerViewCoiffuresSearch.setAdapter(adapterCoiffeur);
                            return true;
                        }
                    });



                }

            }

        }











    }



    // COMMENTE: GET ALL COIFFURES
    private void getAllCoiffure(Context context, VolleyCallBack callBack){
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("profileId","eee");
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, APIurls.URL_GET_ALL_COIFFURE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    callBack.onSuccess(response);


                    Log.d("re",response.toString());





                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("VOLLEY", "eeeee");
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }


            };

            requestQueue.add(stringRequest);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }




    private void navCtrl(Context context, Class<?>cls ){

        Intent intent = new Intent( context,
                cls );

        startActivity(intent);

    }


}