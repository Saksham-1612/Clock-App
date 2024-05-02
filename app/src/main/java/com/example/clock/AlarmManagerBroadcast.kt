package com.example.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import com.example.clock.R

class AlarmManagerBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Alarming..", Toast.LENGTH_SHORT).show()

        val media = MediaPlayer.create(context, R.raw.alarm_sound)
        media.start()
    }
}
