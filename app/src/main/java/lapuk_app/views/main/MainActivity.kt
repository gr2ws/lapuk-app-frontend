package lapuk_app.views.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.toArgb
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
import lapuk_app.views.main.ui.pages.SegregatePage
import lapuk_app.views.main.ui.theme.LapukTheme
import lapuk_app.views.main.ui.theme.br1
import lapuk_app.views.main.ui.theme.br5

@Suppress("DEPRECATION") // remove deprecation warning
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen().apply { setOnExitAnimationListener { splashScreenView ->
            // Call remove() when animation is finished to remove splash screen
            splashScreenView.remove()}
        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = br5.toArgb()
        window.navigationBarColor = br1.toArgb()

        setContent {
            MainScreen()
        }
    }
}   

@Preview(
    showBackground = true, device = "spec:width=1080px,height=2400px,dpi=440,navigation=buttons"
)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

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
                        startDestination = "segregate",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable("home") {
                            TODO()
                        }
                        composable("segregate") {
                            SegregatePage()
                        }
                        composable("articles") {
                            TODO()
                        }
                        composable("heatmap") {
                            TODO()
                        }
                        composable("info") {
                            TODO()
                        }
                    }
                }
            },

            bottomBar = {
                BottomBar(navController)
            })
    }
}}



