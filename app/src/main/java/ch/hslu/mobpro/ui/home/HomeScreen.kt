package ch.hslu.mobpro.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ch.hslu.mobpro.DemoApplicationScreen
import ch.hslu.mobpro.ui.bands.BandsViewModel
import ch.hslu.mobpro.ui.theme.HSLU_MOBPROTheme


@Composable
fun HomeScreen(
    destinationName: String,
    navController: NavHostController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HomeScreenTitle()
            BandOverview(
                modifier = Modifier
                    .weight(1f),
                navController = navController
            )
            BottomButtonNavigation(
                destinationName = destinationName,
                onNavigation = {
                    navController.navigate(
                        route = "${DemoApplicationScreen.Detail.name}/HomeScreen"
                    )
                },
                onUserNavigation = {
                    navController.navigate(
                        route = "${DemoApplicationScreen.User.name}/UserScreen"
                    )
                },
                onComponentNavigation = {
                    navController.navigate(
                        route = "${DemoApplicationScreen.Components.name}/ComponentScreen"
                    )
                }
            )
        }
    }
}

@Composable
fun HomeScreenTitle() {
    Text(
        text = "Welcome to the HomeScreen!",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun BottomButtonNavigation(
    destinationName: String,
    onNavigation: () -> Unit,
    onUserNavigation: () -> Unit,
    onComponentNavigation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Button(
            modifier = Modifier
                .padding(top = 16.dp),
            onClick = onComponentNavigation
        ) {
            Text(
                text ="Go to ComponentScreen!",
            )
        }
        Button(
            modifier = Modifier,
            onClick = onNavigation
        ) {
            Text(
                text = "Go to $destinationName!",
            )
        }
        Button(
            modifier = Modifier,
            onClick = onUserNavigation
        ) {
            Text(
                text ="Go to UserScreen!",
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 4.dp),
            text = "With the button above, we can navigate to a new screen",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary)
        }
}

@Composable
fun BandOverview(
    modifier: Modifier,
    navController: NavHostController,
) {
    val bandsViewModel: BandsViewModel = viewModel()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bands",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Button(
            onClick = {
                bandsViewModel.requestBandCodesFromServer()
            }
        ) {
            Text("Bands laden")
        }

        val bands = bandsViewModel.bandsFlow.collectAsState()
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {
            bands.value.forEach { band ->
                Text(
                    modifier = Modifier.clickable {
                        navController.navigate(
                            route = "${DemoApplicationScreen.BandInfo.name}/${band.code}"
                        )
                    },
                    text = band.name
                )

            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun HomePreview() {
    HSLU_MOBPROTheme {
        HomeScreen(
            destinationName = "DetailScreen",
            navController = rememberNavController()
        )
    }
}