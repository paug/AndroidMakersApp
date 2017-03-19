package fr.paug.androidmakers.model;

/**
 * Created by stan on 18/03/2017.
 */

public class AgendaItem {

    private ScheduleSlot mScheduleSlot;
    private String mTitle;

    public AgendaItem(ScheduleSlot scheduleSlot, String title) {
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

    public int getRoomColorIndex() {
        return mScheduleSlot.room;
    }

    public int getSessionId() {
        return mScheduleSlot.sessionId;
    }
}
