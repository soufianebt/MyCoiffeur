package com.mycoiffeur.securite;

import android.util.Log;

public class Securite {

    public Boolean passwordConfirme(String passwordHash1,String passwordHash2){
        Log.d("secure class password verrification :",passwordHash1+","+passwordHash2);
        return passwordHash1.equals(passwordHash2);

    }
}
