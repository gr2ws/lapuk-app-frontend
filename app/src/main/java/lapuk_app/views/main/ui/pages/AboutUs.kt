package lapuk_app.views.main.ui.pages
//
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br6
import lapuk_app.views.main.ui.theme.wh1
import kotlin.math.abs

data class Person(
    val name: String,
    val image: Painter,
    val body: String,
    val igLink: String,
    val liLink: String,
    val fbLink: String
) {/*...*/ }

@Preview(
    showBackground = true,
    device = "spec:width=1080px,height=2400px,dpi=440,navigation=buttons"
)
@Composable
fun AboutUsPage() {
    val people = listOf(
        Person(
            name = "Adrian Philip V. Amihan",
            image = painterResource(id = R.drawable.adrian_amihan),
            body = "Adrian is a dedicated and curious student with a positive attitude, he consistently strives to improve and supports his peers in group work. His resilience and growth mindset help him tackle challenges and achieve his goals.",
            igLink = "https://www.instagram.com/kuyiiiiip_/",
            liLink = "https://www.linkedin.com/in/adrian-philip-amihan-828082339/",
            fbLink = "https://www.facebook.com/adrianphilip.amihan"
        ),
        Person(
            name = "Gian Ross Wennette Asunan",
            image = painterResource(id = R.drawable.gian_asunan),
            body = "Gian takes his studies seriously, always putting in the time and effort to do his best. His approach to assignments and attention to detail show how much he values learning, building strong skills along the way.",
            igLink = "https://www.instagram.com/giiiiiaaaaannn/",
            liLink = "https://www.linkedin.com/in/gian-asunan-64146333a/",
            fbLink = "https://www.facebook.com/g2r.w5a"
        ),
        Person(
            name = "Lanz Alexander I. Malto",
            image = painterResource(id = R.drawable.lanz_malto),
            body = "A reliable and positive presence--Lanz is always willing to help out and keep everyone organized. His contributions motivate others to stay focused, creating a productive and friendly team experience.",
            igLink = "https://www.instagram.com/saccharine__7393/",
            liLink = "https://www.linkedin.com/in/lanz-alexander-malto/",
            fbLink = "https://www.facebook.com/g2r.w5a"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), //page margin
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = Typography.titleMedium,
                text = "ABOUT US"
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 18.dp, start = 42.dp, end = 42.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = Typography.bodyMedium,
                text = "Learn more about the creators of the LAPUK app in this page!",
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth()
                .height(380.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = br6),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                var currentPage by remember { mutableIntStateOf(0) }
                val swipeThreshold = 88f // Adjust this value to control sensitivity //
                val currentPerson = people[currentPage]

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    people.forEachIndexed { index, person ->
                        if (index == currentPage) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .size(80.dp)
                                    .clip(shape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = person.image,
                                    contentDescription = person.name,
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .size(60.dp)
                                    .clip(shape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = person.image,
                                    contentDescription = person.name,
                                    modifier = Modifier.size(60.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(Color.Black.copy(alpha = 0.5f))
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                        .height(230.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = wh1)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { change, dragAmount ->

                                // Consume the change to avoid propagation to other gestures
                                change.consume()

                                // Apply the threshold for dragAmount before changing pages
                                if (abs(dragAmount) > swipeThreshold) {
                                    currentPage =
                                        when {
                                            dragAmount < 0 -> (currentPage + 1).coerceIn(0, people.size - 1) // Swipe left
                                            dragAmount > 0 -> (currentPage - 1).coerceIn(0, people.size - 1) // Swipe right
                                        else -> currentPage
                                    }
                                }
                            }
                        },
                    contentAlignment = Alignment.Center

                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            modifier = Modifier.padding(top = 20.dp),
                            text = currentPerson.name,
                            style = Typography.labelLarge,
                            fontSize = if (currentPage == 1) 23.sp else 24.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = currentPerson.body.trimIndent(),
                            style = Typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        SocialMediaContainer(currentPerson)

                        Spacer(modifier = Modifier.height(20.dp))

                    }
                }
            }
        }
    }
}

@Composable
fun SocialMediaContainer(currentPerson: Person) {

    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val icons = listOf(
            painterResource(id = R.drawable.instagram) to currentPerson.igLink,
            painterResource(id = R.drawable.linkedin) to currentPerson.liLink,
            painterResource(id = R.drawable.facebook) to currentPerson.fbLink,
        )

        icons.forEach { (icon, link) ->

            Box(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .size(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    modifier = Modifier
                        .size(38.dp)
                        .padding(bottom = 2.dp)
                        .clickable {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
                        },
                    contentDescription = "description"
                )
            }
        }
    }
}


