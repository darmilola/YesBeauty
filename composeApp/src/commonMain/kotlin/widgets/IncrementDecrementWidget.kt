package widgets
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.ButtonComponent
import components.ImageComponent
import components.TextComponent

@Composable
fun IncrementDecrementWidget(){
    var counter by remember { mutableStateOf(1) }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(60.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .background(color = Color(color = 0xfffa2d65), shape = RoundedCornerShape(10.dp))
        ) {
            TextComponent(
                text = "-",
                fontSize = 30,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h4,
                textColor = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                textModifier = Modifier
                    .clickable { if(counter > 1)counter -= 1 }
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
        ) {

            TextComponent(
                text = counter.toString(),
                fontSize = 27,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h4,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                textModifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
        }


        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .background(color = Color(color = 0xfffa2d65), shape = RoundedCornerShape(10.dp))
        ) {
            TextComponent(
                text = "+",
                fontSize = 30,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h4,
                textColor = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                textModifier = Modifier
                    .clickable { counter += 1 }
            )
        }

    }

}



@Composable
fun CartIncrementDecrementWidget(){
    var counter by remember { mutableStateOf(1) }

    val decrementBorderColor: Color = if(counter == 1) Color(0x80CCCCCC) else Color(color = 0xfffa2d65)
    val decrementBg: Color = if(counter == 1) Color(0x20CCCCCC) else Color(color = 0x20fa2d65)
    val decrementImgRes: String = if(counter == 1) "drawable/remove_icon.png" else "drawable/minus_icon.png"
    val decrementImgTint: Color = if(counter == 1) Color(0x80CCCCCC) else Color(color = 0xfffa2d65)
    val decrementBorderWidth: Int = if(counter == 1) 1 else 2

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentWidth().height(50.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(45.dp)
                .width(45.dp)
                .border(border = BorderStroke(decrementBorderWidth.dp, decrementBorderColor), shape = RoundedCornerShape(15.dp))
                .background(shape = RoundedCornerShape(15.dp), color = decrementBg)
        ) {

            ImageComponent(imageModifier = Modifier.padding(12.dp).fillMaxSize()
                .clickable { if(counter > 1)counter -= 1 }, imageRes = decrementImgRes, colorFilter = ColorFilter.tint(color = decrementImgTint))
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .height(50.dp)
                .width(50.dp)
        ) {

            TextComponent(
                text = counter.toString(),
                fontSize = 27,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h4,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                textModifier = Modifier.padding(start = 5.dp, end = 5.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(45.dp)
                .width(45.dp)
                .border(border = BorderStroke(2.dp, Color(color = 0xfffa2d65)), shape = RoundedCornerShape(15.dp))
                .background(shape = RoundedCornerShape(15.dp), color = Color(color = 0x20fa2d65))
        ) {

            ImageComponent(imageModifier = Modifier.padding(12.dp).fillMaxSize()
                .clickable { counter += 1 }, imageRes = "drawable/add_icon.png", colorFilter = ColorFilter.tint(color = Color(color = 0xfffa2d65)))
        }

    }

}