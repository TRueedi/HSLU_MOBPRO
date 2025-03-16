package ch.hslu.mobpro.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ch.hslu.mobpro.ui.theme.HSLU_MOBPROTheme

@Composable
fun DetailScreen(
    senderText: String,
    navController: NavHostController,
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text (
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Start),
            text = "Oben Anfang",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text (
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            text = "Oben Mitte",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text (
            modifier = Modifier
                .align(Alignment.End)
                .weight(1f),
            text = "Mitte Ende",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text (
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            text = "Untern Mitte",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text (
            modifier = Modifier
                .align(Alignment.Start)
                .weight(1f),
            text = "Unten Anfang",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Row (
            modifier = Modifier
                .fillMaxSize()
                .weight(0.4f),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Text(
                text= "Welcome to the DetailScreen from $senderText",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Button(
                modifier = Modifier,
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


}