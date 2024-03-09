package com.example.itfest.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.itfest.R
import com.example.itfest.databinding.FragmentStatsBinding
import java.util.Calendar

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getPastDates()
        generateCalendarUI(binding.daysTable, 31)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //gets the number of days for previous 2 months
    fun getPastDates() {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        for (i in 0..3) {
            calendar.set(Calendar.MONTH, currentMonth - i)
            Log.i(calendar.getActualMaximum(Calendar.DAY_OF_MONTH).toString(), "date")
        }
    }

    fun generateCalendarUI(daysTable: TableLayout, daysInMonth: Int) {
        var tableRow = TableRow(context)
        for (days in 1..daysInMonth) {
            if (days % 7 == 1) {
                tableRow = TableRow(context)
                daysTable.addView(tableRow)
            }

            generateDayFragment(days.toString(), tableRow)
        }
    }

    fun generateDayFragment(day: String, tableRow: TableRow) {
        val frame = FrameLayout(requireContext())
        val text = TextView(context)
        text.text = day
        text.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        frame.setBackgroundResource(R.drawable.calendar_day_frag)
        frame.setPadding(12)
        frame.addView(text)
        tableRow.addView(frame)
    }
}