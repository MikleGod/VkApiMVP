package com.epam.mikle.vkapimvp.presenters;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.epam.mikle.vkapimvp.R;
import com.epam.mikle.vkapimvp.contracts.BaseContract;
import com.epam.mikle.vkapimvp.contracts.StartContract;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;


public class StartActivityPresenter implements BaseContract.MvpPresenter {

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
        if(isInternetOn()) {
            VKSdk.login(context.getActivity(), scope);
            context.loadMainActivity();
            context.finishView();
        } else {
            Toast.makeText(
                    context.getActivity()
                    , context.getActivity()
                            .getResources()
                            .getString(R.string.INTERNET_OUT_TOAST), Toast.LENGTH_LONG).show();
            context.finishView();
        }
        context = null;
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

}
