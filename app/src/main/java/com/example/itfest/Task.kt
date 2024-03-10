package com.example.itfest

import java.time.LocalDate

//considered as todo
open class Task(val name: String, var completed: Boolean, val dateAdded: LocalDate = LocalDate.now()) {
    fun markAsCompleted() {
        completed = true
    }

    constructor() : this("", false)
}
