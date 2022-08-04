package com.mycoiffeur.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mycoiffeur.R;
import com.mycoiffeur.interfaces.ProfileForClient;
import com.mycoiffeur.models.Client;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {
    private Context context;
    private List<Client> recomendedCoiffureList;

    public RecommendationAdapter(Context context, List<Client> recomendedCoiffureList){
        this.context = context;
        this.recomendedCoiffureList = recomendedCoiffureList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recomended_coiffeur_item,parent,false);
        return new RecommendationAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.firstNameLastName.setText(recomendedCoiffureList.get(position).getNom()+" "+recomendedCoiffureList.get(position).getPrenom());
        holder.gotoAppelle.setTag(recomendedCoiffureList.get(position).getUserId()+"w");// for whatssApp
        holder.gotoProfile.setTag(recomendedCoiffureList.get(position).getUserId());// for profile
        Log.d("MMMM",recomendedCoiffureList.get(position).getImageProfile());
        Picasso.with(context).load("https://mycoiffure.azurewebsites.net/Files/"+recomendedCoiffureList.get(position).getImageProfile()).into(holder.coiffeurImage);

        if(recomendedCoiffureList.get(position).getValable()){
            holder.isValable.setText("Sera disponible apres: "+recomendedCoiffureList.get(position).getWaitTime());
        }else{
            holder.isValable.setText("N'est pas disponible");
            holder.indiceImgDisponibilite.setVisibility(View.GONE);
            holder.isValable.setTextColor(Color.RED);
        }
        holder.gotoProfile.setOnClickListener(new View.OnClickListener() {
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
        holder.gotoAppelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return recomendedCoiffureList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView firstNameLastName,address,disponibiliete,note;
        ImageView coiffeurImage,indiceImgDisponibilite;
        CardView gotoProfile,gotoAppelle;
        TextView isValable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameLastName = itemView.findViewById(R.id.firstlastname_r);
            disponibiliete = itemView.findViewById(R.id.disponibilite_r);
            gotoProfile = itemView.findViewById(R.id.btn_go_profile_r);
            gotoAppelle = itemView.findViewById(R.id.vutton_go_whatsap_r);
            coiffeurImage = itemView.findViewById(R.id.recmimg);
            isValable = itemView.findViewById(R.id.disponibilite_r);
            indiceImgDisponibilite = itemView.findViewById(R.id.disind_r);




        }
    }

}
