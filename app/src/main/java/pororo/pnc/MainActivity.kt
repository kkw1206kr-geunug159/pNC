package pororo.pnc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pororo.pnc.ui.theme.PNCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PNCTheme {
                Header("pNC");
            }
        }
    }
}

@Composable
fun Header(hText:String,modifier:Modifier=Modifier){
    Column(
        Modifier.padding(start=20.dp,end=20.dp)
    ) {
        Spacer(modifier = Modifier.statusBarsPadding());
        Spacer(modifier = Modifier.height(100.dp));
        Text(
            text = "$hText",
            style = TextStyle(fontSize = 60.sp, fontWeight = FontWeight.ExtraBold)
        );
        Spacer(modifier=Modifier.height(20.dp));
        ToggleButton(0);
    }
}

@Composable
fun ToggleButton(ifOn:Int){
    Row(modifier=Modifier
        .height(35.dp)
        .width(70.dp)
        .clip(RoundedCornerShape(35.dp))
        .background(Color(55,126,240,255))
        .background(Color.LightGray),
        verticalAlignment= Alignment.CenterVertically){
        Box(Modifier
            .size(35.dp)
            .clip(RoundedCornerShape(35.dp))
            .background(Color.White))
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PNCTheme {
        Greeting("Android")
    }
}