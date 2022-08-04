package com.mycoiffeur.adapter;


import android.content.*;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.mycoiffeur.R;
import com.mycoiffeur.interfaces.ProfileActivity;
import com.mycoiffeur.models.ItemCalandria;

import java.util.List;


public class Rendezvousadapter extends RecyclerView.Adapter<Rendezvousadapter.ViewHolder> {

    private Context context;
    private List<ItemCalandria> itemCalandriaList;

    public Rendezvousadapter(Context context, List<ItemCalandria> itemCalandriaList){
        this.context = context;
        this.itemCalandriaList = itemCalandriaList;
    }

    @NonNull
    @Override
    public Rendezvousadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fileitem,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Rendezvousadapter.ViewHolder holder, int position) {
        ItemCalandria item = itemCalandriaList.get(position);
        holder.linearLayout.setTag("a"+position);

        if(!item.getLebele().equals("no")) {
            holder.lebele.setText(item.getLebele());
            holder.time.setText(item.getTime());
            holder.prix.setText(item.getPrice());
            holder.services.setText(item.getServices());
            holder.modifier.setTag(position);
            holder.supprimer.setTag(position);
            holder.linearLayout.setVisibility(View.VISIBLE);
           // Log.d("no",holder.linearLayout.getTag().toString());


            holder.modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("modifier btn", v.getTag().toString());
                }
            });
            holder.supprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("supprimer btn", v.getTag().toString());
                    int position = Integer.parseInt(v.getTag().toString());
                    SharedPreferences sharedPreferences= context.getSharedPreferences(Integer.toString(position+1), Context.MODE_PRIVATE);
                    item.deleteRendezVous(context,position+1);
                    Log.d("WHY:", sharedPreferences.getString("lebele","").toString()+"--");
                    Intent myIntent = new Intent(context, ProfileActivity.class);
                    context.startActivity(myIntent);
                }
            });

        }else{

            //Log.d("oui",holder.linearLayout.getTag().toString());
            holder.linearLayout.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return itemCalandriaList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView lebele,services,prix,time,delay;
        private TextView modifier,supprimer;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.itemLinear);
            lebele = itemView.findViewById(R.id.lebele);
            services = itemView.findViewById(R.id.services);
            prix = itemView.findViewById(R.id.prix);
            time = itemView.findViewById(R.id.time);
            delay = itemView.findViewById(R.id.delay);
            modifier = itemView.findViewById(R.id.modifier);
            supprimer = itemView.findViewById(R.id.supprimer);
        }
    }
}
