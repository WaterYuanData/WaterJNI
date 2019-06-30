package com.yuan.waterjni;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: " + BuildConfig.VERSION_NAME);
        final TextView tv = findViewById(R.id.sample_text);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example of a call to a native method
                tv.setText(stringFromJNI());
            }
        });
        tv.setText("当前版本：" + BuildConfig.VERSION_NAME);
        findViewById(R.id.diff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                long s = System.currentTimeMillis();
                diff(old, newp, patch);
                long s1 = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "生成差分包成功，用时:" + (s1 - s) + "ms", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.patch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                long s2 = System.currentTimeMillis();
                patch(old, tmp, patch);
                long s3 = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "差分包合并成功，用时:" + (s3 - s2) + "ms", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //旧版本
    String old = getsdpath() + "old.apk";
    //新版本
    String newp = getsdpath() + "new.apk";
    //差分包
    String patch = getsdpath() + "patch.patch";
    //旧版apk和差分包合并生成的新版apk
    String tmp = getsdpath() + "new2.apk";

    private String getsdpath() {
        Log.i(TAG, "getsdpath: " + Environment.getExternalStorageDirectory().getPath());
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    //生成差分包
    public native int diff(String oldpath, String newpath, String patch);

    //旧apk和差分包合并
    public native int patch(String oldpath, String newpath, String patch);

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
