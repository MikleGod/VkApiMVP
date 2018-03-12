package com.epam.mikle.vkapimvp.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.epam.mikle.vkapimvp.models.Student;
import com.epam.mikle.vkapimvp.models.StudentPack;
import com.epam.mikle.vkapimvp.repositories.Observer;
import com.epam.mikle.vkapimvp.repositories.SavingRepository;
import com.epam.mikle.vkapimvp.util.StudentsCreator;
import com.epam.mikle.vkapimvp.util.VkUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.epam.mikle.vkapimvp.VKApplication.TAG;

public class MemoryWorker implements SavingRepository {
    private static MemoryWorker worker;
    private DbHelper dbHelper;
    private boolean isInited;

    //sqlite

    private MemoryWorker(final Context context) {
        MemoryWorker.worker = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbHelper = new DbHelper(context);
                isInited = true;
            }
        }).start();
    }

    public static MemoryWorker getInstance(Context context) {
        if (worker != null)
            return worker;
        return new MemoryWorker(context);
    }

    @Override
    public void saveStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.DB_COLUMN_FULL_NAME, student.fullName);
        cv.put(DbHelper.DB_COLUMN_VK_DOMAIN, student.vkDomain);
        if (student.vkId != null)
            cv.put(DbHelper.DB_COLUMN_VK_ID, student.vkId);
        if (student.photo != null)
            cv.put(DbHelper.DB_COLUMN_PHOTO_FILE_PATH, student.photo.getPath());
        db.insert(DbHelper.DB_TABLE_NAME, null, cv);
    }

    @Override
    public void updateStudents(Student student) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.DB_COLUMN_FULL_NAME, student.fullName);
        cv.put(DbHelper.DB_COLUMN_VK_DOMAIN, student.vkDomain);
        cv.put(DbHelper.DB_COLUMN_VK_ID, student.vkId);
        if (student.photo != null)
            cv.put(DbHelper.DB_COLUMN_PHOTO_FILE_PATH, student.photo.getPath());
        int i = dbHelper.getWritableDatabase().update(DbHelper.DB_TABLE_NAME, cv, DbHelper.DB_COLUMN_FULL_NAME + " = ?", new String[]{student.fullName});
        Log.d(TAG, "updateStudents: " + student.fullName + " " + i + " " + student.vkId);
    }

    @Override
    public List<Student> getStudents() {

        ArrayList<Student> students = new ArrayList<>();
        Log.d(TAG, "getStudents: w8g for init of dbHelper");
        while (!isInited){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "getStudents: dbHelper is inited");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DbHelper.DB_TABLE_NAME, null,
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int nameColIndex = cursor.getColumnIndex(DbHelper.DB_COLUMN_FULL_NAME);
            int domainColIndex = cursor.getColumnIndex(DbHelper.DB_COLUMN_VK_DOMAIN);
            int vkIdColIndex = cursor.getColumnIndex(DbHelper.DB_COLUMN_VK_ID);
            int filePathColIndex = cursor.getColumnIndex(DbHelper.DB_COLUMN_PHOTO_FILE_PATH);
            do {
                String name = cursor.getString(nameColIndex);
                String domain = cursor.getString(domainColIndex);
                String vkId = cursor.getString(vkIdColIndex);
                String filePath = cursor.getString(filePathColIndex);

                Student student = new Student(name, domain);
                student.vkId = vkId;
                if (!(filePath == null || filePath.equals("")))
                    student.photo = new File(filePath);
                students.add(student);
            } while (cursor.moveToNext());
        } else
            cursor.close();


        return students;
    }

    @Override
    public void deleteStudent(Student student) {
        int i = dbHelper.getWritableDatabase().delete(
                DbHelper.DB_TABLE_NAME,
                DbHelper.DB_COLUMN_FULL_NAME + " = ?",
                new String[]{student.fullName}
        );
        Log.d(TAG, "deleteStudent: " + i + " " + student.fullName);
    }

    private class DbHelper extends SQLiteOpenHelper implements Observer {
        private List<Student> students;
        static final String DB_COLUMN_FULL_NAME = "fullName";
        static final String DB_COLUMN_VK_DOMAIN = "vkDomain";
        static final String DB_COLUMN_VK_ID = "vkId";
        static final String DB_COLUMN_PHOTO_FILE_PATH = "photoFilePath";
        static final String DB_TABLE_NAME = "mytable";
        SQLiteDatabase db;

        DbHelper(Context context) {
            super(context, "myDb", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            this.db = db;
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "fullName text,"
                    + "vkDomain text,"
                    + "vkId text,"
                    + "photoFilePath text" + ");");
            students = StudentsCreator.getBaseStudents();
            for (Student student : students) {
                Log.d(TAG, "onCreate: write in DB" + student.fullName + " " + student.vkId);
                ContentValues cv = new ContentValues();
                cv.put(DB_COLUMN_FULL_NAME, student.fullName);
                cv.put(DbHelper.DB_COLUMN_VK_DOMAIN, student.vkDomain);
                if (student.vkId != null)
                    cv.put(DbHelper.DB_COLUMN_VK_ID, student.vkId);
                if (student.photo != null)
                    cv.put(DbHelper.DB_COLUMN_PHOTO_FILE_PATH, student.photo.getPath());
                db.insert(DB_TABLE_NAME, null, cv);
            }
            VkUtil.register(this);
            VkUtil.setupVkIds(students);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        @Override
        public void update(StudentPack pack, int id) {
            students.get(students.indexOf(pack.getStudent())).vkId = id + "";
            ContentValues cv = new ContentValues();
            cv.put(DB_COLUMN_FULL_NAME, pack.getStudent().fullName);
            cv.put(DbHelper.DB_COLUMN_VK_DOMAIN, pack.getStudent().vkDomain);
            cv.put(DbHelper.DB_COLUMN_VK_ID, id);
            if (pack.getStudent().photo != null)
                cv.put(DbHelper.DB_COLUMN_PHOTO_FILE_PATH, pack.getStudent().photo.getPath());
            int i = db.update(DB_TABLE_NAME, cv, DB_COLUMN_FULL_NAME + " = ?", new String[]{pack.getStudent().fullName});
            Log.d(TAG, "update: " + pack.getStudent().fullName + " " + i + " " + id);
        }
    }
}
