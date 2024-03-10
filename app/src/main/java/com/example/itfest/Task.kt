package com.example.itfest

import java.time.LocalDate
import java.time.format.DateTimeFormatter

//considered as todo
open class Task(val name: String, var completed: Boolean, val dateAdded: String = LocalDate.now().format(
    DateTimeFormatter.ISO_LOCAL_DATE)) {
    fun markAsCompleted() {
        completed = true
    }

    constructor() : this("", false)
}
