package com.epam.mikle.vkapimvp.repositories;

import com.epam.mikle.vkapimvp.models.StudentPack;
import com.epam.mikle.vkapimvp.models.PhotoObserver;

/**
 * Created by Mikle on 10.02.2018.
 */

public interface VkRepository {
    String VK_SENDING_REQUEST_GONE = "com.epam.mikle.vkapimvp.repositories.VK_SENDING_REQUEST_GONE";
    String VK_SENDING_REQUEST_ERROR = "com.epam.mikle.vkapimvp.repositories.VK_SENDING_REQUEST_ERROR";
    void sendMessage(String message, String vkId, StudentPack studentPack);
    void getVKId(String vkDomain, StudentPack studentPack);
    void getPhoto(String vkId, PhotoObserver observer);
}
