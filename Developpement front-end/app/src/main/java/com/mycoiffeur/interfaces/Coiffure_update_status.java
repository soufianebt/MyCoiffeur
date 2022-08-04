package com.mycoiffeur.interfaces;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.mycoiffeur.R;
import com.mycoiffeur.api.APIurls;
import com.mycoiffeur.models.Client;
import com.mycoiffeur.models.DataPart;
import com.mycoiffeur.models.VolleyCallBack;
import com.mycoiffeur.models.VolleyMultipartRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Coiffure_update_status extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int IMG_REQUEST = 1;
    private EditText mfirst,mlast,mwhatsApp,madresse;
    private ImageView mimage,reteurleft;
    private CheckBox mchoui,mchnon,mch1,mch2,mch3,mch4;
    private AppCompatButton msave;
    private Client currentCoiffeur;

    private Bitmap bitmap;
    Uri path;
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coiffure_update_status);

        reteurleft = findViewById(R.id.reteur);
        reteurleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(myIntent);
            }
        });
        mfirst = findViewById(R.id.mfirstname);
        mlast = findViewById(R.id.mlastname);
        mwhatsApp = findViewById(R.id.mwhatsapp);
        madresse = findViewById(R.id.maddress);
        mimage = findViewById(R.id.mmimg);
        mch1 = findViewById(R.id.mcheck0);
        mch2 = findViewById(R.id.mcheck20);
        mch3 = findViewById(R.id.mcheck45);
        mch4 = findViewById(R.id.mcheck1);
        mchnon = findViewById(R.id.mchecknon);
        mchoui = findViewById(R.id.mcheckoui);
        msave = findViewById(R.id.msave);
        currentCoiffeur = new Client();

        mch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){


                    mch2.setChecked(false);
                    mch3.setChecked(false);
                    mch4.setChecked(false);
                    currentCoiffeur.setWaitTime("0 min");

                }
            }
        });
        mch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    currentCoiffeur.setWaitTime("20 min");
                    mch1.setChecked(false);
                    mch3.setChecked(false);
                    mch4.setChecked(false);

                }
            }
        });
        mch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    currentCoiffeur.setWaitTime("45 min");
                    mch2.setChecked(false);
                    mch1.setChecked(false);
                    mch4.setChecked(false);

                }
            }
        });
        mch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    currentCoiffeur.setWaitTime("+1 heure");
                    mch2.setChecked(false);
                    mch3.setChecked(false);
                    mch1.setChecked(false);

                }
            }
        });
        mchnon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    currentCoiffeur.setValable(false);
                    mchoui.setChecked(false);

                }
            }
        });
        mchoui.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    currentCoiffeur.setValable(true);
                    mchnon.setChecked(false);

                }
            }
        });

        currentCoiffeur = new Client();
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_LOGIN_DATA", MODE_PRIVATE);
        currentCoiffeur.setUserId(prefs.getString("userId",""));

        // GET COIFFURE DATA FROM SERVEUR
        getCoiffeur(currentCoiffeur.getUserId(), getApplicationContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Picasso.with(getApplicationContext()).load("https://mycoiffure.azurewebsites.net/Files/"+jsonObject.getString("imageUrl")).into(mimage);
                    if(jsonObject.getString("firstName")!=null){

                        currentCoiffeur.setPrenom(jsonObject.getString("firstName"));
                        mfirst.setText(currentCoiffeur.getPrenom());
                    }
                    if(jsonObject.getString("lastName")!=null){

                        currentCoiffeur.setNom(jsonObject.getString("lastName"));
                        mlast.setText(currentCoiffeur.getNom());
                    }
                    if(jsonObject.getString("tele")!=null){

                        currentCoiffeur.setTelephone(jsonObject.getString("tele"));
                        mwhatsApp.setText(currentCoiffeur.getTelephone());
                    }
                    if(jsonObject.getString("address")!=null){

                        currentCoiffeur.setAddress(jsonObject.getString("address"));
                        madresse.setText(currentCoiffeur.getAddress());
                    }
                    if(jsonObject.getString("waitTime")!=null){

                        currentCoiffeur.setWaitTime(jsonObject.getString("waitTime"));


                        if(currentCoiffeur.getWaitTime().equals("0 min")){
                            mch1.setChecked(true);
                            mch2.setChecked(false);
                            mch3.setChecked(false);
                            mch4.setChecked(false);
                        }else if(currentCoiffeur.getWaitTime().equals("20 min")){
                            mch1.setChecked(false);
                            mch2.setChecked(true);
                            mch3.setChecked(false);
                            mch4.setChecked(false);

                        }else if(currentCoiffeur.getWaitTime().equals("45 min")){
                            mch1.setChecked(false);
                            mch2.setChecked(false);
                            mch3.setChecked(true);
                            mch4.setChecked(false);

                        }else if(currentCoiffeur.getWaitTime().equals("+1 heure")){
                            mch1.setChecked(false);
                            mch2.setChecked(false);
                            mch3.setChecked(false);
                            mch4.setChecked(true);

                        }
                    }
                    if(jsonObject.getBoolean("isAvailable")){

                        mchoui.setChecked(true);
                        mchnon.setChecked(false);
                    }else {
                        mchnon.setChecked(true);
                        mchoui.setChecked(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onSuccess(JSONObject response) {

            }
        });


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


        mimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // save image if existe
                if(path!=null){
                    // image selected
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
                    try {
                        okhttp3.Response response = client.newCall(request).execute();
                        String imageUri = response.body().string();
                        currentCoiffeur.setImageProfile(imageUri);
                        currentCoiffeur.setTelephone(mwhatsApp.getText().toString());
                        currentCoiffeur.setAddress(mwhatsApp.getText().toString());
                        currentCoiffeur.setNom(mlast.getText().toString());
                        currentCoiffeur.setPrenom(mfirst.getText().toString());

                        // Now update coiffeur
                        updateCoiffeur(currentCoiffeur,getApplicationContext(),new VolleyCallBack(){

                            @Override
                            public void onSuccess(String response) {


                            }

                            @Override
                            public void onSuccess(JSONObject response) {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

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
            Log.d("PATH",path.toString());
            filePath = getPath(path);






            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                mimage.setImageBitmap(bitmap);

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

    private String inageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        System.out.println("ooo"+ Base64.encodeToString(imgBytes,Base64.DEFAULT));
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

    private void getCoiffeur(String coiffeurId, Context context, VolleyCallBack callBack){
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("profileId",coiffeurId);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, APIurls.URL_GET_ALL_COIFFURE+"/"+coiffeurId, new Response.Listener<String>() {
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

    private void updateCoiffeur(Client coiffeur,Context context,VolleyCallBack callBack){
        try{
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("firstName",coiffeur.getPrenom());
            jsonBody.put("lastName",coiffeur.getNom());
            jsonBody.put("imageUrl",coiffeur.getImageProfile());
            jsonBody.put("waitTime",coiffeur.getWaitTime());
            jsonBody.put("isAvailable",coiffeur.getValable());
            jsonBody.put("tele",coiffeur.getTelephone());
            jsonBody.put("address",coiffeur.getAddress());
            jsonBody.put("userId",coiffeur.getUserId());




            final String requestBody = jsonBody.toString();
            Log.d("JSON POST",requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, APIurls.URL_PUT_COIFFEUR, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    callBack.onSuccess(response);
                    Log.d("response:",response.toString());


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
}