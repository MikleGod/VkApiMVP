package com.epam.mikle.vkapimvp.presenters.impl;


import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.epam.mikle.vkapimvp.R;
import com.epam.mikle.vkapimvp.presenters.StartMvpPresenter;
import com.epam.mikle.vkapimvp.views.StartMvpView;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;


public class StartActivityPresenter implements StartMvpPresenter {

    private static StartActivityPresenter instance;
    private static StartMvpView context;
    private String[] scope = new String[]{
            VKScope.PAGES,
            VKScope.GROUPS,
            VKScope.MESSAGES,
            VKScope.FRIENDS
    };

    private StartActivityPresenter(StartMvpView context){
        instance = this;
        StartActivityPresenter.context = context;
    }

    public static StartActivityPresenter getInstance(StartMvpView context){
        if (instance == null)
            return new StartActivityPresenter(context);
        else
            return instance;
    }

    @Override
    public void onStart() {
        if(isInternetOn()) {
            Toast.makeText(
                    context.getActivity()
                    , context.getActivity()
                            .getResources()
                            .getString(R.string.INTERNET_OUT_TOAST), Toast.LENGTH_LONG).show();
            context.finishView();
        } else {
            VKSdk.login(context.getActivity(), scope);
            context.loadMainActivity();
            context.finishView();
        }
    }

    @Override
    public void onFinish() {
    }

    private boolean isInternetOn(){
        ConnectivityManager cm = (ConnectivityManager) context.getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm == null;
    }

}
