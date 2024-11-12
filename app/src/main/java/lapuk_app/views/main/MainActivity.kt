package lapuk_app.views.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.LapukTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = 0xFF7F5539.toInt()
        window.navigationBarColor = 0xFF7F5539.toInt()

        setContent {
            LapukTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        HEROPAGE()
                    }
                }
            }
        }
    }
}

@Composable
fun HEROPAGE(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFF7F5539))
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.lapuk_logo_light),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(100.dp) // Set the desired width
                    .height(100.dp) // Set the desired height
            )
            Text(
                textAlign = TextAlign.Center,
                lineHeight = 76.sp,
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.White, // Ensure the text color contrasts with the background
                            fontSize = 76.sp
                        )
                    ) { append("LAPUK\n") }
                    withStyle(
                        style = SpanStyle(
                            color = Color.White, // Ensure the text color contrasts with the background
                            fontSize = 14.sp
                        )
                    ) { append("A Garbage Segregation Aid Application") }
                },
                modifier = Modifier
                    .padding(top = 16.dp) // Add some space between the image and text
                    .fillMaxWidth()
            )
        }
    }
}
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    LapukTheme {
//        Greeting("Android")
//    }
//}

@Preview(showBackground = true)
@Composable
fun HEROPAGEPreview() {
    HEROPAGE()
}

