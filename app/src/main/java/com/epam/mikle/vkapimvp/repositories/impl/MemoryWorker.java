package com.epam.mikle.vkapimvp.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.epam.mikle.vkapimvp.models.Student;
import com.epam.mikle.vkapimvp.repositories.SavingRepository;
import com.epam.mikle.vkapimvp.util.StudentsCreator;
import com.epam.mikle.vkapimvp.util.VkUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MemoryWorker implements SavingRepository{
    private static MemoryWorker worker;
    private DbHelper dbHelper;

    //sqlite

    private MemoryWorker(final Context context){
        MemoryWorker.worker = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbHelper = new DbHelper(context);
            }
        }).start();
    }

    public static MemoryWorker getInstance(Context context){
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

    }

    @Override
    public List<Student> getStudents() {

        LinkedList<Student> students = new LinkedList<>();
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

    private class DbHelper extends SQLiteOpenHelper {
        static final String DB_COLUMN_FULL_NAME = "fullName";
        static final String DB_COLUMN_VK_DOMAIN = "vkDomain";
        static final String DB_COLUMN_VK_ID = "vkId";
        static final String DB_COLUMN_PHOTO_FILE_PATH = "photoFilePath";
        static final String DB_TABLE_NAME = "mytable";

        DbHelper(Context context) {
            super(context, "myDb", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "fullName text,"
                    + "vkDomain text,"
                    + "vkId text,"
                    + "photoFilePath text" + ");");
            List<Student> students = StudentsCreator.getBaseStudents();
            VkUtil.setupVkIds(students);
            for (Student student : students) {
                ContentValues cv = new ContentValues();
                cv.put(DbHelper.DB_COLUMN_FULL_NAME, student.fullName);
                cv.put(DbHelper.DB_COLUMN_VK_DOMAIN, student.vkDomain);
                if (student.vkId != null)
                    cv.put(DbHelper.DB_COLUMN_VK_ID, student.vkId);
                if (student.photo != null)
                    cv.put(DbHelper.DB_COLUMN_PHOTO_FILE_PATH, student.photo.getPath());
                db.insert(DbHelper.DB_TABLE_NAME, null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
