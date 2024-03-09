package com.example.itfest.ui

import com.google.gson.Gson
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.itfest.R
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.example.itfest.Habit
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.charset.Charset

class TrackFragment : AppCompatActivity() {
    var habitChipBttn :Chip? = null
    var habitDetailsDiv :LinearLayout? = null
    var daysOfTheWeekChip :Chip? = null
    var daysOfTheWeekList :LinearLayout? = null
    var todoChip :Chip? = null
    var todoMessage: TextInputLayout? = null
    var moodChip: Chip? = null
    var moodDiv: LinearLayout? = null
    var addTaskBttn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track_fragment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initializing everything we need
        habitChipBttn = findViewById(R.id.habitChip)
        habitDetailsDiv = findViewById(R.id.habitDetailsDiv)
        daysOfTheWeekChip = findViewById(R.id.daysOfTheWeekChip)
        daysOfTheWeekList = findViewById(R.id.daysOfTheWeekListDiv)
        todoChip = findViewById(R.id.todoChip)
        todoMessage = findViewById(R.id.toDoInput)
        moodChip = findViewById(R.id.moodChip)
        moodDiv = findViewById(R.id.moodOptionsDiv)
        addTaskBttn = findViewById(R.id.addBttn)

        setButtonListeners()

        addTaskBttn!!.setOnClickListener {
            val checkedBttn = checkWhoIsChecked()
            when(checkedBttn) {
                habitChipBttn -> {
                    createHabit()
                }
                todoChip -> {

                }
                moodChip -> {

                }
            }
        }
    }

    fun createHabit() {
        //get habit name
        val habitNameInput = findViewById<TextInputLayout>(R.id.habitNameInput)
        val habitName = habitNameInput.editText?.text.toString()
        val frequency: String? = null
        if (daysOfTheWeekChip!!.isChecked) {
            val checkedDays = getDaysOfTheWeek()
            for (day in checkedDays) {
                Log.i(day.toString(), "day")
            }
        }
        val habit = Habit("djjd", false, "daily")
        val jsonString = Gson().toJson(habit)
        Log.i("muie", jsonString)
    }

    fun isFilePathValid(filePath: String): Boolean {
        val file = File(filePath)
        val parentDir = file.parentFile

        // Check if parent directory exists and is a directory
        if (parentDir != null && parentDir.exists() && parentDir.isDirectory) {
            // Check if file can be created
            return try {
                file.createNewFile()
                file.delete() // Delete the file if created successfully
                true
            } catch (e: Exception) {
                false
            }
        }

        return false
    }

    fun getDaysOfTheWeek() : MutableList<Int> {
        val list: MutableList<Int> = mutableListOf()
        for (i in 0 until daysOfTheWeekList!!.childCount) {
            val day = daysOfTheWeekList!!.getChildAt(i)
            if (day is CheckBox) {
                if (day.isChecked) {
                    list.add(i)
                }
            }
        }
        return list
    }

    fun getCheckedDays() {

    }

    fun openSpecificDiv(div: LinearLayout, chipBttn: Chip) {
        if (chipBttn.isChecked) {
            div.visibility = View.VISIBLE
        }
        else {
            div.visibility = View.INVISIBLE
        }
    }

    fun closeSpecificDiv(div: LinearLayout) {
        div.visibility = View.INVISIBLE
    }


    fun setButtonListeners() {
        makeInvisible(habitDetailsDiv!!)
        habitChipBttn!!.setOnClickListener {
            openSpecificDiv(habitDetailsDiv!!, habitChipBttn!!)
            keepOneThingChecked(habitChipBttn!!)
        }

        makeInvisible(daysOfTheWeekList!!)
        daysOfTheWeekChip!!.setOnClickListener {
            openSpecificDiv(daysOfTheWeekList!!, daysOfTheWeekChip!!)
        }

        makeInvisible(todoMessage!!)
        todoChip!!.setOnClickListener {
            openSpecificDiv(todoMessage!!, todoChip!!)
            keepOneThingChecked(todoChip!!)
        }

        makeInvisible(moodDiv!!)
        moodChip!!.setOnClickListener {
            openSpecificDiv(moodDiv!!, moodChip!!)
            keepOneThingChecked(moodChip!!)
        }
    }

    //returns button that is checked
    fun checkWhoIsChecked() : Chip? {
        if (habitChipBttn!!.isChecked) return habitChipBttn!!
        if (todoChip!!.isChecked) return todoChip!!
        if (moodChip!!.isChecked) return moodChip!!
        return null
    }

    fun keepOneThingChecked(bttnPressed: Chip) {
        val checkedBttn = checkWhoIsChecked()
        if (checkedBttn != bttnPressed) {
            when(checkedBttn) {
                habitChipBttn -> {
                    habitChipBttn!!.isChecked = false
                    closeSpecificDiv(habitDetailsDiv!!)
                }
                todoChip -> {
                    todoChip!!.isChecked = false
                    closeSpecificDiv(todoMessage!!)
                }
                moodChip -> {
                    moodChip!!.isChecked = false
                    closeSpecificDiv(moodDiv!!)
                }
            }
        }
    }

    fun <T : View> makeInvisible(div: T) {
        div.visibility = View.INVISIBLE
    }
}