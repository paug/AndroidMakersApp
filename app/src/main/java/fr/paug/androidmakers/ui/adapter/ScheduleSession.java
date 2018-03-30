package fr.paug.androidmakers.ui.adapter;

import android.support.annotation.NonNull;

import fr.paug.androidmakers.model.ScheduleSlot;

public class ScheduleSession implements Comparable<ScheduleSession> {

    private ScheduleSlot mScheduleSlot;
    private String mTitle;
    private String mLanguage;

    public ScheduleSession(ScheduleSlot scheduleSlot, String title, String language) {
        mScheduleSlot = scheduleSlot;
        mTitle = title;
        mLanguage = language;
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

    public String getLanguage() {
        return mLanguage;
    }

    public String getLanguageInEmoji() {
        if ("en".equalsIgnoreCase(mLanguage)) {
            return "\uD83C\uDDEC\uD83C\uDDE7";
        } else if ("fr".equalsIgnoreCase(mLanguage)) {
            return "\uD83C\uDDEB\uD83C\uDDF7";
        } else {
            return "\uD83C\uDDEC\uD83C\uDDE7 \uD83C\uDDEB\uD83C\uDDF7";
        }
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

    @Override
    public int compareTo(@NonNull ScheduleSession o) {
        return Long.valueOf(this.mScheduleSlot.startDate).compareTo(o.mScheduleSlot.startDate);
    }

}