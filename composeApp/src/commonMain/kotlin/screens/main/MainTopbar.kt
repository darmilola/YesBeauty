package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansBold
import GGSansRegular
import GGSansSemiBold
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import components.ImageComponent
import components.TextComponent
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import screens.authentication.attachWaveIcon

@Composable
fun MainTopBar(mainViewModel: MainViewModel) {

    val rowModifier = Modifier
        .padding(top = 55.dp, end = 0.dp)
        .height(45.dp)
        .background(color = Color.Transparent)

        Box(modifier = rowModifier) {
            centerTopBarItem(mainViewModel)
            rightTopBarItem(mainViewModel)
        }
}


@Composable
fun leftTopBarItem(currentScreen: Int = 0) {
    val rowModifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

    Box(modifier = rowModifier
    ) {
       welcomeToProfile()
    }
}

@Composable
fun welcomeToProfile(){
    val rowModifier = Modifier
        .padding(start = 20.dp, top = 5.dp)
        .fillMaxWidth()
    MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                text = "Welcome ",
                fontSize = 25,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30
            )
            TextComponent(
                text = "Jackson",
                fontSize = 25,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color(color = 0xFFFA2D65),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30
            )
            TextComponent(
                text = ",",
                fontSize = 25,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30
            )
        }
    }
}

@Composable
fun rightTopBarItem(mainViewModel: MainViewModel) {
    val tabNavigator = LocalTabNavigator.current
    val modifier = Modifier
        .padding(end = 10.dp)
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

    val indicatorModifier = Modifier
        .padding(start = 8.dp, bottom = 25.dp, end = 4.dp)
        .background(color = Color.Transparent)
        .size(14.dp)
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(color = 0xFFFA2D65),
                    Color(color = 0xFFFA2D65)
                )
            ),
            shape = RoundedCornerShape(7.dp)
        )

    Box(modifier = modifier,
        contentAlignment = Alignment.CenterEnd
        ) {
            ImageComponent(imageModifier = Modifier.size(35.dp).clickable {
                tabNavigator.current = NotificationTab(mainViewModel)
            }, imageRes = "notification.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
        Box(modifier = indicatorModifier){}
        }
    }

@Composable
fun centerTopBarItem(mainViewModel: MainViewModel) {
    val rowModifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

    val screenTitle: State<String> =  mainViewModel.screenTitle.observeAsState()

    Box(modifier = rowModifier,
        contentAlignment = Alignment.Center
    ) {
        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
       val shouldDisplay = screenTitle.value.contentEquals("Home")
            AnimatedVisibility(
                visible = !shouldDisplay,
                enter = fadeIn(animationSpec = keyframes {
                    this.durationMillis = 0
                }),
                exit = fadeOut(animationSpec = keyframes {
                    this.durationMillis = 0
                })
            ) {
                TextComponent(
                    text = screenTitle.value.toString(),
                    fontSize = 22,
                    fontFamily = GGSansRegular,
                    textStyle = TextStyle(fontFamily = GGSansSemiBold, letterSpacing = TextUnit(0.5F, TextUnitType.Sp)),
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    textModifier = Modifier.padding(top = 15.dp)
                )
            }
            AnimatedVisibility(
                visible = shouldDisplay,
                enter = fadeIn(animationSpec = keyframes {
                    this.durationMillis = 0
                }),
                exit = fadeOut(animationSpec = keyframes {
                    this.durationMillis = 0
                })
            ) {
                   leftTopBarItem()
            }
        }
    }
 }