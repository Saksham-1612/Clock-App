package com.example.clock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.util.*

class StopwatchFragment : Fragment() {

    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private lateinit var timerTextView: TextView
    private var isTimerRunning: Boolean = false
    private var startTime: Long = 0L
    private var elapsedTime: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stopwatch, container, false)

        // Initialize UI components
        timerTextView = view.findViewById(R.id.timerTextView)
        startButton = view.findViewById(R.id.startButton)
        stopButton = view.findViewById(R.id.stopButton)
        resetButton = view.findViewById(R.id.resetButton)

        // Set click listeners
        startButton.setOnClickListener { onStartClicked() }
        stopButton.setOnClickListener { onStopClicked() }
        resetButton.setOnClickListener { onResetClicked() }

        return view
    }

    private var timerTask: TimerTask? = null

    private fun onStartClicked() {
        if (!isTimerRunning) {
            startButton.isEnabled = false
            stopButton.isEnabled = true
            isTimerRunning = true
            startTime = System.currentTimeMillis() - elapsedTime
            startTimer()
        }
    }

    private fun onStopClicked() {
        if (isTimerRunning) {
            startButton.isEnabled = true
            stopButton.isEnabled = false
            isTimerRunning = false
            stopTimer()
        }
    }

    private fun onResetClicked() {
        startButton.isEnabled = true
        stopButton.isEnabled = false
        isTimerRunning = false
        elapsedTime = 0L
        timerTextView.text = "00:00:00"
        stopTimer()
    }

    private fun startTimer() {
        timerTask = object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    val currentTime = System.currentTimeMillis()
                    elapsedTime = currentTime - startTime
                    updateTimerText(elapsedTime)
                }
            }
        }
        Timer().scheduleAtFixedRate(timerTask, 0, 1000)
    }

    private fun stopTimer() {
        timerTask?.cancel()
    }

    private fun updateTimerText(elapsedTime: Long) {
        val seconds = elapsedTime / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        val secondsFormatted = String.format("%02d", seconds % 60)
        val minutesFormatted = String.format("%02d", minutes % 60)
        val hoursFormatted = String.format("%02d", hours)

        timerTextView.text = "$hoursFormatted:$minutesFormatted:$secondsFormatted"
    }
}



