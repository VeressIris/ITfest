package com.example.itfest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.itfest.R
import com.example.itfest.databinding.FragmentAnimalBinding

class PetFragment : Fragment() {

    private var _binding: FragmentAnimalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentAnimalBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        // Get references to your ImageView elements
//        val imageViewToChange: ImageView = binding.imageViewbase // Replace with your ImageView ID
//        val thirstProgressBar: ProgressBar = binding.progressBar
//        val hungerProgressBar: ProgressBar = binding.progressBar2
//        val happinessProgressBar: ProgressBar = binding.progressBar3
//
//        // Change the image based on the progress levels
//        if (thirstProgressBar.progress < 50) {
//            // Load the new image from the folder
//            val newImage = resources.getIdentifier("thirst", "drawable", requireContext().packageName)
//            imageViewToChange.setImageResource(newImage)
//        } else if (hungerProgressBar.progress < 50) {
//            val newImage = resources.getIdentifier("hunger", "drawable", requireContext().packageName)
//            imageViewToChange.setImageResource(newImage)
//        } else if (happinessProgressBar.progress < 50) {
//            val newImage = resources.getIdentifier("ok", "drawable", requireContext().packageName)
//            imageViewToChange.setImageResource(newImage)
//        } else {
//            // If none of the progress levels are below 50, set the default image
//            imageViewToChange.setImageResource(R.drawable.base)
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}