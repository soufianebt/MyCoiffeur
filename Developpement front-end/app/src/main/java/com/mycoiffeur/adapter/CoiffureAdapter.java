package com.mycoiffeur.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mycoiffeur.R;
import com.mycoiffeur.api.APIurls;
import com.mycoiffeur.interfaces.ProfileForClient;
import com.mycoiffeur.models.Client;
import com.mycoiffeur.models.PostItem;
import com.mycoiffeur.models.VolleyCallBack;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *  THIS CLASS FOR SEARCH SERVICES
 */
public class CoiffureAdapter extends RecyclerView.Adapter<CoiffureAdapter.ViewHolder>{

    private Context context;
    private List<Client> itemCoiffureList;

    public CoiffureAdapter(Context context, List<Client> itemCoiffureList){
        this.context = context;
        this.itemCoiffureList = itemCoiffureList;
    }

    @NonNull
    @Override
    public CoiffureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.coiffure_item,parent,false);
        return new CoiffureAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CoiffureAdapter.ViewHolder holder, int position) {

        // TO DO : OTHER ATTRIBUT
        holder.firstNameLastName.setText(itemCoiffureList.get(position).getPrenom()+" "+itemCoiffureList.get(position).getNom());
        holder.address.setText(itemCoiffureList.get(position).getAddress());
        holder.cardView.setTag(itemCoiffureList.get(position).getUserId());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileId = v.getTag().toString();
                Log.d("HOME PAGE ADAPTER",profileId);
                Intent myIntent = new Intent(context, ProfileForClient.class);
                myIntent.putExtra("profileId",profileId);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(myIntent);
            }
        });

        Picasso.with(context).load("https://mycoiffure.azurewebsites.net/Files/"+itemCoiffureList.get(position).getImageProfile()).into(holder.coiffeurImage);

        // GET REVIEWS AND CALCULED NOTES
        getComments(itemCoiffureList.get(position).getUserId(), context, new VolleyCallBack() {
            @Override
            public void onSuccess(String response) {
                if(response.toString().equals("[]")){
                    // PAS DE COMMENTAIRE PAS DE NOTES
                    Log.d("note",response);
                    holder.note.setText("0/5 (0 reviews)");
                }else{
                    JSONArray jsonArrayComments = null;
                    try {
                        jsonArrayComments = new JSONArray(response);
                        int noteFinal = 0;
                        for(int index =0;index<jsonArrayComments.length();index++){
                            noteFinal += jsonArrayComments.getJSONObject(index).getInt("note");

                        }

                        noteFinal = noteFinal/jsonArrayComments.length();
                        holder.note.setText(noteFinal+"/5 ("+jsonArrayComments.length()+" reviews)");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onSuccess(JSONObject response) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemCoiffureList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView firstNameLastName,address,services,note;
        ImageView coiffeurImage;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameLastName = itemView.findViewById(R.id.firstlasttname);
            address = itemView.findViewById(R.id.address);
           // services = itemView.findViewById(R.id.coiffure_service);
            note = itemView.findViewById(R.id.coiffeur_note);
            coiffeurImage = itemView.findViewById(R.id.coiffure_img);
            cardView = itemView.findViewById(R.id.card_coiffeur);





        }
    }

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
}
