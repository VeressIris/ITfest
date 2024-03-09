package com.example.itfest.classes

//considered as todo
open class Task(val name: String, var completed: Boolean, var frequency: String) {
    fun markAsCompleted() {
        completed = true
    }
}
