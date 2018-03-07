package com.epam.mikle.vkapimvp.util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.epam.mikle.vkapimvp.models.Observer;
import com.epam.mikle.vkapimvp.models.Student;
import com.epam.mikle.vkapimvp.repositories.impl.VkWorker;

import java.util.List;

/**
 * Created by Mikle on 19.02.2018.
 */

public class VkUtil {
    private static VkWorker worker = new VkWorker();
    private VkUtil(){}

    public static void setupVkId(Student student){

        Observer observer = new Observer();
        worker.getVKId(student.vkDomain, observer);
        while (observer.response != null){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        student.vkId = observer.response;
    }

    public static void setupVkIds(List<Student> students){
        for (Student student : students) {
            Observer observer = new Observer();
            worker.getVKId(student.vkDomain, observer);
            while (observer.response != null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            student.vkId = observer.response;
        }
    }

    public static void sendTo(Student student, String message){
        worker.sendMessage(message, student.vkId, new Observer());
    }
}
