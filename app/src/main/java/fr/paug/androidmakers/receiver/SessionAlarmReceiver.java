package fr.paug.androidmakers.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import fr.paug.androidmakers.service.SessionAlarmService;

public class SessionAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent scheduleIntent = new Intent(
                SessionAlarmService.ACTION_SCHEDULE_ALL_STARRED_BLOCKS,
                null, context, SessionAlarmService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(scheduleIntent);
        } else {
            context.startService(scheduleIntent);
        }
    }

}