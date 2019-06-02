package com.faust93.kocontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.Map;

/**
 * Created by faust93 on 22.08.13.
 */
public class BootReceiver extends BroadcastReceiver implements Constants {

    private static final String BOOT_ACTION_NAME = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (BOOT_ACTION_NAME.equals(intent.getAction()))
        {
            SharedPreferences prefs = context.getSharedPreferences(PREF_FILE_NAME, 0);
            if (prefs.getBoolean("onBoot",false)) {
                Map<String, ?> prefsMap = prefs.getAll();
                for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
                    if(entry.getKey().endsWith(".ko")){
                        if(entry.getValue().toString().contains("true")) {
                            Log.d(APP_TAG, "loadModule(): " + entry.getKey());
                            Utils.spawnCmd("insmod " + "/system/lib/modules/" + entry.getKey());
                        }
                    }
                }

            }
        }
    }
}
