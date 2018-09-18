package com.mdove.intentdispatcher;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mdove.dispatcher.DispatcherHandler;
import com.mdove.dispatcher.DispatcherManager;
import com.mdove.dispatcher.DispatcherResultIntent;

public class MainActivity extends AppCompatActivity {
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.btn);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DispatcherManager.start(MainActivity.this, SecondActivity.class, new DispatcherHandler.RequestCallback() {
                    @Override
                    public void onResult(DispatcherResultIntent result) {
                        Intent intent = (Intent) result.mData;
                        mTv.setText(intent.getStringExtra("AAA"));
                    }
                });
            }
        });
    }
}
