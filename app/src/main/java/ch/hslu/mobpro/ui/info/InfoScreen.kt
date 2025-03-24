package ch.hslu.mobpro.ui.info

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun InfoScreen(
    senderText: String, arg2: String,
    navController: NavHostController,
) {
    Column {
        Text(
            text= "Welcome to the InfoScreen from $senderText and $arg2",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(
                text = "Go Back",
            )
        }
    }
}