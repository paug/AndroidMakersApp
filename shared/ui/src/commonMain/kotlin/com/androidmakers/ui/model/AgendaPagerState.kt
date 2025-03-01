package com.androidmakers.ui.model

import com.androidmakers.ui.agenda.DaySchedule
import fr.androidmakers.domain.model.Room

data class AgendaPagerState(val days: List<DaySchedule>, val rooms: List<Room>)
