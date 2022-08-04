package com.mycoiffeur.services.gestion;

import android.content.Context;

import com.mycoiffeur.models.Service;
import com.mycoiffeur.models.VolleyCallBack;

public interface Services {

    public void createService(Service service, Context context, VolleyCallBack volleyCallBack);
    public void updateService(Service service, Context context, VolleyCallBack volleyCallBack);
    public void deleteService(Service service, Context context, VolleyCallBack volleyCallBack);

}
