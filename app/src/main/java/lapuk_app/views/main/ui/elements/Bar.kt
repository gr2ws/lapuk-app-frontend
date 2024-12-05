package lapuk_app.views.main.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.LapukTheme
import lapuk_app.views.main.ui.theme.br1
import lapuk_app.views.main.ui.theme.br5

/**
 * Composable function that creates a top bar with a centered logo.
 * The top bar uses the LapukTheme and has a background color and offset.
 */
@Composable
fun TopBar() {
    LapukTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.10f)
                .background(br5)
                .offset(y = 12.5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

/**
 * Composable function that creates a bottom navigation bar.
 * The bottom bar contains multiple navigation items, each with an icon and label.
 * When an item is clicked, it navigates to the corresponding screen.
 *
 * @param navController The NavController used for navigation.
 */
@Composable
fun BottomBar(navController: NavController, pageClicked : Int) {
    var selectedItem = pageClicked

    val items = listOf(
        "Home", "Segregate", "Articles", "Heatmap", "Info"
    )

    val icons = listOf(
        painterResource(id = R.drawable.home),
        painterResource(id = R.drawable.segregate),
        painterResource(id = R.drawable.articles),
        painterResource(id = R.drawable.heatmaps),
        painterResource(id = R.drawable.infocenter)
    )

    val navigationLabel = listOf(
        "home", "segregate", "articles", "heatmap", "info"
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.135f),
        containerColor = br1,
    ) {
        items.forEachIndexed { index, item ->

            NavigationBarItem(icon = {
                Icon(icons[index], contentDescription = item)
            },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(navigationLabel[index]) {
                        popUpTo(navController.graph.startDestinationId) {
                            if (index == 0)
                                inclusive = true
                            else
                                saveState = true
                        }
                        launchSingleTop = true
                        restoreState = (index != 0) // Don't restore state for 'Home'
                    }
     // DO NOT REMOVE THE CODE BELOW ! IT WILL RUIN THE SPEECH BUBBLE FUNCTIONALITY //
                if (item == "Info") { navController.navigate("info") }
            },
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.Black,
                    selectedIndicatorColor = br5,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    disabledIconColor = Color.Red,
                    disabledTextColor = Color.Red
                )
            )
        }
    }
}