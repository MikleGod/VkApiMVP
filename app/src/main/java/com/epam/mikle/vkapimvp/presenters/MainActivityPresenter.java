package com.epam.mikle.vkapimvp.presenters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.epam.mikle.vkapimvp.R;
import com.epam.mikle.vkapimvp.contracts.MainContract;
import com.epam.mikle.vkapimvp.models.Student;
import com.epam.mikle.vkapimvp.repositories.impl.MemoryWorker;
import com.epam.mikle.vkapimvp.repositories.impl.VkWorker;
import com.epam.mikle.vkapimvp.util.VkUtil;
import com.epam.mikle.vkapimvp.views.StartActivity;
import com.epam.mikle.vkapimvp.views.adapters.RVAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivityPresenter implements MainContract.MainMvpPresenter {

    private static MainActivityPresenter instance;
    private static MainContract.MainMvpView context;
    private RecyclerViewPresenter recyclerViewPresenter;

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
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        List<Student> students = recyclerViewPresenter.getStudents();
                                        if (!validateStudents(students)) {
                                            students = getStudentsFromMemory();
                                            recyclerViewPresenter.setStudents(students);
                                        }
                                        String message;
                                        EditText editText = (EditText) context.getActivity().findViewById(R.id.editText);
                                        message = createMessage(students, editText);
                                        //sendMessageForAll(message, students);
                                        try {
                                            messaging_test();
                                        } catch (Exception e) {
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

    public void messaging_test() throws Exception{
        VkWorker worker = new VkWorker();
        for (int i = 0; i< 30; i++){
            worker.sendMessage("Hello "+i, "139398744", null);
            Thread.sleep(2000);
        }
    }

    private boolean validateStudents(List<Student> students) {
        for (Student student : students) {
            if (student.isVkIdNull())
                return false;
        }
        return true;
    }

    private String createMessage(List<Student> students, EditText editText) {
        if ((editText.getText() + "").isEmpty()) {
            StringBuilder message = new StringBuilder("");
            for (Student student : students) {
                message.append(student.fullName).append(": ").append(student.randNumber).append('\n');
            }
            return message.toString();
        }
        return editText.getText() + "";
    }

    private void sendMessageForAll(String message, List<Student> students) {
        for (Student student : students) {
            if (!student.isOwner) {
                VkUtil.sendTo(student, message);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addStudent() {

    }

    private List<Student> getStudentsFromMemory() {
        MemoryWorker worker = MemoryWorker.getInstance(context.getActivity());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return worker.getStudents();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(Intent intent) {
        //context.showProgress();


        ArrayList<Parcelable> parcelableArrayListExtra = intent.getParcelableArrayListExtra(StartActivity.PARCELABLE_NAME);
        ArrayList<Student> students = new ArrayList<>();
        for (Parcelable parcelable : parcelableArrayListExtra) {
            students.add((Student) parcelable);
        }
        recyclerViewPresenter = new RecyclerViewPresenter(students, context);
        context.initRecView(recyclerViewPresenter);

        //context.hideProgress();
    }

}
