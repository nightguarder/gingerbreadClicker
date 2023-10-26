package com.example.cookieclicker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Stopwatch: AppCompatActivity() {
    private var isRunning = false
    private var startTime: Long = 0
    //Handle() is deprecated
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stopwatchtext = findViewById<TextView>(R.id.stopwatch)
        startStopwatch(stopwatchtext);
    }
    private fun startStopwatch(display: TextView) {
        handler.post(object : Runnable {
            override fun run() {
                if (isRunning) {
                    val currentTime = System.currentTimeMillis()
                    val elapsedTime = currentTime - startTime

                    val seconds = (elapsedTime / 1000).toInt()
                    val minutes = seconds / 60

                    val timeString = String.format("%02d:%02d", minutes, seconds % 60)

                    display.text = timeString
                    handler.postDelayed(this, 1000)
                }
            }
        })
    }
    override fun onPause() {
        super.onPause()
        isRunning = false
    }
}