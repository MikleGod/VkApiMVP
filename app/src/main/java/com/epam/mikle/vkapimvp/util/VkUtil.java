package com.epam.mikle.vkapimvp.util;

import com.epam.mikle.vkapimvp.models.StudentPack;
import com.epam.mikle.vkapimvp.models.Student;
import com.epam.mikle.vkapimvp.repositories.Observer;
import com.epam.mikle.vkapimvp.repositories.impl.VkWorker;

import java.util.List;

/**
 * Created by Mikle on 19.02.2018.
 */

public class VkUtil {
    private static VkWorker worker = new VkWorker();

    private VkUtil() {
    }

    public static void register(Observer o){
        worker.registerObserver(o);
    }

    public static void unRegister(Observer o){
        worker.deleteObserver(o);
    }
    public static void setupVkId(Student student) {

        StudentPack studentPack = new StudentPack(student);
        worker.getVKId(student.vkDomain, studentPack);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void setupVkIds(List<Student> students) {
        for (Student student : students) {

            setupVkId(student);
        }
    }

    public static void sendTo(Student student, String message) {
        if (!student.isVkIdNull())
            worker.sendMessage(message, student.vkId, null);
    }
}
