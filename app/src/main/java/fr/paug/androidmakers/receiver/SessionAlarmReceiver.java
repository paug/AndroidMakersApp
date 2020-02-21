package fr.paug.androidmakers.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import fr.paug.androidmakers.service.SessionAlarmService;

// On boot, reschedule all favorites sessions
public class SessionAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (SessionAlarmService.Companion.getACTION_NOTIFY_SESSION().equals(action)) {
            Intent scheduleIntent = new Intent(SessionAlarmService.Companion.getACTION_NOTIFY_SESSION())
                    .setData(intent.getData())
                    .putExtras(intent);
            SessionAlarmService.Companion.enqueueWork(context, scheduleIntent);
        } else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Intent scheduleIntent = new Intent(SessionAlarmService.Companion.getACTION_SCHEDULE_ALL_STARRED_BLOCKS());
            SessionAlarmService.Companion.enqueueWork(context, scheduleIntent);
        }
    }

}