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
import lapuk_app.views.main.ui.pages.ArticlesPage
import lapuk_app.views.main.ui.pages.ContactUsPage
import lapuk_app.views.main.ui.pages.FAQsPage
import lapuk_app.views.main.ui.pages.HomePage
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
    var lastNavigatedRoute by remember { mutableStateOf("home") }
    var indexOfLastPageAccessed = 0

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

                        composable("heatmap") { TODO() }

                        composable("info") {
                            CallSpeechBubble(lastNavigatedRoute) { option ->
                                when (option) {
                                    "FAQs" -> {
                                        navController.navigate("info/frequently-asked-questions")
                                        indexOfLastPageAccessed = 4
                                    }

                                    "About Us" -> {
                                        navController.navigate("info/about-us")
                                        indexOfLastPageAccessed = 4
                                    }

                                    "Contact Us" -> {
                                        lastNavigatedRoute = "info/contact-us"
                                        navController.navigate("info/contact-us")
                                        indexOfLastPageAccessed = 4
                                    }

                                    "Privacy Policy" -> {
                                        lastNavigatedRoute = "info/privacy-policy"
                                        navController.navigate("info/privacy-policy")
                                        indexOfLastPageAccessed = 4
                                    }
                                }
                            }
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
                }
            },

            bottomBar = {
                if (lastNavigatedRoute != "home")
                    BottomBar(navController, indexOfLastPageAccessed)
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
