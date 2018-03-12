package com.epam.mikle.vkapimvp.repositories;

import com.epam.mikle.vkapimvp.models.StudentPack;

/**
 * Created by Сергей on 10.03.2018.
 */

public interface Observable {
    void notifyObservers(StudentPack pack, int id);
    void registerObserver(Observer observer);
    void deleteObserver(Observer observer);
}
