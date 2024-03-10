package com.example.itfest.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    val database = Firebase.database.reference

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

        //gets and display habits
        getHabits()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun <T: Task> displayElements(list: MutableList<T>) {
        for (task in list) {
            Log.i("Td Habit:", task.name)
        }
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

                displayElements(habitsList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("get habits", "can't get habits")
            }
        })
    }


}