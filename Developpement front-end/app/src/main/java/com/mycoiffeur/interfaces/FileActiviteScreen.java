package com.mycoiffeur.interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mycoiffeur.R;
import com.mycoiffeur.adapter.AdapterPost;
import com.mycoiffeur.adapter.Rendezvousadapter;
import com.mycoiffeur.models.Client;
import com.mycoiffeur.models.Post;
import com.mycoiffeur.models.PostItem;
import com.mycoiffeur.models.User;
import com.mycoiffeur.models.VolleyCallBack;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FileActiviteScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterPost adapterPost;
    private List<PostItem> posts;
    private List<PostItem> postsItem;
    private Client user;

    ImageView addPostIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_activite_screen);

        recyclerView = findViewById(R.id.vos_post_recycle);

        addPostIcon = findViewById(R.id.add_post_icon);
        addPostIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getApplicationContext(),AddPost.class);
                startActivity(myIntent);
            }
        });

        user = new Client();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DATA", MODE_PRIVATE);
        user.setUserId(prefs.getString("userId",""));
        user.setUserType(prefs.getString("userType",""));
        user.setEmail(prefs.getString("email",""));
        // to do : first not firs
        user.setPrenom(prefs.getString("firstName",""));
        user.setNom(prefs.getString("lastName",""));

        System.out.println("username:"+user.getPrenom());
        user.getUser(user.getUserId(), getApplicationContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    user.setImageProfile(new JSONObject(response).getString("imageUrl"));
                    Log.d("....",user.getImageProfile());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onSuccess(JSONObject response) {

            }
        });

        // COMMENTE: GET ALL POSTS PUBLISH WITH THIS USER.
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            user.setUserId(extras.getString("coiffeurId"));
            user.setImageProfile(extras.getString("imageUrl"));
            user.setPrenom(extras.getString("firstName"));
            user.setNom(extras.getString("lastName"));
        }

        user.getPosts(user.getUserId(), getApplicationContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArrayPosts = new JSONArray(response);
                    Log.d("JSON ARRAY SIZE:",jsonArrayPosts.length()+"-");

                    // COMMENTE: EXTRACT IMAGES FOR SERVER FOR POSTS
                    postsItem = new ArrayList<>();

                    for(int i=0;i< jsonArrayPosts.length();i++){
                            PostItem postItem = new PostItem();


                            postItem.setProfileId(jsonArrayPosts.getJSONObject(i).getString("profileId"));
                            postItem.setDateOfPost(jsonArrayPosts.getJSONObject(i).getString("dateOfPost"));
                            postItem.setDescription(jsonArrayPosts.getJSONObject(i).getString("description"));
                            postItem.setImageUrl(jsonArrayPosts.getJSONObject(i).getString("imageUrl"));
                            postItem.setPostId(jsonArrayPosts.getJSONObject(i).getString("postId"));
                            postItem.setImage(new String("https://mycoiffure.azurewebsites.net/Files/"+postItem.getImageUrl()));
                            postItem.setProfileUrl("https://mycoiffure.azurewebsites.net/Files/"+user.getImageProfile());
                            postItem.setFirstName(user.getPrenom());
                            postItem.setLastName(user.getNom());
                            postsItem.add(postItem);
                            System.out.println("eee"+postsItem.get(i).getImage());





                    }
                    adapterPost = new AdapterPost(getApplicationContext(),postsItem);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapterPost);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(JSONObject response) {

            }
        });




        posts = new ArrayList<>();

        PostItem post1 = new PostItem();
        PostItem post2 = new PostItem();

        post1.setDescription("Hello every one that is most imposrtant so ");
        post2.setDescription("That is my favorai hair cut forever, Thank you @everyDay");

        posts.add(post1);
        posts.add(post2);





    }



}