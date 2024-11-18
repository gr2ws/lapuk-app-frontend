package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.LapukTheme
import lapuk_app.views.main.ui.elements.BottomBar
import lapuk_app.views.main.ui.elements.TopBar

@Preview(showBackground = true,
    device = "spec:width=1080px,height=2400px,dpi=440,navigation=buttons"
)
@Composable
fun PrivacyPolicyPagePreview() {
    LapukTheme {
        val navController = rememberNavController() // Create a mock NavController for preview
        PrivacyPolicyPage(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyPage(navController: NavController) {
    LapukTheme {
        Scaffold(
            topBar = {
                TopBar()
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // title
                    Box(modifier = Modifier.padding(vertical=18.dp), contentAlignment = Alignment.Center) {
                        Text(
                            modifier = Modifier
                                .padding(12.dp),
                            text = "PRIVACY POLICY",
                            fontSize = 36.sp
                        )
                    }
                    //body
                    Box(
                        modifier = Modifier
                            .padding(vertical=18.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val fontSize = 14.sp
                        Text(
                            modifier = Modifier.padding(18.dp),
                            text =
                            """
                            The LAPUK app and its creators greatly respect its user’s privacy and is committed to protecting their data. The app collects little to no personal information, especially as the app is only meant for waste detection purposes.
        
                            Images taken, uploaded, and processed in the SEGREGATE option are maintained only in the local database of the user’s device to service statistical requests on the user’s waste items.     
                            """.trimIndent(),
                            style = TextStyle(
                                fontSize = fontSize,
                                lineHeight = fontSize * 1.8 // Line height is 1.5 times the font size
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                    // navigation
                    Box(modifier = Modifier.padding(vertical=18.dp), contentAlignment = Alignment.Center) {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.chevrons_left),
                                contentDescription = "Previous Page",
                                modifier = Modifier
                                    .size(24.dp)
                                //disable clicks here
                            )

                            Text(
                                modifier = Modifier
                                    .padding(12.dp),
                                    text = "Page 1 of 2",
                                    fontSize = 14.sp
                            )

                            Icon(
                                painterResource(id = R.drawable.chevrons_right),
                                contentDescription = "Previous Page",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        println("Hello World!")
                                    },
                            )
                        }
                    }

                }
            },
            bottomBar = {
                BottomBar(navController)
            }
        )
    }
}
