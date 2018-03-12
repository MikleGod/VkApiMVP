package com.epam.mikle.vkapimvp.views;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.epam.mikle.vkapimvp.VKApplication;
import com.epam.mikle.vkapimvp.contracts.StartContract;
import com.epam.mikle.vkapimvp.models.Student;
import com.epam.mikle.vkapimvp.presenters.StartActivityPresenter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity implements StartContract.StartMvpView {

    private StartActivityPresenter presenter;
    public static final String PARCELABLE_NAME = "Students";
    private String[] scope = new String[]{
            VKScope.PAGES,
            VKScope.GROUPS,
            VKScope.MESSAGES,
            VKScope.FRIENDS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VKSdk.login(this, scope);
        Log.d("StartActivity", "onPause");
        presenter = StartActivityPresenter.getInstance(this);
        //presenter.onStart();
    }


    @Override
    public void loadMainActivity(List<Student> students) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putParcelableArrayListExtra(PARCELABLE_NAME, (ArrayList<Student>)students);
        startActivity(intent);
    }

    @Override
    public void makeToast(String message) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
// Пользователь успешно авторизовался
                presenter.onValidToken();
                Log.d(VKApplication.TAG, "onResult: Valid login");
            }

            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                presenter.onInvalidToken();
                Log.d(VKApplication.TAG, "onErrorLogin: " + error.toString());
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        presenter.onStart();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void finishView() {
        Log.d("StartActivity", "finish " + getApplicationInfo().taskAffinity);
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
