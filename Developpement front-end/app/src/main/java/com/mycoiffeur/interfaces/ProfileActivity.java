package com.mycoiffeur.interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mycoiffeur.R;
import com.mycoiffeur.adapter.Rendezvousadapter;
import com.mycoiffeur.models.Client;
import com.mycoiffeur.models.ItemCalandria;
import com.mycoiffeur.models.VolleyCallBack;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView postCount,followCount,raiting,userName,statutCoifure,disponibilite;
    private Client client;
    private Spinner spinnerStatut,spinnerDelay;
    private CardView addPost;

    // Gestion des calandries

    private RecyclerView recyclerView;
    private Rendezvousadapter rendezvousadapter;
    private ArrayList<ItemCalandria> itemCalandrias;

    private ImageView addRendeyVous,updateCoiffeur,profileImage;




    // Fin gestion des calandriers
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        disponibilite = findViewById(R.id.disp);
        profileImage = findViewById(R.id.itemimg);
        updateCoiffeur = findViewById(R.id.update_coiffeur);
        updateCoiffeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),Coiffure_update_status.class);
                startActivity(myIntent);
            }
        });

        addPost = findViewById(R.id.create_post);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(getApplicationContext(),AddPost.class);
                //startActivity(myIntent);
                Log.d(".....","i'm in file activite");
                Intent myIntent = new Intent(getApplicationContext(),FileActiviteScreen.class);
                startActivity(myIntent);
            }
        });
        /**
         *   GESTION DES CALANDRIAS
         */
            addRendeyVous = findViewById(R.id.add_rendez_vous);
            recyclerView = findViewById(R.id.recycle_view_file_items);

            addRendeyVous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getApplicationContext(),AddRendezVous.class);
                    myIntent.putExtra("operationType","ajouter"); // ajouter or modifier
                    startActivity(myIntent);

                }
            });

            // Extract rendez-vous from local storage
                // extract size of list
                itemCalandrias = new ArrayList<>();

                SharedPreferences prefss = getApplicationContext().getSharedPreferences("RENDEZ_NBR", Context.MODE_PRIVATE);
                String srendezVousSize = prefss.getString("rendez_nbr","");




                if(srendezVousSize.equals("")){
                    //pas de rendez-vous
                    Log.d("MESSAGE","PAS DE RENDEZ-VOUS");
                    rendezvousadapter = new Rendezvousadapter(this,itemCalandrias);

                }else{
                    Log.d("MESSAGE","EXISTE DES RENDEZ-VOUS");
                    // exist des rendez-vous
                    int rendezSize = Integer.parseInt(srendezVousSize);
                    for(int i=1;i<=rendezSize;i++){
                        SharedPreferences prefssItem = getApplicationContext().getSharedPreferences(Integer.toString(i), Context.MODE_PRIVATE);


                        if(!prefssItem.getString("lebele","").equals("")){

                        itemCalandrias.add(new ItemCalandria(
                                prefssItem.getString("lebele",""),
                                prefssItem.getString("time",""),
                                prefssItem.getString("services",""),
                                prefssItem.getString("prix",""),
                                "eeee"));
                        System.out.println(i+" -- "+prefssItem.getString("lebele",""));
                        }else{
                           itemCalandrias.add(new ItemCalandria("no","ee","","",""));
                        }

                    }
                    rendezvousadapter = new Rendezvousadapter(this,itemCalandrias);
                }






            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(rendezvousadapter);



        /**
         *  FIN GESTION DES CALANDRIAS
         */


        //postCount = findViewById(R.id.PostCount);
        //followCount = findViewById(R.id.followCount);
        //raiting = findViewById(R.id.rating);
        userName = findViewById(R.id.profileName);


        // Extraire user informations

        client = new Client();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DATA", MODE_PRIVATE);

        client.setEmail(prefs.getString("email",""));
        client.setUserType(prefs.getString("userType",""));
        client.setUserId(prefs.getString("userId",""));

        client.getUser(client.getUserId(), getApplicationContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonUser = new JSONObject(response);
                    // add data to view profile
                    userName.setText(jsonUser.get("firstName").toString()+" "+jsonUser.get("lastName"));
                    Picasso.with(getApplicationContext()).load("https://mycoiffure.azurewebsites.net/Files/"+jsonUser.getString("imageUrl")).into(profileImage);
                    Log.d("user name:",jsonUser.get("firstName").toString());
                    if(jsonUser.getBoolean("isAvailable")){
                        disponibilite.setText("Sera disponible apres: "+jsonUser.getString("waitTime"));
                    }else{
                        disponibilite.setText("N'est pas disponible");

                    }


                    // Addapter for drop down item open or close
                    // C'est le client est un coiffeur
                    if(client.getUserType().equals("Coiffure")){
                        // Spinner information | is just for coiffure
                        spinnerStatut = findViewById(R.id.statut_spinner);
                        spinnerDelay = findViewById(R.id.delay_spinner);

                        final List<String> spinnerArray = new ArrayList<>();
                        spinnerArray.add("Ouverte");
                        spinnerArray.add("Fermer");
                        final List<String> spinnerArrayDelay = new ArrayList<>();
                        spinnerArrayDelay.add("0 minute");
                        spinnerArrayDelay.add("15 minute");
                        spinnerArrayDelay.add("30 minute");
                        spinnerArrayDelay.add("45 minute");
                        spinnerArrayDelay.add("+1 heure");
                        spinnerArrayDelay.add("+2 heure");

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,spinnerArray);
                        ArrayAdapter<String> adapterDelay = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,spinnerArrayDelay);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapterDelay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinnerStatut.setAdapter(adapter);
                        spinnerDelay.setAdapter(adapterDelay);
                        spinnerStatut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                spinnerStatut.setSelection(0);
                            }
                        });

                        spinnerDelay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                spinnerStatut.setSelection(0);
                            }
                        });

                    }else{
                        // C'est le client n'est pas un coiffeur


                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(JSONObject response) {

            }
        });













    }
}