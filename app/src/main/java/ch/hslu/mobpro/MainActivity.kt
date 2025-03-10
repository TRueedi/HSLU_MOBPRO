package ch.hslu.mobpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ch.hslu.mobpro.ui.theme.HSLU_MOBPROTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HSLU_MOBPROTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        DemoAppNavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                )

            }

        }

    }
}

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){

        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 4.dp),
            text = "Welcome to the HomeScreen!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp),
            onClick = {
                navController.navigate(
                    route = "${DemoApplicationScreen.Detail.name}/HomeScreen"
                )
            }
        ) {
            Text(
                text = "Go to DetailScreen!",
            )
        }
        Button(
            modifier = Modifier
                .align(Alignment.End),
            onClick = {
                navController.navigate(
                    route = "${DemoApplicationScreen.Info.name}/HomeScreen/arg2Value"
                )
            }
        ) {
            Text(
                text = "Go to InfoScreen!",
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 4.dp),
            text = "Wih the buttons above, we can navigate to a new screen",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DemoAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DemoApplicationScreen.Home.name,
        modifier = modifier,
        enterTransition = { EnterTransition.None},
        exitTransition = { ExitTransition.None}
    ) {
        composable(
            route = DemoApplicationScreen.Home.name) {
            HomeScreen(
                navController = navController
            )
        }
        composable(
            route = "${DemoApplicationScreen.Detail.name}/{senderText}",
            arguments = listOf(
                navArgument(name = "senderText") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        )
        {
                navBackStackEntry ->
            val senderText = navBackStackEntry.arguments?.getString("senderText") ?: "error"
            DetailScreen(senderText = senderText, navController = navController)
        }
        composable(
            route = "${DemoApplicationScreen.Info.name}/{senderText}/{arg2}",
            arguments = listOf(
                navArgument(name = "senderText") {
                    type = NavType.StringType
                },
                navArgument(name = "arg2") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        )
        { navBackStackEntry ->
            val senderText = navBackStackEntry.arguments?.getString("senderText") ?: "error"
            val arg2 = navBackStackEntry.arguments?.getString("arg2") ?: "default"
            InfoScreen(senderText = senderText, arg2 = arg2, navController = navController)
        }
    }
}

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

enum class DemoApplicationScreen {
    Home,
    Detail,
    Info,
}