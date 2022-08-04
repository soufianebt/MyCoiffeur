package com.mycoiffeur.services.authentification;

import android.content.Context;
import android.widget.TextView;

import com.mycoiffeur.models.Client;
import com.mycoiffeur.models.VolleyCallBack;

public interface Authentification {

    public void signIn(String email, String passwordHash, TextView errMessage, Context context, VolleyCallBack callBack);
    public void signUp(Client client, Context context,VolleyCallBack callBack);
    public void updateUser(Client client,Context context,VolleyCallBack callBack);
    public String signOut();

}
