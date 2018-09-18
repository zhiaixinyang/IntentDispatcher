package com.mdove.dispatcher;

import android.app.Activity;
import android.content.Intent;

import java.util.Stack;

public class DispatcherHandler {
    private static DispatcherHandler mDeliveryHandler;
    private Stack<RequestCallback> mCallBackStack = new Stack<>();
    public static DispatcherHandler getInstance() {
        if(mDeliveryHandler == null) {
            mDeliveryHandler = new DispatcherHandler();
        }

        return mDeliveryHandler;
    }

    public void requestData(Activity context, Intent rawIntent, int requestCode, RequestCallback callback) {
        mCallBackStack.push(callback);
        Intent wrapIntent = DispatcherActivity.wrapIntent(context, rawIntent, requestCode);
        context.startActivityForResult(wrapIntent, requestCode);
    }

    public void notifyResult(DispatcherResultIntent result) {
        RequestCallback callback = null;
        if (!mCallBackStack.isEmpty()) {
            callback = mCallBackStack.pop();
        }
        if (callback != null) {
            callback.onResult(result);
        }
    }

    public interface RequestCallback {
        void onResult(DispatcherResultIntent result);
    }
}
