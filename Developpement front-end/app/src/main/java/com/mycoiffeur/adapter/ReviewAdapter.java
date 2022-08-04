package com.mycoiffeur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mycoiffeur.R;
import com.mycoiffeur.models.PostItem;
import com.mycoiffeur.models.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    List<Review> listComments;
    Context context;

    public ReviewAdapter(Context context, List<Review> listComments){
        this.context = context;
        this.listComments = listComments;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comment_profile,parent,false);
        return new ReviewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.evaluation.setText("Evaluation :"+listComments.get(position).getNote()+"/5");
        holder.commentContent.setText(listComments.get(position).getFeedBack());
        holder.username.setText(listComments.get(position).getFirstName());
    }

    @Override
    public int getItemCount() {
        return listComments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView username,commentContent,evaluation;
        ImageView userImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.firstlastname_comment);
            commentContent = itemView.findViewById(R.id.content_comment);
            evaluation = itemView.findViewById(R.id.note_comment);



        }
    }
}
