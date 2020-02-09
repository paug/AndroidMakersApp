package fr.paug.androidmakers.model

data class RoomsList(
        var allRooms: ArrayList<RoomKt> = arrayListOf()
)

data class RoomKt(
        val roomId: String = "",
        val roomName: String = "",
        val infos: String = ""
)