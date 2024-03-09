package com.example.itfest.classes

//considered as todo
open class Task(val name: String, var completed: Boolean) {
    fun markAsCompleted() {
        completed = true
    }
}
