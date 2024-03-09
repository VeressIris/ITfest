package com.example.itfest

//frequency = daily/ {0, 1, 5} (indexes 0 -> 6 of days of the week)
class Habit(name: String, completed: Boolean, val frequency: String) : Task(name, completed)