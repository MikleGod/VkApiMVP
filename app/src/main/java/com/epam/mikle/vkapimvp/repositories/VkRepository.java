package com.epam.mikle.vkapimvp.repositories;

import com.epam.mikle.vkapimvp.models.Student;

import java.util.List;

/**
 * Created by Mikle on 10.02.2018.
 */

public interface VkRepository {
    String VK_SENDING_REQUEST_GONE = "com.epam.mikle.vkapimvp.repositories.VK_SENDING_REQUEST_GONE";
    String VK_SENDING_REQUEST_ERROR = "com.epam.mikle.vkapimvp.repositories.VK_SENDING_REQUEST_ERROR";
    void sendMessages(List<Student> students);
    void sendResultBroadcast();
}
