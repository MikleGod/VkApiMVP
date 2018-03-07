package com.epam.mikle.vkapimvp.presenters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.epam.mikle.vkapimvp.contracts.RecyclerViewContract;
import com.epam.mikle.vkapimvp.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mikle on 14.02.2018.
 */

public class RecyclerViewPresenter implements RecyclerViewContract.StudentsPresenter {

    private List<Student> students;
    private List<Integer> randoms;

    private Random random = new Random();

    RecyclerViewPresenter(List<Student> students){
        this.students = new ArrayList<>(students);
        randoms = new ArrayList<>();
        makeRandom();
    }

    private void makeRandom() {
        int size = students.size();
        for (int i = 0; i<size; i++) {
            int temp = Math.abs(random.nextInt())%size + 1;
            while (randoms.contains(temp)){
                temp = Math.abs(random.nextInt())%size + 1;
            }
            randoms.add(temp);
        }
    }


    @Override
    public void onBindViewHolder(final int position, final RecyclerViewContract.StudentsView view, final RecyclerView.Adapter adapter) {
        view.setFullName(students.get(position).fullName);
        view.setVkDomain(students.get(position).vkDomain);
        view.setRandomNumber(randoms.get(position));
        view.setId(position);
        view.setContextMenu(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Удалить").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                    int pos = position;

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        students.remove(pos);
                        randoms.clear();
                        makeRandom();
                        adapter.notifyDataSetChanged();
                        //Memory observ
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public int getStudentsCount() {
        return students.size();
    }
}
