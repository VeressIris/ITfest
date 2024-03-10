package com.example.itfest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.itfest.R
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.example.itfest.Habit
import com.example.itfest.MainActivity
import com.example.itfest.Mood
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.itfest.Task
import java.util.UUID

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
    val database = Firebase.database.reference

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
                    createToDo()
                }
                moodChip -> {
                    createMood()
                }
            }
//            val intent = Intent(requireActivity(), MainActivity::class.java)
//            startActivity(intent)
        }
    }

    fun createMood() {
        val sadCheckBox = findViewById<CheckBox>(R.id.sadCheckBox)
        val mehCheckBox = findViewById<CheckBox>(R.id.mehCheckbox)
        val happyCheckBox = findViewById<CheckBox>(R.id.happyCheckbox)

        var mood: String? = null
        if (sadCheckBox.isChecked) {
            mood = "sad"
        }
        else if (mehCheckBox.isChecked) {
            mood = "meh"
        }
        else if (happyCheckBox.isChecked) {
            mood = "happy"
        }

        val currentMood = Mood(mood!!)
        database.child("moods").child(UUID.randomUUID().toString()).setValue(currentMood)
    }

    fun createHabit() {
        //get habit name
        val habitNameInput = findViewById<TextInputLayout>(R.id.habitNameInput)
        val habitName = habitNameInput.editText?.text.toString()
        var frequency: String? = null

        //set frequency
        val dailyChip = findViewById<Chip>(R.id.dailyChip)
        var habit: Habit? = null
        if(dailyChip.isChecked) {
            habit = Habit(habitName, false, "daily")
        }
        else if(daysOfTheWeekChip!!.isChecked) {
            val checkedDays = getCheckedDays()
            habit = Habit(habitName, false, null, checkedDays)
        }

        database.child("habits").child(UUID.randomUUID().toString()).setValue(habit)
    }

    fun createToDo() {
        val toDoTitle = todoMessage!!.editText!!.text
        val toDo = Task(toDoTitle.toString(), false)

        database.child("todos").child(UUID.randomUUID().toString()).setValue(toDo)
    }

    fun getCheckedDays() : MutableList<Int> {
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
            keepOneThingChecked(daysOfTheWeekChip!!)
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