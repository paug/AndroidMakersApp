package fr.paug.androidmakers.ui.adapter

data class RoomScheduleKt(
        val roomId: String? =null,
        val title: String = "",
        var scheduleSessions: ArrayList<ScheduleSessionKt>
) : Comparable<RoomScheduleKt> {
    override fun compareTo(other: RoomScheduleKt): Int {
        return when {
            roomId == null && other.roomId == null -> return 0
            roomId == null && other.roomId != null -> return -1
            roomId != null && other.roomId == null -> return 1
            else -> roomId!!.compareTo(other.roomId!!)
        }
    }
}