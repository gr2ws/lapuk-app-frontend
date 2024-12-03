@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.elements.BottomBar
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br2

data class NavigationItem(
    val name: String,
    val symbol: Painter,
    val route : String,
) { /* ... */ }

@Preview(
    showBackground = true, device = "spec:width=1080px,height=2400px,dpi=440,navigation=buttons"
)
@Composable
fun HomePage(navController: NavController) {

    val selectedIndex = remember { mutableStateOf(0) } // Track selected index

    val firstRow = listOf(
        NavigationItem("Segregate", painterResource(id = R.drawable.segregate), route = "segregate"),
        NavigationItem("Articles", painterResource(id = R.drawable.articles), route = "articles"),
        NavigationItem("Heatmaps", painterResource(id = R.drawable.heatmaps), route = "heatmap"),
        NavigationItem("FAQs", painterResource(id = R.drawable.faqs), route = "info/frequently-asked-questions"),
    )

    val secondRow = listOf(
        NavigationItem("About Us", painterResource(id = R.drawable.about_us), route = "info/about-us"),
        NavigationItem("Contact Us", painterResource(id = R.drawable.contact_us), route = "info/contact-us"),
        NavigationItem("Privacy", painterResource(id = R.drawable.privacy_policy), route = "info/privacy-policy"),
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 124.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // title
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier,
                text = "Welcome",
                style = Typography.titleMedium
            )
        }

        // description
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 56.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier,
                text = "What can we help you with today?",
                style = Typography.bodyMedium
            )
        }

        // buttons row 1

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                firstRow.forEachIndexed { index, navigationItem ->
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(shape)
                                .background(br2)
                                .clickable {
                                    navController.navigate(navigationItem.route)
                                    selectedIndex.value = index
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = navigationItem.symbol,
                                contentDescription = navigationItem.name,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier,
                                text = navigationItem.name,
                                style = Typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // buttons row 2

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                secondRow.forEachIndexed { index, navigationItem ->
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(shape)
                                .background(br2)
                                .clickable {
                                    navController.navigate(navigationItem.route)
                                    selectedIndex.value = 4 // Update selected index
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = navigationItem.symbol,
                                contentDescription = navigationItem.name,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                            Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier,
                                text = navigationItem.name,
                                style = Typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        // collapsible content

        BottomBar(navController = navController, pageClicked = selectedIndex.value)
    }
}
