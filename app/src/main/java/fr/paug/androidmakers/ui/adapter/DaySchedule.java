package fr.paug.androidmakers.ui.adapter;

import android.support.annotation.NonNull;

import java.util.List;

public class DaySchedule {

    private final String mTitle;
    @NonNull
    private final List<RoomSchedule> mRoomSchedules;

    public DaySchedule(String title, @NonNull List<RoomSchedule> roomSchedules) {
        mTitle = title;
        mRoomSchedules = roomSchedules;
    }

    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public List<RoomSchedule> getRoomSchedules() {
        return mRoomSchedules;
    }

    @Override
    public String toString() {
        return "DaySchedule{" +
                "mTitle='" + mTitle + '\'' +
                ", mRoomSchedules=" + mRoomSchedules +
                '}';
    }

}