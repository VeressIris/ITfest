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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track_fragment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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

    fun setButtonListeners() {
        val habitChipBttn = findViewById<Chip>(R.id.habitChip)
        val frequencyDiv = findViewById<LinearLayout>(R.id.frequencyDiv)
        makeInvisible(frequencyDiv)
        habitChipBttn.setOnClickListener {
            openSpecificDiv(frequencyDiv, habitChipBttn)
        }

        val daysOfTheWeekChip = findViewById<Chip>(R.id.daysOfTheWeekChip)
        val daysOfTheWeekList = findViewById<LinearLayout>(R.id.daysOfTheWeekListDiv)
        makeInvisible(daysOfTheWeekList)
        daysOfTheWeekChip.setOnClickListener {
            openSpecificDiv(daysOfTheWeekList, daysOfTheWeekChip)
        }

        val todoChip = findViewById<Chip>(R.id.todoChip)
        val todoMessage = findViewById<TextInputLayout>(R.id.todoText)
        makeInvisible(todoMessage)
        todoChip.setOnClickListener {
            openSpecificDiv(todoMessage, todoChip)
        }

        val moodChip = findViewById<Chip>(R.id.moodChip)
        val moodDiv = findViewById<LinearLayout>(R.id.moodOptionsDiv)
        makeInvisible(moodDiv)
        moodChip.setOnClickListener {
            openSpecificDiv(moodDiv, moodChip)
        }
    }

    fun <T : View> makeInvisible(div: T) {
        div.visibility = View.INVISIBLE
    }
}