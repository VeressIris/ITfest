package com.example.itfest.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.itfest.R
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout

class TrackFragment : AppCompatActivity() {
    var habitChipBttn :Chip? = null
    var habitDetailsDiv :LinearLayout? = null
    var daysOfTheWeekChip :Chip? = null
    var daysOfTheWeekList :LinearLayout? = null
    var todoChip :Chip? = null
    var todoMessage: TextInputLayout? = null
    var moodChip: Chip? = null
    var moodDiv: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track_fragment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        habitChipBttn = findViewById<Chip>(R.id.habitChip)
        habitDetailsDiv = findViewById<LinearLayout>(R.id.habitDetailsDiv)
        daysOfTheWeekChip = findViewById<Chip>(R.id.daysOfTheWeekChip)
        daysOfTheWeekList = findViewById<LinearLayout>(R.id.daysOfTheWeekListDiv)
        todoChip = findViewById<Chip>(R.id.todoChip)
        todoMessage = findViewById<TextInputLayout>(R.id.toDoInput)
        moodChip = findViewById<Chip>(R.id.moodChip)
        moodDiv = findViewById<LinearLayout>(R.id.moodOptionsDiv)

        setButtonListeners()
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

    fun keepOneThingChecked(bttnPressed: Chip) {
        if (habitChipBttn!!.isChecked && bttnPressed != habitChipBttn) {
            habitChipBttn!!.isChecked = false
            closeSpecificDiv(habitDetailsDiv!!)
        }
        else if (todoChip!!.isChecked && bttnPressed != todoChip) {
            todoChip!!.isChecked = false
            closeSpecificDiv(todoMessage!!)
        }
        else if (moodChip!!.isChecked && bttnPressed != moodChip) {
            moodChip!!.isChecked = false
            closeSpecificDiv(moodDiv!!)
        }
    }

    fun <T : View> makeInvisible(div: T) {
        div.visibility = View.INVISIBLE
    }
}