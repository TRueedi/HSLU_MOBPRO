package ch.hslu.mobpro.ui.components

import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ch.hslu.mobpro.business.components.MusicPlayerService
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionState = rememberPermissionState(
                android.Manifest.permission.POST_NOTIFICATIONS
                )
            if(!permissionState.status.isGranted) {
                Button(
                    onClick = {
                        permissionState.launchPermissionRequest()
                    }
                ) {
                    Text("Request permission")
                }
            }
        }
        val context = LocalContext.current
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