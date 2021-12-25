package fr.paug.androidmakers.ui.adapter

class DaySchedule(val title: String, val roomSchedules: List<RoomSchedule>) {
    override fun toString(): String {
        return "DaySchedule{" +
                "mTitle='" + title + '\'' +
                ", mRoomSchedules=" + roomSchedules +
                '}'
    }
}