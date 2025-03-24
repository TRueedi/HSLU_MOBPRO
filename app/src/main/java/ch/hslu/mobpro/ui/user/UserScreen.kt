package ch.hslu.mobpro.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ch.hslu.mobpro.data.database.entities.UserEntity


@Composable
fun UserScreen(
    senderText: String,
    navController: NavHostController,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text= "Welcome to the UserScreen",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        var userViewModel = getUserViewModel()
        val user by userViewModel.user.collectAsState()
        val users by userViewModel.allUsers.collectAsState()
        UserInputs(
            user = user,
            onUpdateUser = { updatedUser ->
                userViewModel.updateUser(updatedUser)
            },
            onAddUser = { newUser ->
                userViewModel.addUser(newUser)
            }
        )
        UserList(
            modifier = Modifier
                .weight(1f),
            users = users
        )
    }
}

@Composable
private fun UserInputs(
    user: UserEntity,
    onUpdateUser: (UserEntity) -> Unit,
    onAddUser: (UserEntity) -> Unit
) {
    var name by remember(user.name) { mutableStateOf(user.name) }
    TextField(
        modifier = Modifier
            .padding(top = 16.dp),
        value = name,
        onValueChange = { newName ->
            name = newName
        },
        label = {
            Text("Name")
        }
    )
    var age by remember(user.age) { mutableStateOf(user.age.toString()) }
    TextField(
        modifier = Modifier
            .padding(top = 16.dp),
        value = age,
        onValueChange = { newAge ->
            age = newAge
        },
        label = {
            Text("Alter")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    var authorized by remember(user.authorized) { mutableStateOf(user.authorized)}
    Row(
        modifier = Modifier
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = authorized,
            onCheckedChange = { isChecked ->
                authorized = isChecked
            }
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "User authorisieren"
        )
    }
    Button(
        onClick = {
            onUpdateUser.invoke(
                UserEntity(
                    name = name,
                    age = age.toInt(),
                    authorized = authorized
                )
            )
        }
    ) {
        Text(
            text = "Speichern"
        )
    }
    Button(
        onClick = {
            onAddUser.invoke(
                UserEntity(
                    name = name,
                    age = age.toInt(),
                    authorized = authorized
                )
            )
        }
    ) {
        Text(
            text = "Hinzufügen"
        )
    }
}

@Composable
private fun UserList(
    modifier: Modifier,
    users: List<UserEntity>
) {
    Column(modifier = modifier) {
        users.forEach { user ->
            val status = if (user.authorized) "ist authorisiert" else "ist nicht authorisiert"
            Text(
                text = "${user.name}, ${user.age}, $status",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
