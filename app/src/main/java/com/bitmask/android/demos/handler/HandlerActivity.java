package com.bitmask.android.demos.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bitmask.android.demos.R;

public class HandlerActivity extends AppCompatActivity {

    private static final String TAG  = HandlerActivity.class.getSimpleName();

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_handler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.text_handler);

        findViewById(R.id.btn_handler).setOnClickListener(v -> {
            new Thread(new Runnable() {
                public void run() {
                    // Perform long-running task here, such as downloading a file

                    for (int i = 0; i < 5; ++i) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        // Once the task is complete, update the UI
                        int finalI = i;
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // Update UI here, such as displaying the downloaded file
                                textView.setText("progress :" + finalI);
                            }
                        });
                    }
                }
            }).start();

        });

        findViewById(R.id.btn_handler2).setOnClickListener(v -> {

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        runBackgroundThread();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        interruptBackgroundThread();
    }

    BackgroundThread backgroundThread;
    private void runBackgroundThread() {
        backgroundThread = new BackgroundThread();
        backgroundThread.run();
    }

    private void interruptBackgroundThread() {
        backgroundThread.interrupt();
        backgroundThread = null;
    }

    public class BackgroundThread extends Thread{
        private final String TAG = BackgroundThread.class.getSimpleName();
        private Handler handler;
        @Override
        public void run() {
            super.run();
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            handler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    Log.d(TAG, "Handler handleMessage " + msg.toString());
                }
            };
            Looper.loop();
            Log.d(TAG, "Thread is killed ?");
        }

        public void addTaskToMessageQueue(Runnable task) {
            handler.post(task);
        }
    }








}