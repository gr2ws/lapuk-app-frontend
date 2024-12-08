//@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package lapuk_app.views.main.ui.pages

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lapuk_app.R
import kotlinx.coroutines.launch
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br2
import lapuk_app.views.main.ui.theme.br5
import lapuk_app.views.main.ui.theme.br6
import lapuk_app.views.main.ui.theme.wh1


data class NavigationItem(
    val name: String,
    val symbol: Painter,
    val route: String,
) { /* ... */ }


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomePage(navController: NavController) {

    val isExpanded = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
    val firstRow = listOf(
        NavigationItem(
            "Segregate", painterResource(id = R.drawable.segregate), route = "segregate"
        ),
        NavigationItem("Articles", painterResource(id = R.drawable.articles), route = "articles"),
        NavigationItem("Heatmaps", painterResource(id = R.drawable.heatmaps), route = "heatmap"),
        NavigationItem(
            "FAQs", painterResource(id = R.drawable.faqs), route = "info/frequently-asked-questions"
        ),
    )
    val firstSelectedIndex = remember { mutableStateOf(-1) }
    val secondRow = listOf(
        NavigationItem(
            "About Us", painterResource(id = R.drawable.about_us), route = "info/about-us"
        ),
        NavigationItem(
            "Contact Us", painterResource(id = R.drawable.contact_us), route = "info/contact-us"
        ),
        NavigationItem(
            "Privacy Policy",
            painterResource(id = R.drawable.privacy_policy),
            route = "info/privacy-policy"
        ),
    )
    val secondSelectedIndex = remember { mutableStateOf(-1) }
    val coroutineScope = rememberCoroutineScope() // Coroutine scope for handling delays



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 124.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        PageDetails()

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
                        Box(modifier = Modifier
                            .size(52.dp)
                            .clip(shape)
                            .border(width = 1.dp, color = br6, shape = shape)
                            .clickable {
                                isExpanded.value = true
                                isLoading.value = true
                                firstSelectedIndex.value =
                                    index // Set the selected index when clicked
                                coroutineScope.launch {
                                    kotlinx.coroutines.delay(800) // 1.5-second delay
                                    navController.navigate(navigationItem.route)
                                }
                            }
                            .background(if (firstSelectedIndex.value == index) br5 else br2),
                            contentAlignment = Alignment.Center) {
                            Icon(
                                painter = navigationItem.symbol,
                                contentDescription = navigationItem.name,
                                modifier = Modifier.size(18.dp),
                                tint = if (firstSelectedIndex.value == index) wh1 else Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier.padding(horizontal = 12.dp),
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
                        Box(modifier = Modifier
                            .size(52.dp)
                            .clip(shape)
                            .background(br2)
                            .border(width = 1.dp, color = br6, shape = shape)
                            .clickable {
                                isExpanded.value = true
                                isLoading.value = true
                                secondSelectedIndex.value =
                                    index // Set the selected index when clicked
                                coroutineScope.launch {
                                    kotlinx.coroutines.delay(800) // 1.5-second delay
                                    navController.navigate(navigationItem.route)
                                }
                            }
                            .background(if (secondSelectedIndex.value == index) br5 else br2),
                            contentAlignment = Alignment.Center) {
                            Icon(
                                painter = navigationItem.symbol,
                                contentDescription = navigationItem.name,
                                modifier = Modifier.size(18.dp),
                                tint = if (secondSelectedIndex.value == index) wh1 else Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier,
                                text = navigationItem.name,
                                style = Typography.bodySmall,
                            )
                        }
                    }
                }
            }

        }

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(visible = !isExpanded.value, enter = fadeIn(snap(450)), exit = fadeOut(snap(0))) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable {
                        isExpanded.value = true
                    }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.circle_help),
                    contentDescription = "help",
                    modifier = Modifier.size(24.dp)
                )
            }
        }


        // when help button is clicked...
        AnimatedVisibility(
            visible = isExpanded.value,
            enter = slideInVertically { 600 },
            exit = slideOutVertically { 600 },
            modifier = Modifier.fillMaxHeight(.75f),
            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3.5f)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(br2),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // render app description section (NOT loading section)
                if (!isLoading.value) {
                    // top icon
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .clickable {
                                isExpanded.value = false
                            }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.circle_help),
                            contentDescription = "help",
                            modifier = Modifier.size(24.dp)  // Adjust the size of the icon as needed
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    AppDescriptionComponent()
                }
                // render loading section
                else {
                    LoadingComponent()
                }
            }
        }
    }
}


@Composable
fun PageDetails() {
    // title
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier, text = "Welcome", style = Typography.titleMedium
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
}

@Composable
fun AppDescriptionComponent() {
    // row (lapuk logo + text)
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = R.drawable.home_icon),
            contentDescription = "full logo",
            modifier = Modifier.size(120.dp),
            tint = br6
        )
        Text(
            modifier = Modifier.padding(end = 24.dp),
            text = "LAPUK is a waste recognition app designed to identify waste in a snap. It analyzes waste images and provides details for easier segregation. With LAPUK, you can contribute to a cleaner and greener environment—one scan at a time!",
            style = Typography.bodySmall,
            lineHeight = 12.sp * 1.6,
        )
    }
}

@Composable
fun LoadingComponent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(br2), // Background color similar to your theme
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp), color = br5, strokeWidth = 6.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // "Now Loading..." text
            Text(
                text = "Now Loading...", style = Typography.bodyLarge
            )
        }
    }
}
