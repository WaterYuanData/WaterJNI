package com.yuan.waterjni.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PermissonUtils {
    private static final String TAG = "PermissonUtils";
    private static PermissonUtils instance = new PermissonUtils();

    public static PermissonUtils getInstance() {
        return instance;
    }

    public void checkAndRequestPermissions(Activity activity, String[] permissions, int requestCode) {
        List<String> needRequest = new ArrayList();
        for (int i = 0; i < permissions.length; i++) {
            if (activity.getApplicationContext().checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "checkAndRequestPermissions: needRequest=" + permissions[i]);
                needRequest.add(permissions[i]);
            }
        }
        if (needRequest.size() > 0) {
            String[] ps = new String[needRequest.size()];
            activity.requestPermissions(needRequest.toArray(ps), requestCode);
        }
    }
}
