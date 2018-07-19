package com.example.admin.mymusicplayer.utility;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by TamTT on 7/18/2018.
 */

public class SongManager {
    public static boolean isServiceRunning(String serviceName, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if(serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
