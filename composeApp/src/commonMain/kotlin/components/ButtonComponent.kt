package components

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
public fun ButtonComponent(modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, onClick: ()-> Unit) {
       Button(
             onClick = {
                  onClick()
             },
             border = borderStroke,
             shape = shape,
             modifier = modifier,
             colors = colors,
             elevation =  ButtonDefaults.elevation(
               defaultElevation = 0.dp,
               pressedElevation = 0.dp,
               disabledElevation = 0.dp
           )
       ){
           TextComponent(
               text = buttonText, fontSize = fontSize, textStyle = style, textColor = textColor, textAlign = TextAlign.Center,
               fontWeight = FontWeight.SemiBold)
        }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
public fun IconButtonComponent(modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, iconRes: String, onClick: (() -> Unit)? = null) {
    val rowModifier = Modifier
        .fillMaxWidth()
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {


        Button(
                onClick = {
                    if (onClick != null) {
                        onClick()
                    }
                },
                border = borderStroke,
                shape = shape,
                modifier = modifier,
                colors = colors,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                )
            ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = rowModifier
            ) {

                val iconModifier = Modifier
                    .padding(top = 5.dp)
                .size(28.dp)

                val iconBoxModifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.10f)

                val textModifier = Modifier
                    .padding(top = 7.dp, end = 5.dp)
                    .fillMaxHeight()
                    .fillMaxWidth(0.90f)
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = iconBoxModifier
                ) {
                    ImageComponent(imageModifier = iconModifier, imageRes = iconRes)
                }
              TextComponent(
                text = buttonText,
                fontSize = fontSize,
                textStyle = style,
                textColor = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = GGSansBold,
                textModifier = textModifier
            )
        }
    }
    }
}


@Composable
fun GradientButton(
    modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, gradient : Brush, onClick: ()-> Unit) {
    Button(
        onClick = {
             onClick()
        },
        colors = colors,
        elevation =  ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
    )) {
        Box(
            modifier = modifier
                .padding(start = 5.dp, end = 5.dp)
                .clip(shape)
                .background(gradient),
                contentAlignment = Alignment.Center,
        ) {
            TextComponent(
                text = buttonText, fontSize = fontSize, textStyle = style, textColor = textColor, textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold)
        }
    }
}