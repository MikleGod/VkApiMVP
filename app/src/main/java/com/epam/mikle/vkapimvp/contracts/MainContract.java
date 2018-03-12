package com.epam.mikle.vkapimvp.contracts;

import android.content.Intent;
import android.view.View;

import com.epam.mikle.vkapimvp.views.adapters.RVAdapter;

/**
 * Created by Mikle on 18.02.2018.
 */

public interface MainContract {
    interface MainMvpView extends BaseContract.MvpView {
        void initRecView(RecyclerViewContract.StudentsPresenter presenter);
        void notifyUI();
        void addingStudentUI();
        void showProgress();
        void hideProgress();
    }

    interface MainMvpPresenter extends BaseContract.MvpPresenter {
        void onRecAdapterCreate(RVAdapter adapter);
        void onBackPressed();
        View.OnClickListener onFabInit();
        void addStudent();
        void onCreate(Intent intent);
    }
}
