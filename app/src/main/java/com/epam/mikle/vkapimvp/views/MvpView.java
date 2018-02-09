package com.epam.mikle.vkapimvp.views;

import android.app.Activity;

/**
 * Created by Mikle on 10.02.2018.
 */

public interface MvpView {
    Activity getActivity();
    void finishView();
}
