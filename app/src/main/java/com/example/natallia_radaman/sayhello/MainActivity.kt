package com.example.natallia_radaman.sayhello

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import com.example.natallia_radaman.sayhello.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. HERE: when the notification is tapped,
        // the launcher intent is fired => any accompanying data would be handled here.
        // If different intent is need to fire => set the click_action field of the
        // notification message to this intent. The launcher intent is used when no
        // click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        intent?.extras?.let{
            for (key in it.keySet()) {
                val value = intent.extras.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }
        // [END handle_data_extras]

        subscribe_button.setOnClickListener{
            Log.d(TAG, "Subscribing to weather topic")
            // [START subscribe_topics]
            FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener{task ->
                    var message = getString(R.string.message_subscribed)
                    if (!task.isSuccessful) {
                        message = getString(R.string.message_subscribe_failed)
                    }
                    Log.d(TAG, message)
                    Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
                }
            // [END subscribe_topics]
        }

        log_token_button.setOnClickListener {
            // Get token
            // [START retrieve_current_token]
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                     if (!task.isSuccessful) {
                         Log.w(TAG, "getInstanceId failed", task.exception)
                         return@OnCompleteListener
                     }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    val message = getString(R.string.message_token_fmt, token)
                    Log.d(TAG, message)
                    Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
                })
            // [END retrieve_current_token]
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

