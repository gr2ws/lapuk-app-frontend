package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.Typography

@Preview(showBackground = true,
    device = "spec:width=1080px,height=2400px,dpi=440,navigation=buttons"
)
@Composable
fun PrivacyPolicyPage() {

    var currentPage by remember { mutableIntStateOf(1) }

    val bodyPart1 by remember { mutableStateOf("""
        The LAPUK app and its creators greatly respect its user’s privacy and is committed to protecting their data. The app collects little to no personal information, especially as the app is only meant for waste detection purposes.

        Images taken, uploaded, and processed in the SEGREGATE option are maintained only in the local database of the user’s device to service statistical requests on the user’s waste items.
    """.trimIndent()) }

    val pageLabel1 by remember { mutableStateOf("Page 1 of 2") }

    val bodyPart2 by remember { mutableStateOf("""
       Any contact details recorded via the CONTACT US option is kept STRICTLY confidential, and is used purely to manage app impressions and apply suggestions (if needed).
       
       Rest assured that the creators of the LAPUK app will never share your information with third-party organizations.
    """.trimIndent()) }

    val pageLabel2 by remember { mutableStateOf("Page 2 of 2")}


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // title
        Box(
            modifier = Modifier
                .padding(vertical = 18.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "PRIVACY POLICY STATEMENT",
                style = Typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }

        //body
        Box(
            modifier = Modifier.padding(vertical = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(24.dp),
                text =
                    if (currentPage == 1) { bodyPart1 }
                    else { bodyPart2 },
                style = Typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        // navigation
        Box(
            modifier = Modifier.padding(vertical = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                // [LEFT CHEVRON] //
                Icon(
                    painter = painterResource(id = R.drawable.chevrons_left),
                    contentDescription = "To Page 1",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(enabled = currentPage == 2) {
                            if (currentPage == 2) { currentPage -= 1 }
                        },
                    tint = if (currentPage == 2) Color.Black else Color.Gray
                )

                // [PAGE LABEL] //
                Text(
                    modifier = Modifier
                        .padding(12.dp),
                    text =
                        if (currentPage == 1) { pageLabel1 }
                        else { pageLabel2 },
                    fontSize = 14.sp
                )

                // [RIGHT CHEVRON] //
                Icon(
                    painterResource(id = R.drawable.chevrons_right),
                    contentDescription = "To Page 2",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(enabled = currentPage == 1) {
                            if (currentPage == 1) { currentPage += 1 }
                        },
                    tint = if (currentPage == 1) Color.Black else Color.Gray
                )
            }
        }

    }
}

