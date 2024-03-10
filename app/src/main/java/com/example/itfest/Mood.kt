package com.example.itfest

import java.time.LocalDate

data class Mood(val currentMood: String, val dateAdded: LocalDate = LocalDate.now())