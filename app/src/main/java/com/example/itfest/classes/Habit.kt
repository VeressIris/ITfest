package com.example.itfest.classes

//frequency = daily/ {0, 1, 5} (indexes 0 -> 6 of days of the week)
class Habit(name: String, completed: Boolean, var frequency: String) : Task(name, completed)