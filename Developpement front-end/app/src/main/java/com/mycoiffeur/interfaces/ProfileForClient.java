package com.mycoiffeur.interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mycoiffeur.R;
import com.mycoiffeur.adapter.CoiffureAdapter;
import com.mycoiffeur.adapter.RecommendationAdapter;
import com.mycoiffeur.adapter.ReviewAdapter;
import com.mycoiffeur.api.APIurls;
import com.mycoiffeur.models.Client;
import com.mycoiffeur.models.Review;
import com.mycoiffeur.models.VolleyCallBack;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ProfileForClient extends AppCompatActivity {

    private Client currentCoiffeur;
    private TextView firstNameLastName,isValable,waitTime,recommendation_text;
    private CardView goToWhatssap,goToMaps,goToPots;
    private RecyclerView recyclerViewRecommendation;
    private List<Client> recommendedList;
    private ImageView indiceImgDisponibilite,profileImage;

    // POUR LES REVIEWS
    private List<Review> listComments;
    private RecyclerView recyclerViewComments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_for_client);

        profileImage = findViewById(R.id.primg);
        indiceImgDisponibilite = findViewById(R.id.indiceImageDisponiblite);
        recommendation_text = findViewById(R.id.recommendation_text);
        // Link between activite components and class attributs
        firstNameLastName = findViewById(R.id.profileName_c);
        isValable = findViewById(R.id.disponibilite_c);
        goToMaps = findViewById(R.id.gotomaps_c);
        goToWhatssap = findViewById(R.id.contact_coiffeur_c);
        goToPots = findViewById(R.id.show_coiffeur_post_c);

        recyclerViewRecommendation = findViewById(R.id.recycle_view_recomendation);
        recyclerViewComments = findViewById(R.id.recycle_view_feedback);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerComments = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewRecommendation.setLayoutManager(layoutManager);
        recyclerViewComments.setLayoutManager(layoutManagerComments);

        listComments = new ArrayList<>();
        recommendedList = new ArrayList<>();
        currentCoiffeur = new Client();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String coiffeurId = extras.getString("profileId");

            currentCoiffeur.setUserId(coiffeurId);
            Log.d("PROFILE CLIENT",currentCoiffeur.getUserId());
            //GET COIFFEUR DATA FROM THE SERVER
            getCoiffeur(currentCoiffeur.getUserId(), getApplicationContext(), new VolleyCallBack() {
                @Override
                public void onSuccess(String response) {

                    try {
                        JSONObject coiffeur = new JSONObject(response);
                        currentCoiffeur.setEmail(coiffeur.getString("email"));
                        currentCoiffeur.setAddress(coiffeur.getString("address"));
                        currentCoiffeur.setPrenom(coiffeur.getString("firstName"));
                        currentCoiffeur.setNom(coiffeur.getString("lastName"));

                        currentCoiffeur.setImageProfile(coiffeur.getString("imageUrl"));
                        System.out.println("MED "+coiffeur.getString("imageUrl"));
                        if(currentCoiffeur.getImageProfile()!=null){
                            System.out.println("MED "+coiffeur.getString("imageUrl"));
                            Picasso.with(getApplicationContext()).load("https://mycoiffure.azurewebsites.net/Files/"+currentCoiffeur.getImageProfile()).into(profileImage);
                        }
                        currentCoiffeur.setTelephone(coiffeur.getString("tele"));
                        currentCoiffeur.setWaitTime(coiffeur.getString("waitTime"));
                        currentCoiffeur.setValable(coiffeur.getBoolean("isAvailable"));


                        firstNameLastName.setText(currentCoiffeur.getNom()+" "+currentCoiffeur.getPrenom());
                        goToPots.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent myIntent = new Intent(getApplicationContext(),FileActiviteScreen.class);
                                myIntent.putExtra("coiffeurId",coiffeurId);
                                myIntent.putExtra("imageUrl",currentCoiffeur.getImageProfile());
                                myIntent.putExtra("firstName",currentCoiffeur.getPrenom());
                                myIntent.putExtra("lastName",currentCoiffeur.getNom());
                                startActivity(myIntent);
                            }
                        });


                        if(currentCoiffeur.getValable()){
                            isValable.setText("Sera disponible apres: "+currentCoiffeur.getWaitTime());
                        }else{
                            isValable.setText("N'est pas disponible");
                            indiceImgDisponibilite.setVisibility(View.GONE);
                            isValable.setTextColor(Color.RED);
                        }
                        // GET RECOMMENDED COIFFURES // JUST FOR TEST
                        getCoiffeursRecommended(currentCoiffeur.getUserId(), getApplicationContext(), new VolleyCallBack() {
                            @Override
                            public void onSuccess(String response) {
                                Log.d("RECOMENDED RESPONSEE",response);
                                try {
                                    if(response.contains("message")){
                                        Log.d("MESSAGE","PAS DE RECOMMENDATION");
                                        recyclerViewRecommendation.setVisibility(View.GONE);
                                        recommendation_text.setText("Pas de recommendation!");
                                    }else{
                                    JSONObject jsonObject = new JSONObject(response);

                                    JSONObject correlationObject = jsonObject.getJSONObject("Correlation");
                                    String correlationString = correlationObject.toString();
                                    // LIST DES ID DES COIFFURES RECOMENDES
                                     String [] recomendedCoiffuresIds = correlationTraitement(correlationString);
                                     System.out.println("TABLEE"+recomendedCoiffuresIds.length);
                                     // GET COIFFURES RECOMMENDED DATA FROM SERVER
                                    for(int position=1;position<recomendedCoiffuresIds.length;position++){
                                        System.out.println("JJ"+recomendedCoiffuresIds[position]);
                                            getCoiffeur(recomendedCoiffuresIds[position], getApplicationContext(), new VolleyCallBack() {
                                                @Override
                                                public void onSuccess(String response) {


                                                    try {
                                                        JSONObject  coiffeurRecomended = new JSONObject(response);
                                                        Client client_r = new Client();
                                                       client_r.setEmail(coiffeurRecomended.getString("email"));
                                                        client_r.setAddress(coiffeurRecomended.getString("address"));
                                                       client_r.setPrenom(coiffeurRecomended.getString("firstName"));
                                                        client_r.setNom(coiffeurRecomended.getString("lastName"));
                                                        client_r.setUserId(coiffeurRecomended.getString("userId"));
                                                        client_r.setImageProfile(coiffeurRecomended.getString("imageUrl"));
                                                        client_r.setTelephone(coiffeurRecomended.getString("tele"));
                                                        client_r.setWaitTime(coiffeurRecomended.getString("waitTime"));
                                                        client_r.setValable(coiffeurRecomended.getBoolean("isAvailable"));

                                                        recommendedList.add(client_r);
                                                        System.out.println("ccctttt "+recommendedList.size());
                                                        System.out.println("imggg "+client_r.getImageProfile());
                                                        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(getApplicationContext(),recommendedList);
                                                        addAdapterToRecycleView(recommendationAdapter);

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


                                                }

                                                @Override
                                                public void onSuccess(JSONObject response) {

                                                }
                                            });
                                            // MODIFICATIONS
                                           /* if(position==recomendedCoiffuresIds.length-1){
                                                runOnUiThread(new Runnable() {

                                                    @Override
                                                    public void run() {



                                                            System.out.println("ccctt "+recommendedList.size());
                                                            RecommendationAdapter recommendationAdapter = new RecommendationAdapter(getApplicationContext(),recommendedList);
                                                            addAdapterToRecycleView(recommendationAdapter);




                                                        // Stuff that updates the UI

                                                    }
                                                });
                                            }*/
                                    }

                                    // ADD RECYCLE VIEW TO PROFILE RECOMENDED


                                    }





                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onSuccess(JSONObject response) {

                            }
                        });
                        // TO DO : ADD OTHER ATTRIBUTE
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onSuccess(JSONObject response) {

                }
            });

            // GET COMMENTS FROM THE SERVER

            getComments(currentCoiffeur.getUserId(), getApplicationContext(), new VolleyCallBack() {
                @Override
                public void onSuccess(String response) {
                    Log.d("- COMMENTSS -",response.toString());
                    if(response.toString().equals("[]")){
                        // PAS DE COMMENTAIRE
                    }else{
                        try {
                            JSONArray jsonArrayComments = new JSONArray(response);
                            for(int index =0;index<jsonArrayComments.length();index++){
                                Review review = new Review();

                                review.setFeedBack(jsonArrayComments.getJSONObject(index).getString("feedBack"));
                                review.setNote(jsonArrayComments.getJSONObject(index).getInt("note"));
                                review.setClientId(jsonArrayComments.getJSONObject(index).getString("clientId"));
                                // EXTRACT FIRSTNAME & LASTNAME DE CLIENT ?? JUST EMAIL
                                getClient(review.getClientId(), getApplicationContext(), new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String response) {
                                        try {
                                            String emailLite = new JSONObject(response).getString("email");
                                            Log.d("EMAILL",emailLite);
                                            if(emailLite.contains("@")){
                                                review.setFirstName(emailLite.split("@")[0]);
                                            }else{
                                                review.setFirstName(emailLite);
                                            }
                                            listComments.add(review);
                                            if(listComments.size()== jsonArrayComments.length()){

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {

                                                            Log.d("NBR REVIEWSSSdde",listComments.size()+"");
                                                            ReviewAdapter commentsAdapter = new ReviewAdapter(getApplicationContext(),listComments);
                                                            recyclerViewComments.setAdapter(commentsAdapter);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });

                                            }
                                            Log.d("XXXXXXXXXV",listComments.size()+"");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onSuccess(JSONObject response) {

                                    }
                                });


                            }





                        } catch (JSONException e) {
                            Log.d("ERROR COMMENT","PAS DE COMMENTS");
                            e.printStackTrace();
                        }


                    }

                }

                @Override
                public void onSuccess(JSONObject response) {

                }
            });





        }
    }


    // TRAITEMENTS

    void getCoiffeur(String coiffeurId, Context context, VolleyCallBack callBack){

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("profileId",coiffeurId);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, APIurls.URL_GET_ALL_COIFFURE+"/"+coiffeurId, new Response.Listener<String>() {
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

    // EXTRACT COIFFURES RECOMENDED

    void getCoiffeursRecommended(String coiffeurId, Context context, VolleyCallBack callBack){
        Log.d("RECOMENDATION","I AM HERE | "+coiffeurId);
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("profileId",coiffeurId);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, APIurls.URL_GET_ALL_COIFFURE_RECOMMENDED+""+coiffeurId, new Response.Listener<String>() {
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

    // EXTRACT CORRELATION STRING TRAITEMENT

    String[] correlationTraitement(String corelationString){
        String []result;
        corelationString = corelationString.replace("{","");
        corelationString = corelationString.replace("}","");
        System.out.println("CORELATION STRING--"+corelationString);
        if(corelationString.equals("")){


            result = new String[0];
            return result;
        }
        String []tmps = corelationString.split(",");
        System.out.println(".."+tmps.length);
        for(int position = 0;position<tmps.length;position++){
            tmps[position] = tmps[position].split(":")[0];

            tmps[position] = tmps[position].substring(1,tmps[position].length()-1);
            System.out.println(".."+tmps[position]);
            if(position!=0){

            }



        }


        return tmps;

    }

    void addAdapterToRecycleView(RecommendationAdapter recommendationAdapter){
        recyclerViewRecommendation.setAdapter(recommendationAdapter);
    }

    // GET COMMENTS FROM THE SERVER FOR THE COIFFURE PROFILE

    void getComments(String profileId,Context context, VolleyCallBack callBack){

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("profileId",profileId);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, APIurls.URL_GET_ALL_COIFFURE_REVIEWS+"/"+profileId, new Response.Listener<String>() {
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

    // GET CLIENT DATA BY CLIENTID

    void getClient(String clientId,Context context,VolleyCallBack callBack){
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("profileId",clientId);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, APIurls.URL_GET_CLIENT+""+clientId, new Response.Listener<String>() {
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




}