package fr.paug.androidmakers.ui.adapter;

import android.support.annotation.NonNull;

import java.util.List;

public class RoomSchedule implements Comparable<RoomSchedule> {

    private final int mRoomId;
    private final String mTitle;
    @NonNull
    private final List<ScheduleSession> mScheduleSessions;

    public RoomSchedule(int roomId, String title, @NonNull List<ScheduleSession> scheduleSessions) {
        mRoomId = roomId;
        mTitle = title;
        mScheduleSessions = scheduleSessions;
    }

    public int getRoomId() {
        return mRoomId;
    }

    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public List<ScheduleSession> getItems() {
        return mScheduleSessions;
    }

    @Override
    public int compareTo(@NonNull RoomSchedule o) {
        return mRoomId - o.mRoomId;
    }

    @Override
    public String toString() {
        return "RoomSchedule{" +
                "mRoomId=" + mRoomId +
                ", mTitle='" + mTitle + '\'' +
                ", mItems=" + mScheduleSessions +
                '}';
    }

}