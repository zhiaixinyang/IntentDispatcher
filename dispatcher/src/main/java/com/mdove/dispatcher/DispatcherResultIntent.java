package com.mdove.dispatcher;

import android.os.Parcelable;

public class DispatcherResultIntent {
    public boolean mConsume;
    public int mResultCode = -1;
    public Parcelable mData;

    public DispatcherResultIntent(boolean consume, int resultCode, Parcelable data) {
        mConsume = consume;
        mResultCode = resultCode;
        mData = data;
    }
}
