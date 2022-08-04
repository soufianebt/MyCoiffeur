package com.mycoiffeur.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mycoiffeur.api.APIurls;
import com.mycoiffeur.services.gestion.Services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Service implements Services {

    private String coiffureId;
    private String delay;
    private String description;
    private String name;
    private float price;
    private String serviceId;

    public Service(){
        delay="30";
        description="";
        price=30f;

    };

    public Service(String coiffureId, String delay, String description, String name, float price, String serviceId) {
        this.coiffureId = coiffureId;
        this.delay = delay;
        this.description = description;
        this.name = name;
        this.price = price;
        this.serviceId = serviceId;
    }



    public String getCoiffureId() {
        return coiffureId;
    }

    public void setCoiffureId(String coiffureId) {
        this.coiffureId = coiffureId;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public void createService(Service service, Context context, VolleyCallBack callBack) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("coiffureId",service.getCoiffureId());
            jsonBody.put("delay",service.getDelay());
            jsonBody.put("description",service.getDescription());
            jsonBody.put("name",service.getName());
            jsonBody.put("price",service.getPrice());
            Log.d("btn:enregistrer:",service.getCoiffureId());
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, APIurls.URL_CREATE_SERVICE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    callBack.onSuccess(response);
                    Log.d("sevice inter:",response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("VOLLEY", "eeeee");
                }
            }){
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

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void updateService(Service service, Context context, VolleyCallBack volleyCallBack) {

    }

    @Override
    public void deleteService(Service service, Context context, VolleyCallBack volleyCallBack) {

    }
}
