package com.epam.mikle.vkapimvp.presenters;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.epam.mikle.vkapimvp.contracts.MainContract;
import com.epam.mikle.vkapimvp.models.Student;
import com.epam.mikle.vkapimvp.repositories.impl.MemoryWorker;
import com.epam.mikle.vkapimvp.views.adapters.RVAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivityPresenter implements MainContract.MainMvpPresenter {

    private static MainActivityPresenter instance;
    private static MainContract.MainMvpView context;

    private MainActivityPresenter(MainContract.MainMvpView context) {
        instance = this;
        MainActivityPresenter.context = context;
    }

    public static MainActivityPresenter getInstance(MainContract.MainMvpView context) {
        if (instance == null)
            return new MainActivityPresenter(context);
        else
            return instance;
    }


    @Override
    public void onStart() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("hui", "hui@com"));
        list.add(new Student("huo", "huo@by"));
        list.add(new Student("hup", "hup@by"));

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onRecAdapterCreate(RVAdapter adapter) {
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public View.OnClickListener onFabInit() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Отправляем Результаты", Snackbar.LENGTH_LONG)
                        .setAction("Отправить", new View.OnClickListener() {
                            @SuppressLint("StaticFieldLeak")
                            @Override
                            public void onClick(View v) {
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                        context.showProgress();
                                    }

                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        try {
                                            Thread.sleep(10000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void aVoid) {
                                        super.onPostExecute(aVoid);
                                        context.hideProgress();
                                        Toast.makeText(context.getActivity(), "Отправлено!", Toast.LENGTH_LONG).show();
                                    }
                                }.execute();
                            }
                        }).show();

            }
        };
    }

    @Override
    public void addStudent() {

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate() {
        context.showProgress();
        MemoryWorker worker = MemoryWorker.getInstance(context.getActivity());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Student> students = worker.getStudents();

        RecyclerViewPresenter presenter = new RecyclerViewPresenter(students);
        context.initRecView(presenter);

        context.hideProgress();
        Toast.makeText(context.getActivity(), "Отправлено!", Toast.LENGTH_LONG).show();
    }

}
