package lapuk_app.views.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import lapuk_app.views.main.ui.elements.BottomBar
import lapuk_app.views.main.ui.elements.SpeechBubble
import lapuk_app.views.main.ui.elements.TopBar
import lapuk_app.views.main.ui.pages.AboutUsPage
import lapuk_app.views.main.ui.pages.ArticlesPage
import lapuk_app.views.main.ui.pages.ContactUsPage
import lapuk_app.views.main.ui.pages.FAQsPage
import lapuk_app.views.main.ui.pages.HeatmapsPage
import lapuk_app.views.main.ui.pages.HomePage
import lapuk_app.views.main.ui.pages.PrivacyPolicyPage
import lapuk_app.views.main.ui.pages.SegregatePage
import lapuk_app.views.main.ui.pages.TakeImagePage
import lapuk_app.views.main.ui.theme.LapukTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        Thread.sleep(2000)
        installSplashScreen().apply {
            setOnExitAnimationListener { splashScreenView ->
                // Call remove() when animation is finished to remove splash screen
                splashScreenView.remove()
            }

            WindowCompat.setDecorFitsSystemWindows(window, false)

            enableEdgeToEdge()

            setContent {
                MainScreen()
            }
        }
    }
}

@Preview(
    showBackground = true, device = "spec:width=1080px,height=2400px,dpi=440,navigation=buttons"
)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var lastNavigatedRoute by remember { mutableStateOf("home") }
    var indexOfLastPageAccessed = 0

    var showSpeechBubble by remember { mutableStateOf(false) }

    LapukTheme {
        Scaffold(modifier = Modifier.fillMaxSize(),

            topBar = {
                TopBar()
            },

            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .shadow(2.dp)
                        .zIndex(1f)
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable("home") {
                            lastNavigatedRoute = "home"
                            indexOfLastPageAccessed = 0
                            HomePage(navController)
                        }

                        composable("segregate") {
                            lastNavigatedRoute = "segregate"
                            indexOfLastPageAccessed = 1
                            SegregatePage(navController)
                        }
                        composable("segregate/take-image") {
                            lastNavigatedRoute = "segregate/take-image"
                            indexOfLastPageAccessed = 1
                            TakeImagePage(navController)
                        }

                        composable("articles") {
                            lastNavigatedRoute = "articles"
                            indexOfLastPageAccessed = 2
                            ArticlesPage()
                        }

                        composable("heatmap") {
                            lastNavigatedRoute = "heatmap"
                            indexOfLastPageAccessed = 3
                            HeatmapsPage()
                        }

                        composable("info/frequently-asked-questions") {
                            lastNavigatedRoute = "info/frequently-asked-questions"
                            FAQsPage()
                            indexOfLastPageAccessed = 4
                        }
                        composable("info/contact-us") {
                            lastNavigatedRoute = "info/contact-us"
                            ContactUsPage()
                            indexOfLastPageAccessed = 4
                        }
                        composable("info/about-us") {
                            lastNavigatedRoute = "info/about-us"
                            AboutUsPage()
                            indexOfLastPageAccessed = 4
                        }
                        composable("info/privacy-policy") {
                            lastNavigatedRoute = "info/privacy-policy"
                            PrivacyPolicyPage()
                            indexOfLastPageAccessed = 4
                        }
                    }

                    AnimatedVisibility(
                        visible = showSpeechBubble,
                        enter = fadeIn(tween(100)),
                        exit = fadeOut(tween(100))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.1f))
                                .clickable { showSpeechBubble = false },
                        )
                    }

                    AnimatedVisibility(
                        visible = showSpeechBubble,
                        enter = slideInVertically(
                            animationSpec = tween(100),
                            initialOffsetY = { 500 }),
                        exit = slideOutVertically(
                            animationSpec = tween(100),
                            targetOffsetY = { 500 })
                    ) {
                        CallSpeechBubble(lastNavigatedRoute) { option ->
                            when (option) {
                                "FAQs" -> {
                                    lastNavigatedRoute = "info/frequently-asked-questions"
                                    navController.navigate("info/frequently-asked-questions") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                    indexOfLastPageAccessed = 4
                                }

                                "About Us" -> {
                                    lastNavigatedRoute = "info/about-us"
                                    navController.navigate("info/about-us") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                    indexOfLastPageAccessed = 4
                                }

                                "Contact Us" -> {
                                    lastNavigatedRoute = "info/contact-us"
                                    navController.navigate("info/contact-us") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                    indexOfLastPageAccessed = 4
                                }

                                "Privacy Policy" -> {
                                    lastNavigatedRoute = "info/privacy-policy"
                                    navController.navigate("info/privacy-policy") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                    indexOfLastPageAccessed = 4
                                }
                            }
                            showSpeechBubble = false
                        }
                    }
                }
            }, bottomBar = {
                if (lastNavigatedRoute != "home") BottomBar(
                    navController,
                    indexOfLastPageAccessed,
                    onTapInfo = { showSpeechBubble = it })
            })
    }
}

@Composable
fun CallSpeechBubble(
    lastRoute: String, onOptionClick: (String) -> Unit
) {
    Box(
        modifier = Modifier.offset(x = (-10).dp, y = 28.dp), contentAlignment = Alignment.BottomEnd
    ) {
        // Overlay SpeechBubble
        SpeechBubble(lastRoute, onOptionClick)
    }
}