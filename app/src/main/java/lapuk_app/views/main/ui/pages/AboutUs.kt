package lapuk_app.views.main.ui.pages
//
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.swipeable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br4
import lapuk_app.views.main.ui.theme.br6
import lapuk_app.views.main.ui.theme.wh1

data class Person(val name: String, val image: Painter, val body: String) {/*...*/}

@Preview(
    showBackground = true,
    device = "spec:width=1080px,height=2400px,dpi=440,navigation=buttons"
)
@Composable
fun AboutUsPage() {
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
                text = "About Us"
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = Typography.bodyLarge,
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

                // next container dynamically set
                PicturesContainer()
            }
        }
    }
}


@Composable
fun PicturesContainer() {

    val people = listOf(
        Person(
            name = "Adrian Philip V. Amihan",
            image = painterResource(id = R.drawable.adrian_amihan),
            body = "Adrian is a dedicated and curious student with a positive attitude, he consistently strives to improve and supports his peers in group work. His resilience and growth mindset help him tackle challenges and achieve his goals."),
        Person(
            name = "Gian Ross Wennette Asunan",
            image = painterResource(id = R.drawable.gian_asunan),
            body = "Gian takes his studies seriously, always putting in the time and effort to do his best. His approach to assignments and attention to detail show how much he values learning, building strong skills along the way."),
        Person(
            name = "Lanz Alexander I. Malto",
            image = painterResource(id = R.drawable.lanz_malto),
            body = "A reliable and positive presence--Lanz is always willing to help out and keep everyone organized. His contributions motivate others to stay focused, creating a productive and friendly team experience.")
    )

    val currentPage by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        ActivePerson(people[currentPage])
        InactivePerson(people.filterIndexed { index, _ -> index != currentPage })
    }
        TextContainer(people[currentPage])
}

// receives the current active person and sets its UI.
@Composable
fun ActivePerson(currentPerson: Person) {

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .size(80.dp)
            .clip(shape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = currentPerson.image,
            contentDescription = currentPerson.name,
            modifier = Modifier.size(80.dp)
        )
    }
}

// receives the currently-inactive people and sets their UI.
@Composable
fun InactivePerson(inactivePersons: List<Person>) {

    inactivePersons.forEach { person ->
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

@Composable
fun TextContainer(currentPerson: Person) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .height(230.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = wh1),
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
                modifier = Modifier.padding(top = 28.dp),
                style = Typography.labelLarge,
                text = currentPerson.name,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                style = Typography.bodyMedium,
                text = currentPerson.body.trimIndent(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            SocialMediaContainer()
        }
    }
}

@Composable
fun SocialMediaContainer() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val icons = listOf(
            painterResource(id = R.drawable.instagram) to "IG icon",
            painterResource(id = R.drawable.linkedin) to "LI icon",
            painterResource(id = R.drawable.facebook) to "FB icon"
        )

        icons.forEach { (icon, description) ->

            Box(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .size(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = description,
                    modifier = Modifier
                        .size(38.dp)
                        .padding(bottom = 2.dp)
                )
            }
        }
    }
}


//    Row(
//        modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        // [DESIGN] CURRENTLY ACTIVE
//        Box(
//            modifier = Modifier
//                .padding(horizontal = 16.dp)
//                .size(80.dp)
//                .clip(shape),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.adrian_amihan),
//                contentDescription = "Amihan Picture",
//                modifier = Modifier.size(80.dp)
//            )
//        }
//
//        // [DESIGN] CURRENTLY INACTIVE
//        Box(
//            modifier = Modifier
//                .padding(horizontal = 16.dp)
//                .size(60.dp)
//                .clip(shape),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.gian_asunan),
//                contentDescription = "Asunan Picture",
//                modifier = Modifier.size(60.dp)
//            )
//            Box(
//                modifier = Modifier
//                    .matchParentSize()
//                    .background(Color.Black.copy(alpha = 0.5f))
//            )
//        }
//
//        // [DESIGN] CURRENTLY INACTIVE
//        Box(
//            modifier = Modifier
//                .padding(horizontal = 16.dp)
//                .size(60.dp)
//                .clip(shape),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.lanz_malto),
//                contentDescription = "Malto Picture",
//                modifier = Modifier.size(60.dp)
//            )
//            Box(
//                modifier = Modifier
//                    .matchParentSize() // Fill the parent size (same size as the image)
//                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent dark overlay
//            )
//        }
//    }
//}

