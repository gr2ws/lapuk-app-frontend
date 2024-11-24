package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br6
import lapuk_app.views.main.ui.theme.wh1

data class AccordionItem(
    val question: String,
    val answerExpandable: String,
    var expanded: Boolean
)

@Preview(
    showBackground = true,
    device = "spec:width=1080px,height=2400px,dpi=440,navigation=buttons"
)

@Composable
fun FAQsPage() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = "Frequently Asked Questions",
                style = Typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                text = "Got some questions in mind on LAPUK? Check here--you’ll likely find your answer!",
                style = Typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .padding(vertical = 18.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Description
            AccordionModel()
        }
    }
}

@Composable
fun AccordionModel() {
    val accordionItems = listOf(
        AccordionItem(
            "What is the motivation behind the LAPUK app?",
            "LAPUK was made with a goal in mind--to help tackle segregation woes and build an efficient system to allow users to be in touch with their green thumb. The app was made in hopes of supporting recycling efforts and of helping promote legislations aimed towards keeping the environment healthy. ",
            false
        ),
        AccordionItem("How does the app do its detection/classification?",
            "The application utilizes Image Recognition and Classification through a trained AI/Machine Learning model created from an open-source dataset found online in Kaggle. This model was trained using Python, and was implemented in Flutter during development through the use of an API request.",
            false),
        AccordionItem("Does the application show local landfill areas?",
            "Yes. However, the scope of the application is currently limited to landfill areas around Negros Oriental, Philippines. GPS-enabled heatmap systems under the HEATMAPS feature of the application may be integrated in the future to allow users to send their waste collections to facilities that can more properly handle their waste, and possibly recycle them or convert them into sources of energy.",
            false),
        AccordionItem("How does LAPUK ensure data privacy & security?",
            "LAPUK requires only the camera as the main permission to be granted prior to use of the application. Images captured are stored only in the device’s local database for records-reading per session, and are not stored in any cloud server over the internet. Contact details received via the CONTACT US feature are NOT stored by the LAPUK team.",
            false),
        AccordionItem("Does LAPUK need network connectivity to work?",
            "For the full experience, users are encouraged to use the LAPUK application with Internet access, particularly to enjoy the full extent of features such as those in the ARTICLES and HEATMAPS sections.",
            false)
    )

    var chevronID: Int

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        // Accordion Element
        accordionItems.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = wh1)
                    .border(0.5.dp, Color.Gray),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    style = Typography.bodySmall,
                    text = item.question,
                    modifier = Modifier
                        .weight(0.95f)
                        .padding(horizontal = 12.dp)
                )
                chevronID =
                    if (!item.expanded) R.drawable.chevron_down
                    else R.drawable.chevron_up
                Icon(
                    painterResource(chevronID),
                    contentDescription = "description",
                    modifier = Modifier
                        .weight(0.05f)
                        .clickable {
                            item.expanded = !item.expanded
                        }
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}






