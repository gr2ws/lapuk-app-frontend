package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lapuk_app.views.main.ui.theme.Typography

@Composable
fun ContactUsPage(){
    Column() {

        //title
        Box(
            modifier = Modifier
            .padding(vertical = 18.dp)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = Typography.titleMedium,
                text = "Contact Us"
            )
        }

        //body
        Box(
            modifier = Modifier
                .padding(vertical = 18.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = Typography.bodyMedium,
                text =
                    """
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur
                    """.trimIndent()
            )
        }

        //button
        Box(
            modifier = Modifier
                .padding(vertical = 18.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = Typography.titleSmall,
                text = "Insert Button Here"
            )
        }

        //bottom-text
        Box(
            modifier = Modifier
                .padding(vertical = 18.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = Typography.bodyMedium,
                text =
                """
                    Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                """.trimIndent()
            )
        }
    }
}