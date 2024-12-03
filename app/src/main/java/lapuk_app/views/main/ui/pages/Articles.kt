package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.Typography

data class Article(
    val imageResource: Int,
    val description: String,
    val popupText: String,
    val url: String
) {/*...*/}

@Composable
fun ArticlesPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ARTICLES", style = Typography.titleMedium, modifier = Modifier.padding(top = 48.dp))
        Text(
            "Exposure to landfill areas pose risks to your health. Stay informed with these health articles from the internet:",
            fontSize = 14.sp,
            modifier = Modifier
                .width(300.dp)
                .padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(top = 5.dp, start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFD2B48C))
        ) {
            ArticleList()
        }
    }
}

@Composable
fun ArticleList() {
    val articles = listOf(
        Article(
            R.drawable.img1,
            "Landfills - National Geographic Education",
            "A landfill is a designated area for waste disposal, aimed at minimizing health & environmental impacts. Primarily used for domestic waste, landfills can lead to pollution if mismanaged. Recycling and composting help mitigate these impacts by decreasing the volume of waste that needs disposal..",
            "https://education.nationalgeographic.org/resource/landfills/"
        ),
        Article(
            R.drawable.img2,
            "An overview of the environmental pollution and health effects associated with waste landfilling and open dumping",
            "The study explores landfilling types and the existence of illegal open dumpsites. It also notes the impacts linked to landfills, especially health effects to nearby populations (including (non-)carcinogenic ones due to pollution and gas emissions).",
            "https://pmc.ncbi.nlm.nih.gov/articles/PMC9399006/?fbclid=IwZXh0bgNhZW0CMTEAAR2inGlHdBNvEnLI5y7CYH-xyQwMfU1vGWRd1MiPlggzH3yHX01iXWLQHSg_aem_AOIdQIi_eRy5fOOQFjc_iw"
        ),
        Article(
            R.drawable.img3,
            "Important Things to Know About Landfill Gas",
            "Landfills produce a diverse spectrum of gases, chief of which are methane and carbon monoxide in addition to the minor amounts of ammonia, hydrogen sulfide and volatile Carbon compounds. This article dives deeper on how such gases are generated from the degradation of waste organics in the landfill.",
            "https://www.health.ny.gov/environmental/outdoors/air/landfill_gas.htm?fbclid=IwZXh0bgNhZW0CMTEAAR3AedztPFfPZMbjB18ZMIuLtN323CJNMyWUBT0CEE35Rnhefr54QayanXU_aem_m1Q92lbabTLkPoa2uRsTiA"
        ),
        Article(
            R.drawable.img4,
            "Landfill Impacts on the Environment—Review",
            "In reality, landfilling is and will remain common due to low cost & technical simplicity. This paper discusses the drawbacks and states how individual initiatives to manage waste, impose stricter policies, and better waste management systems and technologies are needed to mitigate these drawbacks.",
            "https://www.researchgate.net/publication/336252254_Landfill_Impacts_on_the_Environment-Review"
        ),
        Article(
            R.drawable.img5,
            "Environmental Impact of Solid Waste Landfilling",
            "This article addresses various environmental impacts of landfilling solid waste such as emissions of greenhouse gases, contamination of groundwater, and disruption of land utilization. Overall, it calls for better waste handling and alternative waste disposal techniques to minimize these harmful impacts.",
            "https://www.researchgate.net/publication/382264936_Environmental_Impact_of_Solid_Waste_Landfilling"
        ),
        Article(
            R.drawable.img6,
            "Landfills: a serious problem for the environment",
            "Landfills are the antithesis of sustainability., the article decrees. It also discusses the waste containments of landfill sites (~40%) and its effects on millions of people worldwide. It further mentions how landfills pose threats to the climate, to biodiversity, land value, and so on.\n",
            "https://www.activesustainability.com/environment/landfills-serious-problem-environment/?fbclid=IwZXh0bgNhZW0CMTEAAR1gkawjmEw4FbeNibuDXWP4a7B80QqusVd9dYKnkr_bIZGy0qsOfv-TgKY_aem_54wYDs-QDV6sLbiRcrHsfQ"
        ),
        Article(
            R.drawable.img7,
            "Health and Environmental Risks of Residents Living Close to a Landfill: A Case Study of Thohoyandou Landfill",
            "This study investigates the impacts of landfill proximity on the environment and the well-being of residents within 100–2000m from the site, and finds that 78% reported severe air contamination and health issues. The report also explores options to mitigate these concerns.",
            "https://pmc.ncbi.nlm.nih.gov/articles/PMC6617357/?fbclid=IwZXh0bgNhZW0CMTEAAR3bykQsYsJ2v3rZr34bAbIlRkPyG6hAnwLP8vapVvQXBiJSrYaL7CYA4Vs_aem_Aubh6ubBBkIZNji78wofsw"
        ),
        Article(
            R.drawable.img8,
            "The Hidden Damage of Landfills",
            "This article by the University of Colorado efficiently lists the environmental and social effects of establishing and working in landfills. It also suggests alternative ways to the public on how they can contribute. ",
            "https://www.colorado.edu/ecenter/2021/04/15/hidden-damage-landfills"
        ),
        Article(
            R.drawable.img9,
            "Systematic review of epidemiological studies on health effects associated with management of solid waste",
            "In this paper, the researchers analyzed toxin behavior in landfill waste, aiming to use existing epidemiological literature on health effects near dumpsites & among waste facility workers to produce risk estimates for health impact assessments.",
            "https://ehjournal.biomedcentral.com/articles/10.1186/1476-069X-8-60"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(articles) { article ->
            ArticleCard(article)
        }
    }
}

@Composable
fun ArticleCard(article: Article) {
    var showPopup by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .clickable { showPopup = true },
    shape = RoundedCornerShape(15.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = article.imageResource),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(0.40f)
                .clip(RectangleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = article.description,
            style = Typography.bodySmall,
            lineHeight = 14.sp * 1.15,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(0.65f)
                .padding(10.dp)
        )
    }
}

    if (showPopup) {
    Dialog(
        onDismissRequest = { showPopup = false },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(1.dp),
            shape = RoundedCornerShape(20.dp)
                ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(650.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = article.imageResource),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            text = article.description, // Heading
                            style = Typography.labelLarge,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = article.popupText, // Actual text
                            style = Typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = { uriHandler.openUri(article.url) }, modifier = Modifier.padding(8.dp) .width(200.dp)) {
                            Text("Open Article in Browser", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}