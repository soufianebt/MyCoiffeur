package com.mycoiffeur.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.mycoiffeur.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    private AppCompatButton btnCreateNewAccount;
    private AppCompatButton btnConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        btnCreateNewAccount = findViewById(R.id.btn_create_new_account);
        btnConnexion = findViewById(R.id.btn_connexion);

        btnCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navCtrl(getApplicationContext(),RegisterScreenActivity.class);
            }
        });

        btnCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navCtrl(getApplicationContext(),LoginScreenActivity.class);
            }
        });



    }


    private void navCtrl(Context context,Class<?>cls ){

        Intent intent = new Intent( context,
                cls );

        startActivity(intent);

    }

}
