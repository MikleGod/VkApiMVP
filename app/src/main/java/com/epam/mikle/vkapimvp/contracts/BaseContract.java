package com.epam.mikle.vkapimvp.contracts;

import android.app.Activity;

/**
 * Created by Mikle on 18.02.2018.
 */

public interface BaseContract {
    interface MvpPresenter {
        void onStart();
        void onFinish();
    }
    interface MvpView {
        Activity getActivity();
        void finishView();
    }
}
