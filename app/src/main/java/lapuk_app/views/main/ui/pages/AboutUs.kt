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
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br4
import lapuk_app.views.main.ui.theme.br6
import lapuk_app.views.main.ui.theme.wh1


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
                PicturesContainer()
                TextContainer()
            }
        }
    }
}

@Composable
fun PicturesContainer() {
    // pictures container
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(shape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Amihan Picture",
                modifier = Modifier.size(46.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .size(60.dp)
                .clip(shape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Asunan Picture",
                modifier = Modifier.size(46.dp)
            )
        }
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(shape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Malto Picture",
                modifier = Modifier.size(46.dp)
            )
        }
    }
}

@Composable
fun TextContainer() {
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
                text = "Adrian Phillip V. Amihan",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp)) // Add desired spacing

            Text(
                style = Typography.bodyMedium,
                text =
                """
                            A dedicated and curious student with a positive attitude, he consistently strives to improve and supports his peers in group work. His resilience and growth mindset help him tackle challenges and achieve his goals.
                        """.trimIndent(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp)) // Add desired spacing

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
        Box(
            modifier = Modifier
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.instagram),
                contentDescription = "Amihan Picture",
                modifier = Modifier.size(40.dp).padding(bottom = 2.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.linkedin),
                contentDescription = "Asunan Picture",
                modifier = Modifier.size(40.dp).padding(bottom = 2.dp)
            )
        }
        Box(
            modifier = Modifier
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Malto Picture",
                modifier = Modifier.size(40.dp).padding(bottom = 2.dp) //
            )
        }
    }
}