package fr.paug.androidmakers.ui.adapter

data class DaySchedule(
    val startDayInEpochMillis: Long,
    val title: String,
    val roomSchedules: MutableList<RoomSchedule>,
)
