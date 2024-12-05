package lapuk_app.views.main.ui.pages

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br4
import lapuk_app.views.main.ui.theme.wh1
import androidx.compose.ui.text.TextStyle

@Preview(showBackground = true,
    device = "spec:width=1080px,height=2400px,dpi=440,navigation=buttons"
)
@Composable
fun ContactUsPage(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 52.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        // title //
        Box(
            modifier = Modifier
                .padding(vertical = 32.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = Typography.titleMedium,
                text = "CONTACT US"
            )
        }

        // body //
        Box(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 34.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = Typography.bodyLarge,
                text = "For any comments, suggestions, or business inquiries, feel free to send us a message via GMail."
                    .trimIndent(),
                textAlign = TextAlign.Center
            )
        }

        val context = LocalContext.current

        // button //
        Box(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 12.dp)
                .height(88.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = br4)
                .clickable {
                    openGmail(context)
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(shape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "A google logo for gmails",
                        modifier = Modifier.size(46.dp)
                    )
                }
                Text(
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
                    ),
                    color = wh1,
                    text = "Send an Email to the LAPUK team",
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                )
            }
        }

        //bottom-text
        Box(
            modifier = Modifier
                .padding(vertical = 18.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 36.dp),
                style = Typography.bodyLarge,
                text = "Your thoughts are all greatly appreciated!".trimIndent(),
                textAlign = TextAlign.Center
            )
        }
    }
}

fun openGmail(context: Context) {

    val recipientEmail = "lanzimalto@su.edu.ph"
    val intent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
        putExtra(Intent.EXTRA_SUBJECT, "Comment/Suggestion/Complaint")
        putExtra(Intent.EXTRA_TEXT, "Dear LAPUK Team:\n\n [Insert your message here]")
        type = "message/rfc822"
        setPackage("com.google.android.gm")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context,
            "The Gmail app is not installed on this phone. " +
                    "Please install and try again.", Toast.LENGTH_LONG).show()
    }
}