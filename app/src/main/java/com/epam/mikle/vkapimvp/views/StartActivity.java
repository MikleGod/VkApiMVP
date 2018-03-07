package com.epam.mikle.vkapimvp.views;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.epam.mikle.vkapimvp.contracts.StartContract;
import com.epam.mikle.vkapimvp.presenters.StartActivityPresenter;

public class StartActivity extends AppCompatActivity implements StartContract.StartMvpView {

    private StartActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("StartActivity", "onPause");
        presenter = StartActivityPresenter.getInstance(this);
        presenter.onStart();
    }


    @Override
    public void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void finishView() {
        Log.d("StartActivity", "finish "+getApplicationInfo().taskAffinity);
        finish();
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d("StartActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("StartActivity", "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("StartActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("StartActivity", "onResume");
    }
}
