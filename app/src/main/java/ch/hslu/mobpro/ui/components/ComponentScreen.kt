package ch.hslu.mobpro.ui.components

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.data.ContextCache
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.navigation.NavHostController
import ch.hslu.mobpro.business.components.BroadcastActions
import ch.hslu.mobpro.business.components.MusicPlayerService
import ch.hslu.mobpro.business.components.MyLocalBroadcastReceiver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ComponentsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Components",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        ServicePart()
        BroadcastPart()
        ProviderPart()
    }

}

@Composable
fun ServicePart() {
    val context = LocalContext.current
    Column {
        Text(
            text = "Service",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Start and stop the music player service",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Button(
            onClick =  {
                val intent = Intent(context, MusicPlayerService::class.java)
                context.startService(intent)
            }
        ) {
            Text("Start service")
        }
        Button(
            onClick =  {
                val intent = Intent(context, MusicPlayerService::class.java)
                context.stopService(intent)
            }
        ) {
            Text("Stop service")
        }
    }
}

@Composable
fun BroadcastPart() {
    val context = LocalContext.current
    LifecycleStartEffect(Unit) {
        val receiver = MyLocalBroadcastReceiver()
        ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter(BroadcastActions.LOCAL_ACTION),
            RECEIVER_NOT_EXPORTED
        )
        onStopOrDispose {
            context.unregisterReceiver(receiver)
        }
    }
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = "Broadcast",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )
    Button(
        onClick = {
            val intent = Intent(BroadcastActions.MANIFEST_ACTION)
            intent.`package`= "ch.hslu.mobpro"
            context.sendBroadcast(intent)
        }
    ) {
        Text("Send Manifest broadcast")
    }
    Button(
        onClick = {
            val intent = Intent(BroadcastActions.LOCAL_ACTION)
            intent.`package`= "ch.hslu.mobpro"
            context.sendBroadcast(intent)
        }
    ) {
        Text("Send Local broadcast")
    }


}

@Composable
fun ProviderPart() {
    val context = LocalContext.current

    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = "Provider",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )
    Column {
        Text(
            text = "Provider",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Button(
            onClick = {

            }
        ) {
            Text("Load SMS")
        }
    }
}