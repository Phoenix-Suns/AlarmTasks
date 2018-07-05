package com.windyroad.nghia.common.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Nghia-PC on 7/16/2015.
 */
public class BaseIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BaseIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
