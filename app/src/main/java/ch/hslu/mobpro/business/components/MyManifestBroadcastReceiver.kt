package ch.hslu.mobpro.business.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyManifestBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == BroadcastActions.MANIFEST_ACTION) {
            Log.d("MyManifestBroadcastReceiver", "Manifest Broadcast received!")
        }
        else {
            Log.d("MyManifestBroadcastReceiver", "Manifest Broadcast not received: ${intent?.action}")
        }
    }

}