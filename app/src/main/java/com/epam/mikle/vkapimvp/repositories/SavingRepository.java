package com.epam.mikle.vkapimvp.repositories;

import com.epam.mikle.vkapimvp.models.Student;

import java.util.List;

public interface SavingRepository {
    void saveStudent(Student student);
    void updateStudents(Student student);
    List<Student> getStudents();
    void deleteStudent(Student student);
}
