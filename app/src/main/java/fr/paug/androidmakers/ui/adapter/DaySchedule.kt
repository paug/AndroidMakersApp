package fr.paug.androidmakers.ui.adapter

data class DaySchedule(
        val title: String = "",
        var roomSchedules: MutableList<RoomSchedule> = arrayListOf()
)