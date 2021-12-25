package fr.paug.androidmakers.ui.adapter

data class RoomSchedule(
        val roomId: String? =null,
        val title: String = "",
        var scheduleSessions: ArrayList<ScheduleSession>
) : Comparable<RoomSchedule> {
    override fun compareTo(other: RoomSchedule): Int {
        return when {
            roomId == null && other.roomId == null -> return 0
            roomId == null && other.roomId != null -> return -1
            roomId != null && other.roomId == null -> return 1
            else -> roomId!!.compareTo(other.roomId!!)
        }
    }
}