package com.epam.mikle.vkapimvp.repositories.impl;

import android.support.annotation.Nullable;
import android.util.Log;

import com.epam.mikle.vkapimvp.VKApplication;
import com.epam.mikle.vkapimvp.models.StudentPack;
import com.epam.mikle.vkapimvp.models.PhotoObserver;
import com.epam.mikle.vkapimvp.repositories.Observable;
import com.epam.mikle.vkapimvp.repositories.Observer;
import com.epam.mikle.vkapimvp.repositories.VkRepository;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.epam.mikle.vkapimvp.VKApplication.TAG;


public class VkWorker implements VkRepository, Observable {

    private List<Observer> observers = new ArrayList<>();


    @Override
    public void sendMessage(String message, String vkId, @Nullable final StudentPack studentPack) {
        final VKRequest request = new VKRequest(
                "messages.send",
                VKParameters.from(
                        VKApiConst.USER_ID,
                        Integer.parseInt(vkId),
                        VKApiConst.MESSAGE, message
                )
        );

        request.executeWithListener(new MyVKListener(request));
    }

    @Override
    public void getVKId(String vkDomain, final StudentPack studentPack) {

        VKRequest friendsRequest = new VKRequest(
                "search.getHints",
                VKParameters.from(
                        VKApiConst.Q,
                        vkDomain));

        Log.d(TAG, "getVKId: executing req " + studentPack.getStudent().fullName + " " + vkDomain);
        friendsRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                Log.d(TAG, "attemptFailed: " + attemptNumber + " total: " + totalAttempts + '\n' + request.toString());
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d(TAG, "attemptFailed: Error " + error.toString());
            }

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                int userId = 0;

                JSONObject object = response.json;
                try {
                    JSONArray responseArray = object.getJSONArray("response");
                    JSONObject userInfo = responseArray.getJSONObject(0);
                    JSONObject profile = userInfo.getJSONObject("profile");
                    String id = profile.getString("id");
                    userId = Integer.parseInt(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "attemptPassed: " + userId);
                notifyObservers(studentPack, userId);
            }
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPhoto(String vkId, PhotoObserver observer) {

    }


    @Override
    public void notifyObservers(StudentPack pack, int id) {
        for (Observer observer : observers) {
            Log.d(TAG, "notifyObservers: " + observer + " " + pack.getStudent());
            observer.update(pack, id);
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void deleteObserver(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }


    private static class MyVKListener extends VKRequest.VKRequestListener {
        VKRequest request;

        MyVKListener(VKRequest request) {
            super();
            this.request = request;
        }

        @Override
        public void onError(VKError error) {
            super.onError(error);
            Log.d(TAG, "onError: message " + error.toString());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            request.executeWithListener(new MyVKListener(request));
        }

        @Override
        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            super.attemptFailed(request, attemptNumber, totalAttempts);
            Log.d(TAG, "onFaild: totalAttempts " + totalAttempts);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            request.executeWithListener(new MyVKListener(request));
        }

        @Override
        public void onComplete(VKResponse response) {
            super.onComplete(response);
            Log.d(TAG, "onComplete: " + response.json.toString());
        }
    }
}
