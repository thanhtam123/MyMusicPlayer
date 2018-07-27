package com.example.admin.mymusicplayer.utility;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by TamTT on 7/19/2018.
 */

public class NotificationUtils {
    public static final int NOTIFICATION_ID = 1;

    public static final String ACTION_1 = "action_1";



    public static class NotificationActionService extends IntentService {
        public NotificationActionService() {
            super(NotificationActionService.class.getSimpleName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            String action = intent.getAction();
            //DebugUtils.log("Received notification action: " + action);
            if (ACTION_1.equals(action)) {
                //NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID);
                Log.e("TAG","ACTION");
            }
        }
    }
}
