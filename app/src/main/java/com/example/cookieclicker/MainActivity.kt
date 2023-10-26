package com.example.cookieclicker

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var cookieCounter = 0
    private var startTime: Long = 0
    private var isRunning = false
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Define variables
        val cookieCount = findViewById<TextView>(R.id.cookieCounter)
        val icon = findViewById<ImageButton>(R.id.pernicek)
        val vyska = Resources.getSystem().displayMetrics.heightPixels
        val sirka = Resources.getSystem().displayMetrics.widthPixels
        val timerText = findViewById<TextView>(R.id.stopwatch)

        //Listen to the click
        icon.setOnClickListener{
            //Update the counter
            //TODO: replace the strings.xml instead of hardcoded text
            cookieCounter++
            cookieCount.text = "Collected: $cookieCounter"

            //Move the cookie
            moveCookie(vyska,sirka, icon)

            // Start the stopwatch only if it hasn't been started yet
            if (!isRunning) {
                isRunning = true
                startTime = System.currentTimeMillis()
                handler.post(stopwatchRunnable)
            }

        }

    }
    private fun moveCookie(vyska: Int, sirka: Int, icon: ImageButton) {
        val layoutParams = icon.layoutParams as ViewGroup.MarginLayoutParams

        // Generate random X and Y positions within the screen dimensions
        val maxX = sirka - (icon.width + 5)
        val maxY = vyska - (icon.height + 10) // minus some boundary

        val randomX = (0 until maxX).random()
        val randomY = (0 until maxY).random()

        // Put the Cookie to Random position
        layoutParams.leftMargin = randomX
        layoutParams.topMargin = randomY

        icon.layoutParams = layoutParams
    }
    private fun startStopwatch() {
        if (!isRunning) {
            isRunning = true
            startTime = System.currentTimeMillis()
            handler.post(stopwatchRunnable)
        } else {
            isRunning = false
            handler.removeCallbacks(stopwatchRunnable)
        }
    }
    private val stopwatchRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - startTime
                updateStopwatchTime(elapsedTime)
                handler.postDelayed(this, 1000) // Update every second
            }
        }
    }

    private fun updateStopwatchTime(elapsedTime: Long) {
        val timerid = findViewById<TextView>(R.id.stopwatch)
        val seconds = (elapsedTime / 1000).toInt()
        val minutes = seconds / 60

        val timerText = String.format("%02d:%02d", minutes, seconds % 60)
        timerid.setText("$timerText");
    }

}