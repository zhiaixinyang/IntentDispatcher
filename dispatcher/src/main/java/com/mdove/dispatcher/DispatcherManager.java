package com.mdove.dispatcher;

import android.app.Activity;
import android.content.Intent;

public class DispatcherManager implements DispatcherActivity.ActivityResultHandler {
    private static final int REQUEST_CODE = 51;

    public static void start(Activity context, Class activity, DispatcherHandler.RequestCallback callback) {
        Intent intent = new Intent(context, activity);
        DispatcherHandler.getInstance().requestData(context, intent, REQUEST_CODE, callback);
    }

    public static void start(Activity context,Intent intent, DispatcherHandler.RequestCallback callback) {
        DispatcherHandler.getInstance().requestData(context, intent, REQUEST_CODE, callback);
    }

    @Override
    public DispatcherResultIntent onActivityResult(int requestCode, int resultCode, Intent data) {
        DispatcherResultIntent result = null;
        if (requestCode == REQUEST_CODE) {
            result = new DispatcherResultIntent(true, resultCode, data);
        }
        return result;
    }
}
