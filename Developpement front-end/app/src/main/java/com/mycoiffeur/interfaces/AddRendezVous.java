package com.mycoiffeur.interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mycoiffeur.R;
import com.mycoiffeur.models.ItemCalandria;

public class AddRendezVous extends AppCompatActivity {

    private EditText lebele,time,prix,services;
    private AppCompatButton saveRendezVous,annulerRendezVous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rendez_vous);

        lebele = findViewById(R.id.lebele);
        time = findViewById(R.id.time);
        prix = findViewById(R.id.prix);
        services = findViewById(R.id.services);

        saveRendezVous = findViewById(R.id.save_rendez_vous);
        annulerRendezVous = findViewById(R.id.annuler_rendez_vous);


        Bundle extras = getIntent().getExtras();
        if(extras != null){

                String operationType = extras.getString("operationType");
                if(operationType.equals("ajouter")){


                    saveRendezVous.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ItemCalandria itemCalandria = new ItemCalandria();
                            itemCalandria.setLebele(lebele.getText().toString());
                            itemCalandria.setPrice(prix.getText().toString());
                            itemCalandria.setServices(services.getText().toString());
                            itemCalandria.setTime(time.getText().toString());

                            itemCalandria.saveRendezVous(getApplicationContext());

                            Intent myIntent = new Intent(getApplicationContext(),ProfileActivity.class);
                            startActivity(myIntent);

                        }
                    });

                }else{

                }
        }
    }
}