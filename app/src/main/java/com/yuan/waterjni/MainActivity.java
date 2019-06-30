package com.yuan.waterjni;

import android.os.AsyncTask;
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
                Log.i(TAG, "onClick: " + old);
                Log.i(TAG, "onClick: old=" + new File(old).exists());
                Log.i(TAG, "onClick: " + newp);
                Log.i(TAG, "onClick: newp=" + new File(newp).exists());
                Log.i(TAG, "onClick: " + patch); // /storage/emulated/0/_ae/patch.diff
                Log.i(TAG, "onClick: patch=" + new File(patch).exists()); // true
                Log.i(TAG, "onClick: " + tmp);
                Log.i(TAG, "onClick: tmp=" + new File(tmp).exists());

                Log.i(TAG, "onClick: " + tmp);

                Log.i(TAG, "onClick: " + tmp);
                Log.i(TAG, "onClick: " + tmp);
                Log.i(TAG, "onClick: " + tmp);
                Log.i(TAG, "onClick: " + tmp);
                Log.i(TAG, "onClick: " + tmp);
                Log.i(TAG, "onClick: " + tmp);
                Log.i(TAG, "onClick: " + tmp);
                Log.i(TAG, "onClick: " + tmp);

            }
        });
        tv.setText("当前版本：" + BuildConfig.VERSION_NAME);
        findViewById(R.id.diff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                new AsyncTask<String, Integer, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        for (int i = 0; i < strings.length; i++) {
                            Log.i(TAG, "doInBackground: 参数" + i + "=" + strings[i]);
                        }
                        publishProgress(0);
                        long s = System.currentTimeMillis();
                        diff(old, newp, patch);
                        long s1 = System.currentTimeMillis();
                        // TODO: 2019/6/30
                        return "" + (s1 - s);
                    }

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        super.onProgressUpdate(values);
                        tv.setText("...");
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        Log.i(TAG, "run: " + "生成差分包成功，用时:" + s + "ms"); // run: 生成差分包成功，用时:2946ms
                        Toast.makeText(MainActivity.this, "生成差分包成功，用时:" + s + "ms", Toast.LENGTH_SHORT).show();
                    }
                }.execute("1", "2", "3", "go");
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
                tv.setText(BuildConfig.VERSION_NAME);
            }
        });
    }

    //旧版本
    String old = getsdpath() + "old.apk";
    //新版本
    String newp = getsdpath() + "new.apk";
    //差分包
    String patch = getsdpath() + "patch.diff";
    //旧版apk和差分包合并生成的新版apk
    String tmp = getsdpath() + "new2.apk";

    private String getsdpath() {
        Log.i(TAG, "getsdpath: " + Environment.getExternalStorageDirectory().getPath());
        return Environment.getExternalStorageDirectory().getPath() + "/_ae/";
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
