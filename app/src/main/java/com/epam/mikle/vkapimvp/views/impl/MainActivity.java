package com.epam.mikle.vkapimvp.views.impl;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epam.mikle.vkapimvp.R;
import com.epam.mikle.vkapimvp.presenters.impl.MainActivityPresenter;
import com.epam.mikle.vkapimvp.views.MainMvpView;

public class MainActivity extends AppCompatActivity implements MainMvpView{
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = MainActivityPresenter.getInstance(this);
        presenter.onStart();
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
