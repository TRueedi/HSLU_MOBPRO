package ch.hslu.mobpro
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import ch.hslu.mobpro.ui.detail.DetailScreen
import ch.hslu.mobpro.ui.home.HomeScreen
import ch.hslu.mobpro.ui.user.UserScreen

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ch.hslu.mobpro.ui.bands.BandInfoScreen
import ch.hslu.mobpro.ui.components.ComponentsScreen
import ch.hslu.mobpro.ui.theme.HSLU_MOBPROTheme
import ch.hslu.mobpro.ui.user.UserScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val channel = NotificationChannel(
            "Ich bin ein Channel",
            "Music player stuff",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

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
                destinationName = "DetailScreen",
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
            route = "${DemoApplicationScreen.BandInfo}/{bandCode}",
            arguments = listOf(
                navArgument(name = "bandCode") {
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
            val bandCode = navBackStackEntry.arguments?.getString("bandCode") ?: "error"
            BandInfoScreen(
                bandCode = bandCode
            )
        }
        composable(
            route = "${DemoApplicationScreen.User.name}/{senderText}",
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
            UserScreen(senderText = senderText, navController = navController)
        }
        composable(
            route = "${DemoApplicationScreen.Components.name}/{senderText}",
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
            ComponentsScreen()
        }
    }
}



enum class DemoApplicationScreen {
    Home,
    Detail,
    BandInfo,
    User,
    Components
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