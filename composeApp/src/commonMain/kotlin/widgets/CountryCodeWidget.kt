package widgets

import GGSansRegular
import GGSansSemiBold
import Styles.Colors
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent

@Composable
fun CountryCodeView(countryCode: String, countryFlagRes: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
         horizontalArrangement = Arrangement.Start,
         verticalAlignment = Alignment.CenterVertically,
    ) {

        ImageComponent(imageModifier = Modifier.size(25.dp), imageRes = countryFlagRes)

        TextComponent(
            textModifier = Modifier.wrapContentWidth().padding(start = 15.dp), text = countryCode, fontSize = 20,
            textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp), fontFamily = GGSansSemiBold, fontWeight = FontWeight.Normal, textAlign = TextAlign.Start, color = Color.DarkGray), textColor = Color.DarkGray, textAlign = TextAlign.Left,
            fontWeight = FontWeight.Normal, lineHeight = 23, maxLines = 1,  overflow = TextOverflow.Ellipsis)
    }

}