package com.epam.mikle.vkapimvp.repositories.impl;

import android.widget.Toast;

import com.epam.mikle.vkapimvp.models.Observer;
import com.epam.mikle.vkapimvp.models.PhotoObserver;
import com.epam.mikle.vkapimvp.models.Student;
import com.epam.mikle.vkapimvp.repositories.VkRepository;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class VkWorker implements VkRepository {


    @Override
    public void sendMessage(String message, String vkId, final Observer observer) {
        VKRequest request = new VKRequest(
                "messages.send",
                VKParameters.from(
                        VKApiConst.USER_ID,
                        Integer.parseInt(vkId),
                        VKApiConst.MESSAGE, message
                )
        );

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
            }
        });
    }

    @Override
    public void getVKId(String vkDomain, final Observer observer) {

        VKRequest friendsRequest = new VKRequest(
                "search.getHints",
                VKParameters.from(
                        VKApiConst.Q,
                        vkDomain));

        friendsRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
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

                if (userId != 0)
                    observer.response = "" + userId;
            }
        });

    }

    @Override
    public void getPhoto(String vkId, PhotoObserver observer) {

    }


}
