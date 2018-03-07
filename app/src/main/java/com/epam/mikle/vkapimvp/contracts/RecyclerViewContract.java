package com.epam.mikle.vkapimvp.contracts;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mikle on 14.02.2018.
 */

public interface RecyclerViewContract {
    interface StudentsPresenter{
        void onBindViewHolder(int position, StudentsView view, RecyclerView.Adapter adapter);
        int getStudentsCount();
    }
    interface StudentsView{
        void setId(int id);
        void setFullName(String fullName);
        void setVkDomain(String vkDomain);
        void setContextMenu(View.OnCreateContextMenuListener listener);
        void setRandomNumber(int randomNumber);
    }
}
