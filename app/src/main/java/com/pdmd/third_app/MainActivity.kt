package com.pdmd.third_app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import com.pdmd.third_app.ui.theme.Third_appTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Third_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
             }
            }
        }
    }
}

@Composable
fun left(onClick: () -> Unit) {
    ElevatedButton(onClick = { onClick() }) {
        Text("Anterior")
    }
}

@Composable
fun right(onClick: () -> Unit) {
    ElevatedButton(onClick = { onClick() }) {
        Text("Siguiente")
    }
}

@Composable
fun next(){

}

fun before() {

}


@Composable
fun CheckboxDemo() {
    val checkedState = remember { mutableStateOf(false) }
    var switched by remember { mutableStateOf(false) }
    val paragraphVisibility = remember { mutableStateOf(false) }
    paragraphVisibility.value = checkedState.value

    ConstraintLayout (modifier = Modifier.fillMaxSize()){
        val (switch,textol,check,carrusel) = createRefs()

        val imageList = listOf(
            R.mipmap.monkey,
            R.mipmap.no,
            R.mipmap.no2,
            R.mipmap.no3
        )


        var currentImageIndex by remember { mutableStateOf(0) }

        //Barriers

        val botswitch = createBottomBarrier(switch)
        val toptextol = createTopBarrier(textol)
        val bottextol = createBottomBarrier(textol)
        val topcheck = createTopBarrier(check)
        val botcheck = createBottomBarrier(check)

        //Guides

        val topGuide = createGuidelineFromTop(0.15f)
        val startGuide = createGuidelineFromStart(0.1f)
        val endGuide = createGuidelineFromEnd(0.1f)
        val botGuide = createGuidelineFromBottom(0.15f)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .constrainAs(switch) {
                        top.linkTo(topGuide)
                        bottom.linkTo(toptextol)
                        start.linkTo(startGuide)
                        end.linkTo(endGuide)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Switch(
                    checked = switched,
                    onCheckedChange = { isChecked ->
                        switched = isChecked
                        paragraphVisibility.value = isChecked
                    })

                Text(
                    text = "Mostrar párrafo",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Row(modifier = Modifier
                .constrainAs(textol) {
                    top.linkTo(botswitch)
                    bottom.linkTo(topcheck)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    width = Dimension.fillToConstraints
                }) {
                if(paragraphVisibility.value ){
                    Text(
                        text = "Aquí un párrafo largo que se muestra u oculta según el valor del Checkbox " +
                                "pero como no da pa mas el texto aqui meto mas relleno que los fosquitos",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        Text(

            text = if (switched) "Switch activo" else "Switch no activo",
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(check) {
                    top.linkTo(bottextol)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                }
        )
        Row(modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .constrainAs(carrusel) {
                top.linkTo(botcheck)
                bottom.linkTo(botGuide)
                start.linkTo(startGuide)
                end.linkTo(endGuide)
            },
            horizontalArrangement = Arrangement.SpaceBetween) {

            Button(onClick = { currentImageIndex = (currentImageIndex - 1 + imageList.size) % imageList.size }) {
                Text("PREV")
            }
            Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource(id = imageList[currentImageIndex]),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .clip(shape = RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop)

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(onClick = { currentImageIndex = (currentImageIndex + 1) % imageList.size }) {
                        Text("NEXT")
                    }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun Main() {
    CheckboxDemo()
}


