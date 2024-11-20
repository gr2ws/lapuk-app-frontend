package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.br3
import lapuk_app.views.main.ui.theme.br4
import lapuk_app.views.main.ui.theme.br5

// * TODO: show column of list of content
// * TODO: fill in placeholder icons
// TODO: open camera
// TODO: take picture
// TODO: store image to local storage, format title (DATE_TIME) (Ask if sure)
// TODO: take list of stored images
// TODO: show list of stored images, with previews
// TODO: delete images (ask if sure)

// TODO: make backend to classify/detect image
// TODO: send image (ask if sure) (show error if no internet, no server)
// TODO: receive and show

@Composable
fun SegregatePage(navController: NavController) {
    val itemCount = 0
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(itemCount) { index ->
                ColumnItem(index)
            }
            item {
                Row {
                    if (itemCount <= 0) Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        Text(
                            text = "No images added yet...",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else Spacer(
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
        ) {
            IconButton(modifier = Modifier
                .size(70.dp)
                .border(3.dp, br5, shape = RoundedCornerShape(20.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                .background(br3, shape = RoundedCornerShape(20.dp)),
                onClick = {
                    navController.navigate("segregate/take-image") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                Icon(
                    modifier = Modifier.fillMaxSize(.85f),
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "add image",
                    tint = br5
                )
            }
        }
    }
}

@Composable
fun ColumnItem(index: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(0.5.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.clickable { TODO("Open image") }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp, vertical = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_w_name),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterVertically),
                    alignment = Alignment.CenterStart
                )
                Text(
                    text = "Item $index"
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(90.dp)
                    .width(70.dp), onClick = { TODO("Delete image") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "delete image",
                        tint = br4
                    )
                }
            }
        }
    }
}