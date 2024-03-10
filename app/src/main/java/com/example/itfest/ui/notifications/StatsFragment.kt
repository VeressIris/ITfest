package com.example.itfest.ui.notifications

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.itfest.R
import com.example.itfest.databinding.FragmentStatsBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val calendar = Calendar.getInstance()
    val currentMonth = calendar.get(Calendar.MONTH)
    val sdf = SimpleDateFormat("MMMM", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //generate current month dates
        val daysTable = binding.daysTable
        generateCalendarUI(daysTable, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))

        //changing calendar month with bttns
        binding.prevMonthBttn.setOnClickListener {
            daysTable.removeAllViews()
            calendar.add(Calendar.MONTH, -1)
            generateCalendarUI(daysTable, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        }
        binding.nextMonthBttn.setOnClickListener {
            daysTable.removeAllViews()
            calendar.add(Calendar.MONTH, 1)
            generateCalendarUI(daysTable, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun generateCalendarUI(daysTable: TableLayout, daysInMonth: Int) {
        val monthName = sdf.format(calendar.time)
        binding.monthText.text = monthName

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
        val text = TextView(context)
        text.text = day
        text.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        text.setTextColor(Color.BLACK)
        text.gravity = TextView.TEXT_ALIGNMENT_CENTER
        text.setPadding(8)
        text.setBackgroundResource(R.drawable.buton)
        val layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(32)
        text.layoutParams = layoutParams
        tableRow.addView(text)
    }
}