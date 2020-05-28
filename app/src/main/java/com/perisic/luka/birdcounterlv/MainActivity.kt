package com.perisic.luka.birdcounterlv

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

const val dataKey = "counterData"
const val selectedDataKey = "counterSelectedData"

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrieveData()
        setupUi()
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }

    private fun setupUi() {
        viewModel.birds.observe(this, Observer { list ->
            textViewScores.text = list?.joinToString(separator = "\n") {
                "${it.name}: ${it.count}"
            }
        })
        viewModel.selectedBird.observe(this, Observer {
            it?.let {
                textViewScores.setTextColor(ContextCompat.getColor(this, android.R.color.white))
                textViewScores.setBackgroundColor(ContextCompat.getColor(this, it.color))
            } ?: let {
                textViewScores.setTextColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.tab_indicator_text
                    )
                )
                textViewScores.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.white
                    )
                )
            }
        })
        buttonOne.setOnClickListener { viewModel.countBird(0) }
        buttonTwo.setOnClickListener { viewModel.countBird(1) }
        buttonThree.setOnClickListener { viewModel.countBird(2) }
        buttonFour.setOnClickListener { viewModel.countBird(3) }
        buttonReset.setOnClickListener { viewModel.resetCounter() }
    }

    private fun retrieveData() {
        getSharedPreferences(dataKey, Context.MODE_PRIVATE).apply {
            getString(dataKey, null)?.let {
                viewModel.setData(it)
            } ?: let { viewModel.createData() }
            getString(selectedDataKey, null)?.let {
                viewModel.setSelected(it)
            }
        }
    }

    private fun saveData() {
        getSharedPreferences(dataKey, Context.MODE_PRIVATE)
            .edit()
            .putString(dataKey, viewModel.getStringData())
            .putString(selectedDataKey, viewModel.getSelectedStringData())
            .apply()
    }

}
