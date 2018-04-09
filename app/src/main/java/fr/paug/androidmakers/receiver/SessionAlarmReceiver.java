package fr.paug.androidmakers.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import fr.paug.androidmakers.service.SessionAlarmService;

// On boot, reschedule all favorites sessions
public class SessionAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent scheduleIntent = new Intent(
                SessionAlarmService.ACTION_SCHEDULE_ALL_STARRED_BLOCKS,
                null, context, SessionAlarmService.class);
        ContextCompat.startForegroundService(context, scheduleIntent);
    }

}