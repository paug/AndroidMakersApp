package fr.paug.androidmakers.ui.adapter;

import android.support.annotation.NonNull;

import java.util.List;

public class RoomSchedule implements Comparable<RoomSchedule> {
    private final int mRoomId;
    private final String mTitle;
    @NonNull
    private final List<Item> mItems;

    public RoomSchedule(int roomId, String title, @NonNull List<Item> items) {
        mRoomId = roomId;
        mTitle = title;
        mItems = items;
    }

    public int getRoomId() {
        return mRoomId;
    }

    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public List<Item> getItems() {
        return mItems;
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
                ", mItems=" + mItems +
                '}';
    }

}