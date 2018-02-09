package com.epam.mikle.vkapimvp.repositories;

import com.epam.mikle.vkapimvp.models.Student;

import java.util.List;

/**
 * Created by Mikle on 10.02.2018.
 */

public interface SavingRepository {
    void saveStudents(List<Student> students);
    void updateStudents(List<Student> students);
    List<Student> getStudents();
}
