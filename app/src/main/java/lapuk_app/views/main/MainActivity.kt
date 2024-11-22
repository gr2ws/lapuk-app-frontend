package lapuk_app.views.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import lapuk_app.views.main.ui.elements.TopBar
import lapuk_app.views.main.ui.pages.ContactUsPage
import lapuk_app.views.main.ui.pages.PrivacyPolicyPage
import lapuk_app.views.main.ui.pages.SavePreviewPage
import lapuk_app.views.main.ui.pages.SegregatePage
import lapuk_app.views.main.ui.pages.TakeImagePage
import lapuk_app.views.main.ui.theme.LapukTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(3000)
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

    var currentImage = remember { mutableStateOf<ImageProxy?>(null) }

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
                        .fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "segregate/take-image",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable("home") { TODO() }

                        composable("segregate") { SegregatePage(navController) }
                        composable("segregate/take-image") {
                            // pass empty currentImage, get image and store, return currentImage to main screen
                            TakeImagePage(navController, onImageCaptured = { imageProxy ->
                                currentImage.value = imageProxy
                            }) // TODO: make sure not null (callback probably works, image capture fails)
                        }
                        composable("segregate/save-preview") {
                            // TODO: make sure not null
                            Log.d("MainScreen", "currentImage: ${currentImage.value} from save-preview")
                            currentImage.value?.let { SavePreviewPage(it) }
                        }

                        composable("articles") { TODO() }

                        composable("heatmap") { TODO() }

                        composable("info") { TODO() }
                        composable("info/privacy-policy") { PrivacyPolicyPage() }
                        composable("info/contact-us") { ContactUsPage() }
                        composable("info/about-us") { TODO() }
                    }
                }
            },

            bottomBar = {
                BottomBar(navController)
            })
    }
}

