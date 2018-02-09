package com.epam.mikle.vkapimvp.models;

/**
 * Created by Mikle on 10.02.2018.
 */

public class Student {
    public final String fullName;
    public final String vkDomain;
    public int randNumber;
    public String vkId;

    public Student(String fullName, String vkDomain){
        this.fullName = fullName;
        this.vkDomain = vkDomain;
    }

    public boolean isRandNumberNull(){
        return randNumber == 0;
    }

    public boolean isVkIdNull(){
        return vkId == null;
    }
}
