package ch.hslu.mobpro.business.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyLocalBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == BroadcastActions.LOCAL_ACTION) {
            Log.d("MyLocalBroadcastReceiver", "Local Broadcast received!")
        }
        else {
            Log.d("MyLocalBroadcastReceiver", "Local Broadcast not received: ${intent?.action}")
        }
    }

}