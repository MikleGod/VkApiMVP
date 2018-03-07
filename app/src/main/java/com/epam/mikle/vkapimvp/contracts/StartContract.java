package com.epam.mikle.vkapimvp.contracts;

/**
 * Created by Mikle on 18.02.2018.
 */

public interface StartContract {
    interface StartMvpPresenter extends BaseContract.MvpPresenter {

    }
    interface StartMvpView extends BaseContract.MvpView {
        void loadMainActivity();
    }
}
