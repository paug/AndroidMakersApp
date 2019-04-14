package fr.paug.androidmakers.ui.adapter

data class RoomScheduleKt(
        val roomId: String = "",
        val title: String = "",
        var scheduleSessions: ArrayList<ScheduleSessionKt>
) : Comparable<RoomScheduleKt> {
    override fun compareTo(other: RoomScheduleKt): Int {
        return roomId.compareTo(other.roomId)
    }
}