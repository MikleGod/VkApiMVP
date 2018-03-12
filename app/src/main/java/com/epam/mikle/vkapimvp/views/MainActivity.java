package com.epam.mikle.vkapimvp.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.epam.mikle.vkapimvp.R;
import com.epam.mikle.vkapimvp.contracts.MainContract;
import com.epam.mikle.vkapimvp.contracts.RecyclerViewContract;
import com.epam.mikle.vkapimvp.presenters.MainActivityPresenter;
import com.epam.mikle.vkapimvp.views.adapters.RVAdapter;

public class MainActivity extends AppCompatActivity implements MainContract.MainMvpView{

    MainContract.MainMvpPresenter presenter;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = MainActivityPresenter.getInstance(this);
        Log.d("MainActivity", "onCreate");
        initViews();
        presenter.onCreate(getIntent());
    }

    private void initViews(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(presenter.onFabInit());
    }



    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void initRecView(RecyclerViewContract.StudentsPresenter presenter) {
        RecyclerView rv = findViewById(R.id.students_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        RVAdapter adapter = new RVAdapter(presenter);
        this.presenter.onRecAdapterCreate(adapter);
        rv.setAdapter(adapter);

    }

    @Override
    public void notifyUI() {

    }

    @Override
    public void addingStudentUI() {

    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            try {
                progressDialog = ProgressDialog.show(this, "", "Подождите!");
                progressDialog.setCancelable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
