package com.epam.mikle.vkapimvp.presenters.impl;

import android.app.Activity;

import com.epam.mikle.vkapimvp.presenters.MainMvpPresenter;
import com.epam.mikle.vkapimvp.views.MainMvpView;


public class MainActivityPresenter implements MainMvpPresenter {

    private static MainActivityPresenter instance;
    private static MainMvpView context;

    private MainActivityPresenter(MainMvpView context){
        instance = this;
        MainActivityPresenter.context = context;
    }

    public static MainActivityPresenter getInstance(MainMvpView context){
        if (instance == null)
            return new MainActivityPresenter(context);
        else
            return instance;
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }
}
