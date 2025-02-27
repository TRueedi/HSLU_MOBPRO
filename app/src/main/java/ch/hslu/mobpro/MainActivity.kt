package ch.hslu.mobpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
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
    Column {
        Button(
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
            DetailScreen(senderText = senderText)
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
            InfoScreen(senderText = senderText, arg2 = arg2)
        }
    }
}

@Composable
fun DetailScreen(senderText: String) {
    Text("Welcome to the DetailScreen from $senderText")
}

@Composable
fun InfoScreen(senderText: String, arg2: String) {
    Text("Welcome to the InfoScreen from $senderText and $arg2")
}

enum class DemoApplicationScreen {
    Home,
    Detail,
    Info,
}