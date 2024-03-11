package presentation.widgets

import GGSansBold
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentation.components.ButtonComponent
import presentations.components.TextComponent

@Composable
fun CheckOutSummaryWidget() {
    val columnModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
        .height(200.dp)
        .fillMaxWidth()

    val buttonStyle = Modifier
        .padding(bottom = 10.dp, top = 10.dp)
        .fillMaxWidth()
        .height(50.dp)

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = columnModifier) {
         Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
             TextComponent(
                 text = "Sub-total",
                 fontSize = 18,
                 fontFamily = GGSansSemiBold,
                 textStyle = MaterialTheme.typography.h6,
                 textColor = Color.Gray,
                 textAlign = TextAlign.Right,
                 fontWeight = FontWeight.Bold,
                 lineHeight = 20,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis,
                 textModifier = Modifier.fillMaxWidth(0.50f)
             )
             TextComponent(
                 text = "$150,000",
                 fontSize = 18,
                 fontFamily = GGSansSemiBold,
                 textStyle = MaterialTheme.typography.h6,
                 textColor = Color.Gray,
                 textAlign = TextAlign.Right,
                 fontWeight = FontWeight.Bold,
                 lineHeight = 20,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis,
                 textModifier = Modifier.fillMaxWidth()
             )
         }


        Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
            TextComponent(
                text = "Delivery Fee",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Bold,
                lineHeight = 20,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier.fillMaxWidth(0.50f)
            )
            TextComponent(
                text = "Free",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Bold,
                lineHeight = 20,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
            TextComponent(
                text = "Total",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Bold,
                lineHeight = 20,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier.fillMaxWidth(0.50f)
            )
            TextComponent(
                text = "$185,900",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Bold,
                lineHeight = 20,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier.fillMaxWidth()
            )
        }

        ButtonComponent(modifier = buttonStyle, buttonText = "Proceed To CheckOut", borderStroke = BorderStroke(1.dp, Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(25.dp), textColor = Colors.primaryColor, style = TextStyle()){}

    }

}