package com.mdove.dispatcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DispatcherActivity extends Activity {
    private static final String PARAM_RAW_INTENT = "raw_intent";
    private static final String PARAM_RAW_REQUEST_CODE = "raw_request_code";

    private int mRequestCode = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent wrapIntent = getIntent();
        Intent rawIntent = wrapIntent.getParcelableExtra(PARAM_RAW_INTENT);
        mRequestCode = wrapIntent.getIntExtra(PARAM_RAW_REQUEST_CODE, -1);
        if (mRequestCode == -1 || rawIntent == null) {
            finish();
            return;
        }
        startActivityForResult(rawIntent, mRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<ActivityResultHandler> handlerList = getActivityResultHandler();
        for (ActivityResultHandler handler : handlerList) {
            DispatcherResultIntent result = handler.onActivityResult(requestCode, resultCode, data);
            if (result != null && result.mConsume) {
                DispatcherHandler.getInstance().notifyResult(result);
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Intent wrapIntent(Context context, Intent rawIntent, int requestCode) {
        Intent intent = new Intent(context, DispatcherActivity.class);
        intent.putExtra(PARAM_RAW_INTENT, rawIntent);
        intent.putExtra(PARAM_RAW_REQUEST_CODE, requestCode);
        return intent;
    }

    public List<ActivityResultHandler> getActivityResultHandler() {
        List<ActivityResultHandler> handlerList = new ArrayList<>();
        handlerList.add(new DispatcherManager());
        return handlerList;
    }

    public interface ActivityResultHandler {
        DispatcherResultIntent onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
