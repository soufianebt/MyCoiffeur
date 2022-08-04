package com.mycoiffeur.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class ItemCalandria {
    private String lebele,time,services,price,date;

    public ItemCalandria(String lebele, String time, String services, String price, String date) {
        this.lebele = lebele;
        this.time = time;
        this.services = services;
        this.price = price;
        this.date = date;
    }

    public ItemCalandria(){

    }

    public String getLebele() {
        return lebele;
    }

    public void setLebele(String lebele) {
        this.lebele = lebele;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void saveRendezVous(Context context){





        SharedPreferences prefs = context.getSharedPreferences("RENDEZ_NBR", Context.MODE_PRIVATE);
        String nbrRendez = prefs.getString("rendez_nbr","");
        //System.out.println("nbr rendez vous= "+nbrRendez);
        if(nbrRendez.equals("")||nbrRendez.equals("0")){
            SharedPreferences.Editor editor2 = prefs.edit();
            editor2.putString("rendez_nbr","1");
            editor2.apply();


            SharedPreferences sharedPreferences= context.getSharedPreferences("1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("lebele",this.lebele);
            editor.putString("prix",this.price);
            editor.putString("time",this.time);
            editor.putString("services",this.services);



            editor.apply();
        }else{
            int nbr = Integer.parseInt(nbrRendez);
            nbr += 1;
            SharedPreferences.Editor editor2 = prefs.edit();
            editor2.putString("rendez_nbr",Integer.toString(nbr));
            editor2.apply();

            SharedPreferences sharedPreferences= context.getSharedPreferences(Integer.toString(nbr), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("lebele",this.lebele);
            editor.putString("prix",this.price);
            editor.putString("time",this.time);
            editor.putString("services",this.services);

            editor.apply();

        }

    }
    public  void deleteRendezVous(Context context,int position){
        Log.d("POSITION",Integer.toString(position));
        SharedPreferences sharedPreferences= context.getSharedPreferences(Integer.toString(position), Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        Log.d("WHY:", sharedPreferences.getString("lebele",""));



        // redimisioner
        SharedPreferences prefs = context.getSharedPreferences("RENDEZ_NBR", Context.MODE_PRIVATE);
        String nbrRendez = prefs.getString("rendez_nbr","");
        SharedPreferences.Editor editor = prefs.edit();
        int size = Integer.parseInt(nbrRendez);
        System.out.println("size= "+size);
        int tmp = 1;
        for(int i=1;i<=size;i++) {
            SharedPreferences sharedPreferences2 = context.getSharedPreferences(Integer.toString(i), Context.MODE_PRIVATE);
            String lebele = sharedPreferences2.getString("lebele","");
            System.out.println("Position:"+i+",lebele="+lebele+"--");
            String time = sharedPreferences2.getString("time","");
            String prix = sharedPreferences2.getString("prix","");
            String services = sharedPreferences2.getString("services","");
            if(!lebele.equals("")){
                    sharedPreferences2.edit().clear().apply();
                    sharedPreferences2 = context.getSharedPreferences(Integer.toString(tmp), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                    editor2.putString("lebele",lebele);
                    editor2.putString("prix",prix);
                    editor2.putString("time",time);
                    editor2.putString("services",services);
                    editor2.apply();
                    tmp++;
            }


        }

        System.out.println("tmp= "+tmp);

        editor.putString("rendez_nbr",Integer.toString(tmp-1));
        editor.apply();






    }

    // TO DO: Implementation de modification de rendez vous
    public void updateRendezVous(Context context,int position){

    }


}
