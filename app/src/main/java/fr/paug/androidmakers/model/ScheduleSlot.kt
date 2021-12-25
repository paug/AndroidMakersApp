package fr.paug.androidmakers.model

class ScheduleSlot(val room: Int, val sessionId: Int, val startDate: Long, val endDate: Long) {
    override fun toString(): String {
        return "ScheduleSlot{" +
                "room=" + room +
                ", sessionId=" + sessionId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}'
    }
}