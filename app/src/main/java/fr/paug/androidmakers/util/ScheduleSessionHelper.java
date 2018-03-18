package fr.paug.androidmakers.util;

import fr.paug.androidmakers.ui.adapter.ScheduleSession;

public class ScheduleSessionHelper {

    public static final long ALLOWED_OVERLAP = 5 * 60 * 1000; // 5 minutes

    public static boolean sameStartTime(ScheduleSession block1, ScheduleSession block2, boolean useOverlap) {
        return Math.abs(block1.getStartTimestamp() - block2.getStartTimestamp()) <= (useOverlap ? ALLOWED_OVERLAP : 0);
    }

}