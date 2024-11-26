package lapuk_app.views.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import lapuk_app.views.main.ui.pages.ContactUsPage
import lapuk_app.views.main.ui.pages.FAQsPage
import lapuk_app.views.main.ui.pages.PrivacyPolicyPage
import lapuk_app.views.main.ui.pages.SegregatePage
import lapuk_app.views.main.ui.pages.TakeImagePage
import lapuk_app.views.main.ui.theme.LapukTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    var lastNavigatedRoute by remember { mutableStateOf("info/about-us") }

    LapukTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),

            topBar = {
                TopBar()
            },

            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .shadow(2.dp)
                        .zIndex(1f)
                        .fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "info/about-us",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable("home") { TODO() }

                        composable("segregate") { SegregatePage(navController) }
                        composable("segregate/take-image") { TakeImagePage(navController) }

                        composable("articles") { TODO() }

                        composable("heatmap") { TODO() }

                        composable("info") {
                            CallSpeechBubble(lastNavigatedRoute) { option ->
                                when (option) {
                                    "FAQs" -> {
                                        lastNavigatedRoute = "info/frequently-asked-questions"
                                        navController.navigate("info/frequently-asked-questions")
                                    }

                                    "About Us" -> {
                                        lastNavigatedRoute = "info/about-us"
                                        navController.navigate("info/about-us")
                                    }

                                    "Contact Us" -> {
                                        lastNavigatedRoute = "info/contact-us"
                                        navController.navigate("info/contact-us")
                                    }

                                    "Privacy Policy" -> {
                                        lastNavigatedRoute = "info/privacy-policy"
                                        navController.navigate("info/privacy-policy")
                                    }
                                }
                            }
                        }

                        composable("info/privacy-policy") {
                            PrivacyPolicyPage()
                        }
                        composable("info/contact-us") {
                            ContactUsPage()
                        }
                        composable("info/about-us") {
                            AboutUsPage()
                        }
                        composable("info/frequently-asked-questions") {
                            FAQsPage()
                        }
                    }
                }
            },

            bottomBar = {
                BottomBar(navController)
            })
    }
}

@Composable
fun CallSpeechBubble(
    lastRoute: String,
    onOptionClick: (String) -> Unit
) {
    // Render the last-navigated route here
    when (lastRoute) {
        "info/about-us" -> AboutUsPage()
        "info/contact-us" -> ContactUsPage()
        "info/privacy-policy" -> PrivacyPolicyPage()
        "info/frequently-asked-questions" -> FAQsPage()
    }

    Box(
        modifier = Modifier.offset(x = (-10).dp, y = 28.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Overlay SpeechBubble
        SpeechBubble(lastRoute, onOptionClick)
    }
}
