package screens

import AppTheme.AppColors
import AppTheme.AppBoldTypography
import GGSansBold
import GGSansSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.PinkGradientBackground
import components.TextComponent
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.authentication.WelcomeScreen
import widgets.SplashScreenWidget

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
fun SplashScreenCompose() {
   val  modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(color = 0xFFF43569))
    val navigator = LocalNavigator.currentOrThrow
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        // AnimationEffect
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            PinkGradientBackground()
            SplashScreenWidget(textStyle = MaterialTheme.typography.h6)
            Box(modifier = Modifier
                .padding(bottom = 70.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter) {
                businessTagline()
            }
        }
        LaunchedEffect(key1 = true) {
            delay(3000L)

            navigator.replaceAll(WelcomeScreen)
        }
    }

 }


@Composable
fun businessTagline(){
    val rowModifier = Modifier
        .fillMaxWidth()
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                text = "Beauty Forever...",
                fontSize = 30,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.White,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                lineHeight = 30
            )
        }
    }
}

object SplashScreen : Screen {
    @Composable
    override fun Content() {
        SplashScreenCompose()
    }
}

