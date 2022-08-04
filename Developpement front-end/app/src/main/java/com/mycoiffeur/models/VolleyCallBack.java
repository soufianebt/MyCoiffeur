package com.mycoiffeur.models;

import org.json.JSONObject;

public interface VolleyCallBack {
    void onSuccess(String response);
    void onSuccess(JSONObject response);
}
