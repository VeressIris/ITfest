package com.example.itfest

//considered as todo
open class Task(val name: String, var completed: Boolean) {
    fun markAsCompleted() {
        completed = true
    }

    constructor() : this("", false)
}
