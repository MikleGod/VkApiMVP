package com.epam.mikle.vkapimvp.models;

/**
 * Created by Mikle on 13.02.2018.
 */

public class StudentPack {
    public StudentPack(Student student){
        this.student = student;
    }
    private Student student;
    public synchronized Student getStudent(){
        return student;
    }

}
