package com.example.piechartdemo

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pieChart = findViewById<PieChart>(R.id.pieChart)
        val labelsContainer = findViewById<LinearLayout>(R.id.labelsContainer)
        setupPieChart(pieChart)
        setupLabels(labelsContainer)
    }

    private fun setupPieChart(pieChart: PieChart) {
        val entries = listOf(
            PieEntry(30f, "Completed"),
            PieEntry(50f, "In Progress"),
            PieEntry(20f, "Not Started")
        )

        val dataSet = PieDataSet(entries, "Task Status")
        dataSet.colors = listOf(
            Color.parseColor("#673AB7"), // Completed
            Color.parseColor("#8BC34A"), // In Progress
            Color.parseColor("#FF9800")  // Not Started
        )

        // Adjust slice thickness
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        // Make it a donut chart
        pieChart.setHoleRadius(70f) // Adjust the hole radius to control thickness
        pieChart.setTransparentCircleRadius(75f) // Adjust the transparent circle radius to match the hole radius

        // Enable percentage values
        pieChart.setUsePercentValues(true)

        // Customize legend
        val legend = pieChart.legend
        legend.isEnabled = false // Hide default legend

        // Set data and value formatter
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart)) // Set value formatter to show percentage
        data.setValueTextSize(12f) // Adjust text size
        data.setValueTextColor(Color.BLACK) // Set text color

        pieChart.data = data
        pieChart.invalidate() // refresh

        // Customize description
        pieChart.description.isEnabled = false
    }

    private fun setupLabels(container: LinearLayout) {
        val labels = listOf(
            "Completed: 30%",
            "In Progress: 50%",
            "Not Started: 20%"
        )

        val colors = listOf(
            "#673AB7", // Completed
            "#8BC34A", // In Progress
            "#FF9800"  // Not Started
        )

        for (i in labels.indices) {
            val itemContainer = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                setPadding(0, 8, 0, 8) // Add some padding for spacing
            }

            val colorBox = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(30, 30).apply { setMargins(0, 0, 16, 0) }
                setBackgroundColor(Color.parseColor(colors[i]))
            }

            val label = TextView(this).apply {
                text = labels[i]
                textSize = 16f
                setTextColor(Color.BLACK)
            }

            itemContainer.addView(colorBox)
            itemContainer.addView(label)
            container.addView(itemContainer)
        }
    }
}
