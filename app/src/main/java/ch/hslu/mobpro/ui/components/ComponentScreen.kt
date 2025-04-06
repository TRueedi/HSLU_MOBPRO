package ch.hslu.mobpro.ui.components

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.ContactsContract
import android.provider.Telephony
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
        ProviderPartWithTabs()
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProviderPartWithTabs() {
    val tabs = listOf("SMS", "Contacts")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Provider",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- TabRow ---
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> SmsTab()
            1 -> ContactsTab()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SmsTab() {
    val smsPermissionState = rememberPermissionState(android.Manifest.permission.READ_SMS)
    val context = LocalContext.current
    var smsList by remember { mutableStateOf<List<String>>(emptyList()) }
    val isSmsPermissionGranted = smsPermissionState.status.isGranted

    Column {
        if (!isSmsPermissionGranted) {
            Button(onClick = { smsPermissionState.launchPermissionRequest() }) {
                Text("SMS-Permission request")
            }
        } else {
            Button(onClick = { smsList = readSms(context) }) {
                Text("load SMS")
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(smsList) { sms ->
                    Text(text = sms, modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactsTab() {
    val contactsPermissionState = rememberPermissionState(android.Manifest.permission.READ_CONTACTS)
    val context = LocalContext.current
    var contactsList by remember { mutableStateOf<List<String>>(emptyList()) }
    val isContactsPermissionGranted = contactsPermissionState.status.isGranted

    Column {
        if (!isContactsPermissionGranted) {
            Button(onClick = { contactsPermissionState.launchPermissionRequest() }) {
                Text("Contacts-Permission request")
            }
        } else {
            Button(onClick = { contactsList = readContacts(context) }) {
                Text("load Contacts")
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(contactsList) { contact ->
                    Text(text = contact, modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}


private fun readSms(context: Context): List<String> {
    val smsList = mutableListOf<String>()
    context.contentResolver.query(
        Telephony.Sms.Inbox.CONTENT_URI,
        arrayOf(Telephony.Sms.Inbox._ID, Telephony.Sms.Inbox.BODY),
        null, null, null
    )?.use { cursor ->
        val bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY)
        while (cursor.moveToNext()) {
            val body = cursor.getString(bodyIndex)
            smsList.add(body)
        }
    }
    return smsList
}

private fun readContacts(context: Context): List<String> {
    val contactsList = mutableListOf<String>()
    context.contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY),
        null, null, null
    )?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
        while (cursor.moveToNext()) {
            val name = cursor.getString(nameIndex)
            contactsList.add(name)
        }
    }
    return contactsList
}
