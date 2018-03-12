package com.epam.mikle.vkapimvp.presenters;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.epam.mikle.vkapimvp.R;
import com.epam.mikle.vkapimvp.contracts.BaseContract;
import com.epam.mikle.vkapimvp.contracts.StartContract;
import com.epam.mikle.vkapimvp.models.Student;
import com.epam.mikle.vkapimvp.repositories.impl.MemoryWorker;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import java.util.List;


public class StartActivityPresenter implements StartContract.StartMvpPresenter {

    private boolean isTokenValid;
    private static StartActivityPresenter instance;
    private static StartContract.StartMvpView context;
    private String[] scope = new String[]{
            VKScope.PAGES,
            VKScope.GROUPS,
            VKScope.MESSAGES,
            VKScope.FRIENDS
    };

    private StartActivityPresenter(StartContract.StartMvpView context){
        instance = this;
        StartActivityPresenter.context = context;
    }

    public static StartActivityPresenter getInstance(StartContract.StartMvpView context){
        if (instance == null) {
            return new StartActivityPresenter(context);
        } else {
            StartActivityPresenter.context = context;
            return instance;
        }
    }

    @Override
    public void onStart() {
//        if(isInternetOn()) {
//            VKSdk.login(context.getActivity(), scope);
//        } else {
//
//            context.finishView();
//        }
//        context = null;
        if (!isTokenValid){
            context.makeToast("Неверный вк!!!");
            //context.finishView();
        } else if (!isInternetOn()){
            context.makeToast("Включите инет!");
        } else {
            List<Student> students = MemoryWorker.getInstance(context.getActivity()).getStudents();
            context.loadMainActivity(students);
        }
    }



    @Override
    public void onFinish() {
    }

    private boolean isInternetOn(){
        ConnectivityManager cm = (ConnectivityManager) context.getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onValidToken() {
        isTokenValid = true;
    }

    @Override
    public void onInvalidToken() {
        isTokenValid = false;
    }
}
