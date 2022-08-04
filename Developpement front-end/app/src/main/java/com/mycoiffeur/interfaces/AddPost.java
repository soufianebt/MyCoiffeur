package com.mycoiffeur.interfaces;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.mycoiffeur.models.Client;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Api;
import com.mycoiffeur.R;
import com.mycoiffeur.api.APIurls;
import com.mycoiffeur.models.DataPart;
import com.mycoiffeur.models.Post;
import com.mycoiffeur.models.VolleyCallBack;
import com.mycoiffeur.models.VolleyMultipartRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class AddPost extends AppCompatActivity {
    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;

    private ImageView postImage;
    EditText postDescription;
    AppCompatButton postSave, postAnnuler;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    Uri path;
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        postImage = findViewById(R.id.post_image);
        postDescription = findViewById(R.id.post_description);
        postSave = findViewById(R.id.post_save);
        postAnnuler = findViewById(R.id.post_annuler);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique


        }


        // ACCESS TO THE EXTERNAL STORAGE

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        postSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("POST IMAGE","YES");
                /*try {
                    uploadImage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*///uploadBitmap(bitmap);
                //RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                /*StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        APIurls.URL_POST_IMAGE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Hello Man",response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Hello Man","SOrry");
                            }
                            }){


                    @Nullable
                    @Override
                    protected Map<String, String> getPostParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("file",inageToString(bitmap));
                        return params;
                    }
                };

                requestQueue.add(stringRequest);*/

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("file",filePath,
                                RequestBody.create(MediaType.parse("application/octet-stream"),
                                        new File(filePath)))
                        .build();
                okhttp3.Request request = new  okhttp3.Request.Builder()
                        .url("http://mycoiffure.azurewebsites.net/Files")
                        .method("POST", body)
                        .build();
                try{

                    okhttp3.Response response = client.newCall(request).execute();
                    //Log.d("hello",response.body().string());
                    String imageUri = response.body().string();
                    //*********** GET USER ID & USER TYPE
                    Client currentUser = new Client();
                    SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DATA", MODE_PRIVATE);
                    currentUser.setUserId(prefs.getString("userId",""));

                    //*********** GET PROFILE // profileId == userId

                    Post newPost = new Post();
                    newPost.setImageUrl(imageUri);
                    Log.d("profileId",currentUser.getUserId());
                    newPost.setProfileId(currentUser.getUserId());
                    newPost.setDescription(postDescription.getText().toString());
                    newPost.setDateOfPost(new Date().toString());
                    newPost.setPostType("IMAGE");

                    newPost.savePost(newPost, getApplicationContext(), new VolleyCallBack() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("save succefully",response.toString());

                        }

                        @Override
                        public void onSuccess(JSONObject response) {

                        }
                    });




                }catch (Exception e){
                    e.printStackTrace();
                }













            }
        });

        postAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST && resultCode == RESULT_OK && data != null){
            path = data.getData();
            //bitmap =  (Bitmap) data.getExtras().get("data");
            //postImage.setImageBitmap(bitmap);
            //path = getImageUri(getApplicationContext(), bitmap);
            //File finalFile = new File(getRealPathFromURI(path));
            filePath = getPath(path);






            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                postImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);



    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    /*private void uploadImage() throws JSONException {


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIurls.URL_POST_IMAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            Log.d("Image server response:",response);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Image server erreur:",error.toString());
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "text/plain;charset=UTF-8";
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



            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("file",getPath(path));
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }*/
    private String inageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        System.out.println("ooo"+Base64.encodeToString(imgBytes,Base64.DEFAULT));
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        System.out.println("]]]]]"+Uri.parse(path));
        return Uri.parse(path);
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, APIurls.URL_POST_IMAGE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                       /* try {*/
                            System.out.println("........"+response.toString());
                           // JSONObject obj = new JSONObject(new String(response));
                           // Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                       /* } catch (JSONException e) {
                            System.out.println("........SORRY");
                            e.printStackTrace();
                        }*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                        error.printStackTrace();
                    }
                }) {


           /* @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }*/
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<>();
                DataPart dp = new DataPart("imagename "+ ".png", getFileDataFromDrawable(bitmap));
                long imagename = System.currentTimeMillis();
                params.put("file",dp.getContent().toString());
                return params;
            }


        };

        //adding the request to volley
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }




}