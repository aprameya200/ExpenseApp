package com.example.expenseapp.ui.Fragments.Dialogs

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.expenseapp.R
import com.example.expenseapp.databinding.FragmentChartsBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class ChartsFragment : Fragment() {

    lateinit var binding : FragmentChartsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChartsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val pie = PieData()

//        val pieChart = binding.ex_

// Create entries and provide colors
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(30f, "Slice 1"))
        entries.add(PieEntry(20f, "Slice 2"))
        entries.add(PieEntry(50f, "Slice 3"))

        val colors = ArrayList<Int>()
        colors.add(Color.YELLOW)    // Color for Slice 1
        colors.add(Color.GREEN)   // Color for Slice 2
        colors.add(Color.CYAN)     // Color for Slice 3

// Create a PieDataSet with entries and colors
        val dataSet = PieDataSet(entries, "Pie Chart")
        dataSet.colors = colors

// Customize the data set, e.g., add text size, slice spacing, etc.
        dataSet.valueTextSize = 16f

// Create a PieData object with the data set
        val data = PieData(dataSet)

// Set the data to the PieChart
//        pieChart.data = data

        return binding.root
    }

}