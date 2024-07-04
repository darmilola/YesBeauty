package presentation.widgets

import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Enums.AuthSSOScreenNav
import domain.Models.PlatformNavigator
import presentation.authentication.PhoneInputScreen
import presentation.authentication.WelcomeScreen
import presentations.components.ImageComponent

@Composable
fun PageBackNavWidget(onBackPressed: (() -> Unit)) {
    val boxModifier = Modifier
            .border(border = BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(15.dp))
            .background(color = Color.Transparent)
            .clickable {
              onBackPressed()
              }
            .size(45.dp)
    Box(modifier = boxModifier,
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(imageModifier = Modifier.size(20.dp), imageRes = "drawable/nav_back_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
        }
}

@Composable
fun AuthenticationBackNav(goToScreen: Int = AuthSSOScreenNav.AUTH_LOGIN.toPath(), platformNavigator: PlatformNavigator) {
    val navigator = LocalNavigator.currentOrThrow
    PageBackNavWidget {
        if(goToScreen == AuthSSOScreenNav.WELCOME_SCREEN.toPath()){
            navigator.replaceAll(WelcomeScreen(platformNavigator))
        }
        else if(goToScreen == AuthSSOScreenNav.PHONE_SCREEN.toPath()){
            navigator.replaceAll(PhoneInputScreen(platformNavigator))
        }
    }
}
