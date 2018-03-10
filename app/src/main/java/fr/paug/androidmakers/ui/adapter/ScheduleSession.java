package fr.paug.androidmakers.ui.adapter;

import fr.paug.androidmakers.model.ScheduleSlot;

public class ScheduleSession {

    private ScheduleSlot mScheduleSlot;
    private String mTitle;

    public ScheduleSession(ScheduleSlot scheduleSlot, String title) {
        mScheduleSlot = scheduleSlot;
        mTitle = title;
    }

    public long getStartTimestamp() {
        return mScheduleSlot.startDate;
    }

    public long getEndTimestamp() {
        return mScheduleSlot.endDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getRoomId() {
        return mScheduleSlot.room;
    }

    public int getSessionId() {
        return mScheduleSlot.sessionId;
    }

    @Override
    public String toString() {
        return "ScheduleSession{" +
                "mScheduleSlot=" + mScheduleSlot +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }

}