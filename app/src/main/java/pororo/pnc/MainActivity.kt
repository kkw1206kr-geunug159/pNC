package pororo.pnc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pororo.pnc.ui.theme.PNCTheme

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PNCTheme() {
                var screenNum=1
                val scrollState= rememberScrollState()
                val scrollState2= rememberScrollState()
                val configuration= LocalConfiguration.current
                val screenW=configuration.screenWidthDp.dp
                val screenH=configuration.screenHeightDp.dp
                Row(Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .systemBarsPadding()
                    .fillMaxSize(1f)){
                    if(screenW<600.dp){
                        Column(Modifier
                            .verticalScroll(scrollState)
                            .padding(start=20.dp,end=20.dp)) {
                            Screen(screenNum,scrollState=scrollState)
                        }
                    }
                    else{
                        Column(Modifier
                            .verticalScroll(scrollState)
                            .padding(start=20.dp,end=20.dp)
                            .width(300.dp)) {
                            Screen(screenNum,scrollState)
                        }
                        Column(Modifier
                            .verticalScroll(scrollState2)
                            .padding(start=20.dp,end=20.dp)
                            .fillMaxWidth()){

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Screen(screenNum:Int,scrollState: ScrollState){
    if(screenNum==1){
        Header(stringResource(R.string.app_name), hasBack = true, scrollState = scrollState)
        textBox("") { }
        Spacer(modifier = Modifier.height(20.dp))
        MenuBox1("MenuBox1 Test 0",0)
        Spacer(modifier = Modifier.height(20.dp))
        MenuBox1("MenuBox1 Test 1",1)
        Spacer(modifier = Modifier.height(5.dp))
        MenuBox1("MenuBox1 Test 2",2)
        Spacer(modifier = Modifier.height(5.dp))
        MenuBox1("MenuBox1 Test 3",3)
        Spacer(modifier = Modifier.height(20.dp))
        ToggleButton(0)
    }
}

enum class PressState{Pressed,Idle}

fun Modifier.clickableToggle(ifOn:Int,onClick: () -> Unit)=composed{
    var pressState by remember{mutableStateOf(PressState.Idle)}
    val scale by animateFloatAsState(if(pressState==PressState.Pressed)0.95f else 1f)
    clickable(
        indication=null,
        interactionSource = remember { MutableInteractionSource() }
    ){
        onClick()
    }
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(pressState) {
            awaitPointerEventScope {
                pressState=if(pressState==PressState.Pressed){
                    waitForUpOrCancellation()
                    PressState.Idle
                }
                else{
                    awaitFirstDown(false)
                    PressState.Pressed
                }
            }
        }
}

fun Modifier.clickable2(onClick:()->Unit)=composed{
    var pressState by remember{mutableStateOf(PressState.Idle)}
    //val scale by animateFloatAsState(if(pressState==PressState.Pressed)0.95f else 1f)
    val animColor= animateColorAsState(
        targetValue = if(pressState==PressState.Pressed)Color(0xFFC8C8C8)else Color(0xFFFFFFFF),
        animationSpec = tween(150,0,LinearEasing)
    )
    clickable(
        indication=null,
        interactionSource = remember { MutableInteractionSource() }
    ){
        onClick()
    }
    this
        /*.graphicsLayer {
            scaleX=scale
            scaleY=scale
        }*/
        .background(animColor.value)
        .clip(RoundedCornerShape(50.dp))
        .pointerInput(pressState) {
            awaitPointerEventScope {
                pressState=if(pressState==PressState.Pressed){
                    waitForUpOrCancellation()
                    PressState.Idle
                }
                else{
                    awaitFirstDown(false)
                    PressState.Pressed
                }
            }
        }
}

@Composable
fun Header(hText:String, scrollState: ScrollState, hasBack: Boolean, modifier:Modifier=Modifier){
    if(hasBack==true){
        //var scrollPos=with(LocalDensity.current){scrollState.value.toDp()}.toString().toFloat()
        Column(
            Modifier.padding(start=10.dp,end=10.dp)
        ) {
            //Spacer(modifier = Modifier.statusBarsPadding())
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "$hText",
                style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier=Modifier.height(20.dp))
        }
    }
    else{
        Column(
            Modifier.padding(start=10.dp,end=10.dp)
        ) {
            //Spacer(modifier = Modifier.statusBarsPadding())
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "$hText",
                style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier=Modifier.height(20.dp))
        }
    }
}



@Composable
fun ToggleButton(ifOn:Int){
    Row(modifier=Modifier
        .height(30.dp)
        .width(50.dp)
        .clip(RoundedCornerShape(30.dp))
        .background(Color(55,126,240,255))
        .background(Color.Gray)
        .clickableToggle(ifOn,{}),
        verticalAlignment=Alignment.CenterVertically){
        Box(Modifier
            .padding(all=3.dp)
            .size(24.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White))
    }
}

@Composable
fun MenuBox1(mainText:String,roundP:Int){
    if(roundP==0){
        Row(modifier=Modifier
            .height(50.dp)
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(255,255,255,255))
            .clickable2{},
            verticalAlignment=Alignment.CenterVertically){
            Text(text="$mainText",style=TextStyle(fontSize=20.sp),modifier=Modifier.padding(start=15.dp));
        }
    }
    if(roundP==1){
        Row(modifier=Modifier
            .height(50.dp)
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(topStart=25.dp,topEnd=25.dp,bottomEnd=5.dp,bottomStart=5.dp))
            .background(Color(255,255,255,255))
            .clickable2{},
            verticalAlignment=Alignment.CenterVertically){
            Text(text="$mainText",style=TextStyle(fontSize=20.sp),modifier=Modifier.padding(start=15.dp));
        }
    }
    if(roundP==2){
        Row(modifier=Modifier
            .height(50.dp)
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(topStart=5.dp,topEnd=5.dp,bottomEnd=5.dp,bottomStart=5.dp))
            .background(Color(255,255,255,255))
            .clickable2{},
            verticalAlignment=Alignment.CenterVertically){
            Text(text="$mainText",style=TextStyle(fontSize=20.sp),modifier=Modifier.padding(start=15.dp));
        }
    }
    if(roundP==3){
        Row(modifier=Modifier
            .height(50.dp)
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(topStart=5.dp,topEnd=5.dp,bottomEnd=25.dp,bottomStart=25.dp))
            .background(Color(255,255,255,255))
            .clickable2{},
            verticalAlignment=Alignment.CenterVertically){
            Text(text="$mainText",style=TextStyle(fontSize=20.sp),modifier=Modifier.padding(start=15.dp));
        }
    }
}

@Composable
fun textBox(initText:String?,onClickConfirm:(text:String)->Unit){
    val text=remember{ mutableStateOf(initText?:"")}
    BasicTextField(
        value=text.value,
        onValueChange = {text.value=it},
        singleLine = true,
        textStyle=TextStyle(
            fontWeight=FontWeight.SemiBold,
            color=Color.Black,
            fontSize=16.sp
        ),
        decorationBox={innerTextField ->
            Row(Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color(0xFFE0E0E0))
                .padding(start=15.dp,end=15.dp),
                verticalAlignment = Alignment.CenterVertically){
                Icon(
                    imageVector= Icons.Rounded.Search,
                    contentDescription = "",
                    tint = Color.Gray
                )
                Spacer(modifier=Modifier.width(15.dp))
                innerTextField()
                if(text.value==""){
                    Text(text=stringResource(R.string.search_ph),color=Color.Gray,fontSize=16.sp,modifier=Modifier
                        .offset(x=-110.dp))
                }
            }
        }
    )
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