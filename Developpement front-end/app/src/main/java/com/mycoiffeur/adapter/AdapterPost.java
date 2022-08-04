package com.mycoiffeur.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mycoiffeur.R;
import com.mycoiffeur.models.ItemCalandria;
import com.mycoiffeur.models.Post;
import com.mycoiffeur.models.PostItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPost  extends RecyclerView.Adapter<AdapterPost.ViewHolder>{
    private Context context;
    private List<PostItem> itemPostList;

    public AdapterPost(Context context, List<PostItem> itemPostList){
        this.context = context;
        this.itemPostList = itemPostList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.post,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(itemPostList.get(position).getDescription()==null){
            holder.description.setText("description de post");
        }else{
            holder.description.setText(itemPostList.get(position).getDescription());
        }

        holder.username.setText(itemPostList.get(position).getFirstName()+" "+itemPostList.get(position).getLastName());
        Log.d("EXEMPLE",itemPostList.get(position).getDescription());
        System.out.println("HH"+itemPostList.get(position).getImage());
        Picasso.with(context).load(itemPostList.get(position).getImage()).into(holder.postImage);
        Picasso.with(context).load(itemPostList.get(position).getProfileUrl()).into(holder.profileimg);
        // TO DO : OTHER ATTRIBUT


    }

    @Override
    public int getItemCount() {
        return itemPostList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView username,typepost,description,likes;
        ImageView postImage,profileimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            typepost = itemView.findViewById(R.id.post_type);
            description = itemView.findViewById(R.id.post_description);
            likes = itemView.findViewById(R.id.post_likes);

            postImage = itemView.findViewById(R.id.post_image);
            profileimg = itemView.findViewById(R.id.itemimg);


        }
    }
}
