package com.epam.mikle.vkapimvp.repositories;

import com.epam.mikle.vkapimvp.models.StudentPack;

/**
 * Created by Сергей on 10.03.2018.
 */

public interface Observer {
    void update(StudentPack pack, int id);
}
