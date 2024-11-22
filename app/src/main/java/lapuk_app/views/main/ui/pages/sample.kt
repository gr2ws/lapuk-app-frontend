package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lapuk_app.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import lapuk_app.views.main.ui.theme.Typography

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwipeToCyclePeople() {
    // List of people
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

    // Pager state to control the current position
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState,
            count = people.size,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val person = people[page]
            PersonCard(person = person)
        }

        // Optional: Display current person's name
        Text(
            text = people[pagerState.currentPage].name,
            style = Typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun PersonCard(person: Person) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = person.image,
            contentDescription = person.name,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SwipeToCyclePeople()
}
