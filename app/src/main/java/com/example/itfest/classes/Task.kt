package com.example.itfest.classes

open class Task(val name: String, var completed: Boolean) {
    fun markAsCompleted() {
        completed = true
    }
}
