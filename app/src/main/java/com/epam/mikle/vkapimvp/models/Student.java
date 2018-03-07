package com.epam.mikle.vkapimvp.models;

import java.io.File;

public class Student {
    public final String fullName;
    public final String vkDomain;
    public int randNumber;
    public String vkId;
    public File photo;

    public Student(String fullName, String vkDomain){
        this.fullName = fullName;
        this.vkDomain = vkDomain;
    }

    public boolean isRandNumberNull(){
        return randNumber == 0;
    }

    public boolean isVkIdNull(){
        return (vkId == null || vkId.equals(""));
    }
}
