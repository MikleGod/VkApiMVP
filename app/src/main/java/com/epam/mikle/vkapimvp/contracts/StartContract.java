package com.epam.mikle.vkapimvp.contracts;

import com.epam.mikle.vkapimvp.models.Student;

import java.util.List;

/**
 * Created by Mikle on 18.02.2018.
 */

public interface StartContract {
    interface StartMvpPresenter extends BaseContract.MvpPresenter {
        void onValidToken();
        void onInvalidToken();
    }
    interface StartMvpView extends BaseContract.MvpView {
        void loadMainActivity(List<Student> students);
        void makeToast(String message);
    }
}
