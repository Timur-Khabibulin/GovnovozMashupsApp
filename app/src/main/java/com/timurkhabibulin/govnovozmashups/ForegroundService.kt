package com.timurkhabibulin.govnovozmashups

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat

class ForegroundService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun getService(): ForegroundService = this@ForegroundService
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.govnovoz_1)
        mediaPlayer.setNextMediaPlayer(MediaPlayer.create(this, R.raw.govnovoz_2))
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Define an intent that will open when the notification is tapped
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notificationBuilder = NotificationCompat.Builder(this, "ChannelId")
            .setContentTitle("Gonvovoz Player")
            .setContentText("Playing Gonvovoz")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notificationBuilder)
        return START_STICKY
    }
}
