package com.perisic.luka.birdcounterlv

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.perisic.luka.birdcounterlv.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

const val dataKey = "counterData"
const val selectedDataKey = "counterSelectedData"

class MainActivity : AppCompatActivity(), ClickHandler {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        retrieveData()
        setupUi()
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }

    override fun onBirdClick(index: Int) {
        viewModel.countBird(index)
    }

    override fun onResetClick() {
        viewModel.resetCounter()
    }

    private fun setupUi() {
        binding.countStatus = viewModel.birdsDisplay
        binding.handler = this
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
