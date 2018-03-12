package com.epam.mikle.vkapimvp.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.epam.mikle.vkapimvp.contracts.MainContract;
import com.epam.mikle.vkapimvp.contracts.RecyclerViewContract;
import com.epam.mikle.vkapimvp.models.Student;
import com.epam.mikle.vkapimvp.repositories.impl.MemoryWorker;
import com.vk.sdk.VKAccessToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mikle on 14.02.2018.
 */

public class RecyclerViewPresenter implements RecyclerViewContract.StudentsPresenter {
    private final MainContract.MainMvpView context;
    private RecyclerView.Adapter adapter;
    private ArrayList<Integer> randoms = new ArrayList<>();

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    private Random random = new Random();

    RecyclerViewPresenter(List<Student> students, MainContract.MainMvpView context) {
        this.students = new ArrayList<>(students);
        this.context = context;
        makeRandom();
        initStudentRands();
        findOwner();
    }

    private void makeRandom() {
        int size = students.size();
        randoms.clear();
        for (int i = 0; i < size; i++) {
            int temp = Math.abs(random.nextInt()) % size + 1;
            while (randoms.contains(temp)) {
                temp = Math.abs(random.nextInt()) % size + 1;
            }
            randoms.add(temp);
        }
    }

    private void findOwner() {
        VKAccessToken token = VKAccessToken.currentToken();
        for (Student student : students) {
            if (!student.isVkIdNull() && student.vkId.equals(token.userId + ""))
                student.isOwner = true;
        }
    }

    private void initStudentRands() {
        int size = students.size();
        for (int i = 0; i < size; i++) {
            students.get(i).randNumber = randoms.get(i);
        }
    }


    @Override
    public void onBindViewHolder(final int position, final RecyclerViewContract.StudentsView view, final RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        view.setFullName(students.get(position).fullName);
        view.setVkDomain(students.get(position).vkDomain);
        view.setRandomNumber(students.get(position).randNumber);
        view.setId(position);
        view.setContextMenu(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Удалить").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                    int pos = position;

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        MemoryWorker.getInstance(context.getActivity()).deleteStudent(students.get(pos));
                        students.remove(pos);
                        makeRandom();
                        initStudentRands();
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

    @Override
    public void setStudents(List<Student> students) {
        this.students = students;
        initStudentRands();
        findOwner();
        //adapter.notifyDataSetChanged();
    }
}
