package com.example.itfest.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.itfest.Habit
import com.example.itfest.Task
import com.example.itfest.ui.TrackFragment
import com.example.itfest.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.snapshots
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    val database = Firebase.database.reference
    var habitsContainer: LinearLayout? = null
    var todoContainer: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val trackBttn = binding.trackBttn
        trackBttn.setOnClickListener {
            val intent = Intent(requireContext(), TrackFragment::class.java)
            startActivity(intent)
        }

        habitsContainer = binding.habitsContainer
        todoContainer = binding.toDoContainer

        //gets and display habits and todos
        getHabits()
        getToDos()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun displayHabits(list: MutableList<Habit>) {
        for (task in list) {
            if (shouldDisplayHabit(task)) {
                createTaskLayout(task, habitsContainer!!)
                Log.i("Td habit:", task.name)
            }
        }
    }

    fun shouldDisplayHabit(habit: Habit): Boolean {
        if (habit.frequency != null) return true

        for (day in habit.repeatDays!!) {
            if (LocalDate.now().dayOfWeek.value == day) {
                return true
            }
        }

        return false
    }

    fun displayToDos(list: MutableList<Task>) {
        for (task in list) {
            if (LocalDate.parse(task.dateAdded, DateTimeFormatter.ISO_LOCAL_DATE) == LocalDate.now()) {
                createTaskLayout(task, todoContainer!!)
                Log.i("Td todo:", task.name)
            }
        }
    }

    fun createTaskLayout(task: Task, container: LinearLayout) {
        val horizontalView = LinearLayout(requireContext())
        horizontalView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        horizontalView.orientation = LinearLayout.HORIZONTAL

        val title = TextView(requireContext())
        title.text = task.name
        title.setTextColor(Color.BLACK)
        title.textSize = 16f
        val checkBox = CheckBox(requireContext())

        //set checkbox listener
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                title.setTextColor(Color.GRAY)
                task.markAsCompleted()
                Log.i("completed: ", task.name)
            }
            else {
                title.setTextColor(Color.BLACK)
            }
        }

        horizontalView.addView(title)
        horizontalView.addView(checkBox)

        container.addView(horizontalView)
    }

    fun getHabits() {
        val habitsList:MutableList<Habit> = mutableListOf()

        val habitsRef = database.child("habits")
        habitsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (habitSnapshot in dataSnapshot.children) {
                    val habit = habitSnapshot.getValue(Habit::class.java)
                    habitsList.add(habit!!)
                }

                displayHabits(habitsList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("get habits", "can't get habits")
            }
        })
    }

    fun getToDos() {
        val toDoList:MutableList<Task> = mutableListOf()

        val habitsRef = database.child("todos")
        habitsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val todo = snapshot.getValue(Task::class.java)
                    toDoList.add(todo!!)
                }

                displayToDos(toDoList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("get todo", "can't get to-dos")
            }
        })
    }
}