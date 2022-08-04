package com.mycoiffeur.interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.common.api.Api;
import com.mycoiffeur.R;
import com.mycoiffeur.models.Service;
import com.mycoiffeur.models.User;
import com.mycoiffeur.models.Client;
import com.mycoiffeur.models.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

public class CompleteProfile extends AppCompatActivity {

    private Client client;
    private EditText firstname,lastname,address;
    private int[]coiffeurServices = {0,0,0,0};
    private CardView cardShower,cardCut,cardVisage,cardChich;
    private TextView msgShower,msgCut,msgVisage,msgChich,titre,titreService;
    private AppCompatButton btnEnrigister;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        titre = findViewById(R.id.titre);
        titreService = findViewById(R.id.titreservice);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        address = findViewById(R.id.address);

        msgShower = findViewById(R.id.text_shower);
        msgCut = findViewById(R.id.text_haircut);
        msgVisage = findViewById(R.id.text_mask);
        msgChich = findViewById(R.id.text_hairdress);


        cardShower = findViewById(R.id.serviceShower);//[3]
        cardCut = findViewById(R.id.serviceCut);//[0]
        cardVisage = findViewById(R.id.serviceMassageVisage);//[2]
        cardChich = findViewById(R.id.serviceChich);//[1]

        btnEnrigister = findViewById(R.id.profilecompliter);

        // Services select
        cardChich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coiffeurServices[1] == 0){
                    coiffeurServices[1] = 1;
                    msgChich.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }else{
                    coiffeurServices[1] = 0;
                    msgChich.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            }
        });

        cardCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coiffeurServices[0] == 0){
                    coiffeurServices[0] = 1;
                    msgCut.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }else{
                    coiffeurServices[0] = 0;
                    msgCut.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }

            }
        });

        cardVisage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coiffeurServices[2] == 0){
                    coiffeurServices[2] = 1;
                    msgVisage.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }else{
                    coiffeurServices[2] = 0;
                    msgVisage.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }

            }
        });

        cardShower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coiffeurServices[3] == 0){
                    coiffeurServices[3] = 1;
                    msgShower.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }else{
                    coiffeurServices[3] = 0;
                    msgShower.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }

            }
        });





        // extract user information from the local storage
        client = new Client();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DATA", MODE_PRIVATE);
        client.setUserId(prefs.getString("userId",""));
        client.setUserType(prefs.getString("userType",""));
        client.setEmail(prefs.getString("email",""));
        client.setPrenom(prefs.getString("firstName",""));
        client.setNom(prefs.getString("lastName",""));

        if(client.getUserType().equals("CLIENT")){

            // GONE ALL SERVICES
            cardChich.setVisibility(View.GONE);
            cardCut.setVisibility(View.GONE);
            cardShower.setVisibility(View.GONE);
            cardVisage.setVisibility(View.GONE);
            address.setHint("Entrer votre ville");
            titreService.setVisibility(View.GONE);
            titre.setText("S'il vous plaît completer votre infors");
        }


        firstname.setText(client.getPrenom());
        lastname.setText(client.getNom());
        Log.d("firstname",client.getPrenom()+".....");
        Log.d("lastname",client.getNom());



        btnEnrigister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btn:enregistrer:","clicked");

                // Create services and add them to database


                // update coiffure profile that means add firstname and lastname

                client.setPrenom(firstname.getText().toString());
                client.setNom(lastname.getText().toString());
                client.setAddress(address.getText().toString());
                /*client.updateUser(client,getApplicationContext(), new VolleyCallBack() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            //JSONObject jsonUser = new JSONObject(response);
                            SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("USER_LOGIN_DATA", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("firstName", client.getPrenom());
                            editor.putString("lastName", client.getNom());
                            editor.putString("address", client.getAddress());
                            editor.apply();



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(JSONObject response) {

                    }
                });*/
                // Update client data
                client.updateUser(client, getApplicationContext(), new VolleyCallBack() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("UPDATE USER",response.toString());
                        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("USER_LOGIN_DATA",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("firstName", client.getPrenom());
                        editor.putString("lastName", client.getNom());
                        editor.putString("address", client.getAddress());
                        editor.apply();
                        Intent myIntent = new Intent(getApplicationContext(),HomeScreenActivity.class);
                        startActivity(myIntent);

                    }

                    @Override
                    public void onSuccess(JSONObject response) {

                    }
                });

                // add coiffeur services
                if(client.getUserType().equals("COIFFURE")){
                    if(coiffeurServices[0] == 1){
                        Log.d("btn:enregistrer:","clicked0");

                        createService("hair cut",client.getUserId());

                    }
                    if(coiffeurServices[1] == 1){

                        createService("Chichoir",client.getUserId());
                    }
                    if(coiffeurServices[2] == 1){
                        createService("Mask",client.getUserId());

                    }
                    if(coiffeurServices[3] == 1){
                        createService("Shower",client.getUserId());

                    }
                    Intent myIntent = new Intent(getApplicationContext(),HomeScreenActivity.class);
                    startActivity(myIntent);


                }




            }
        });









    }


    void createService(String serviceName,String coiffureId){
        Log.d("btn:enregistrer:","clicked1");
        Service service = new Service();
        service.setName(serviceName);
        service.setCoiffureId(coiffureId);
        service.createService(service,getApplicationContext(),new VolleyCallBack(){

            @Override
            public void onSuccess(String response) {
                    Log.d("SERVRCE CREATE:",response.toString());
            }

            @Override
            public void onSuccess(JSONObject response) {

            }
        });

    }
}