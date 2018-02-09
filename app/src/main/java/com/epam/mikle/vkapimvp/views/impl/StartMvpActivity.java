package com.epam.mikle.vkapimvp.views.impl;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epam.mikle.vkapimvp.presenters.impl.StartActivityPresenter;
import com.epam.mikle.vkapimvp.views.StartMvpView;

public class StartMvpActivity extends AppCompatActivity implements StartMvpView {

    private StartActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = StartActivityPresenter.getInstance(this);
        presenter.onStart();
    }


    @Override
    public void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void finishView() {
        finish();
    }
}
