package com.epam.mikle.vkapimvp.repositories;

import com.epam.mikle.vkapimvp.models.Observer;
import com.epam.mikle.vkapimvp.models.PhotoObserver;
import com.epam.mikle.vkapimvp.models.Student;

import java.util.List;

/**
 * Created by Mikle on 10.02.2018.
 */

public interface VkRepository {
    String VK_SENDING_REQUEST_GONE = "com.epam.mikle.vkapimvp.repositories.VK_SENDING_REQUEST_GONE";
    String VK_SENDING_REQUEST_ERROR = "com.epam.mikle.vkapimvp.repositories.VK_SENDING_REQUEST_ERROR";
    void sendMessage(String message, String vkId, Observer observer);
    void getVKId(String vkDomain, Observer observer);
    void getPhoto(String vkId, PhotoObserver observer);
}
