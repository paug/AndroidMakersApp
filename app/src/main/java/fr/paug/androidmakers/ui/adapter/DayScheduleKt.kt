package fr.paug.androidmakers.ui.adapter

data class DayScheduleKt(
        val title: String = "",
        var roomSchedules: MutableList<RoomScheduleKt> = arrayListOf()
)