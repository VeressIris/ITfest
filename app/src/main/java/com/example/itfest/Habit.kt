package com.example.itfest

//frequency = daily/ {0, 1, 5} (indexes 0 -> 6 of days of the week)
class Habit(name: String, completed: Boolean, val frequency: String, val repeatDays: MutableList<Int>? = null) : Task(name, completed)