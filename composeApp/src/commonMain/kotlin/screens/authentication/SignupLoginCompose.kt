package screens.authentication

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansBold
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ButtonComponent
import components.IconButtonComponent
import components.ImageComponent
import components.TextComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpLoginCompose(currentScreen: Int = 0) {

    val viewModel: AuthenticationViewModel = AuthenticationViewModel()
    val authenticationScreenData = viewModel.authenticationScreenData ?: return


    val  rootModifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color(color = 0xFFFBFBFB))


    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        val topLayoutModifier =
            Modifier.fillMaxWidth()
                .fillMaxHeight(0.90f)
                .background(color = Color(color = 0xFFFBFBFB))


        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                attachCancelIcon()
                welcomeToBeaMo()
                attachAuthenticationText(currentScreen)
                attachAuthenticationButton()
            }
            attachAuthenticationTypeChangeView(currentScreen)


        }
    }
}

@Composable
fun attachCancelIcon() {
    val navigator = LocalNavigator.currentOrThrow
    val modifier = Modifier
        .padding(15.dp)
        .clickable {
            navigator.replace(AuthenticationScreen(-1))
        }
        .size(22.dp)
    ImageComponent(imageModifier = modifier, imageRes = "cancel_icon.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
}

@Composable
fun attachWaveIcon() {
    val modifier = Modifier
        .padding(end = 10.dp)
        .size(30.dp)
    ImageComponent(imageModifier = modifier, imageRes = "wave_hands.png")
}

@Composable
fun attachAuthenticationText(currentScreen: Int = 0) {
    val authText: String = if(currentScreen == 0){
        "Login to Your Account"
    }
    else{
        "Sign Up for Beamo"
    }
    val rowModifier = Modifier
        .padding(top = 30.dp, start = 10.dp)
        .fillMaxWidth()

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                text = authText,
                fontSize = 20,
                fontFamily = GGSansRegular,
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
fun attachAuthenticationButton() {
    val columnModifier = Modifier
        .padding(top = 50.dp, start = 10.dp)
        .fillMaxWidth()

    val navigator = LocalNavigator.currentOrThrow

    val buttonStyle = Modifier
        .padding(bottom = 15.dp)
        .fillMaxWidth(0.95f)
        .height(50.dp)

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = columnModifier
        ) {
            IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Facebook", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "facebook_icon.png"){
                navigator.replace(AuthenticationScreen(0))
            }
            IconButtonComponent(modifier = buttonStyle, buttonText = "Use Phone Number", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "cell_phone.png")
            IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Twitter", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "twitter_icon.png")
            IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Google", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "google_icon.png")
            IconButtonComponent(modifier = buttonStyle, buttonText = "Continue with Instagram", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFFBFBFB)), fontSize = 16, shape = RoundedCornerShape(28.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "instagram_icon.png")
        }
    }
}


@Composable
fun authenticationTypeChangeText(currentScreen: Int = 0) {
    val rowModifier = Modifier
        .padding(top = 20.dp, start = 10.dp)
        .fillMaxWidth()

    val authText: String = if(currentScreen == 0){
        "Login"
    }
    else{
        "Sign Up"
    }

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                textModifier = modifier,
                text = "Don't have an account?",
                fontSize = 15,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30
            )
            TextComponent(
                textModifier = modifier,
                text = authText,
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color(color = 0xFFFA2D65),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                lineHeight = 30
            )
        }
    }
}


@Composable
fun attachAuthenticationTypeChangeView(currentScreen: Int = 0) {
    val navigator = LocalNavigator.currentOrThrow
    val rowModifier = Modifier
        .background(color = Color(color = 0x70EBEBEB))
        .fillMaxHeight()
        .fillMaxWidth()
        .clickable {
            navigator.replace(AuthenticationScreen(currentScreen))
        }

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            authenticationTypeChangeText(currentScreen = currentScreen)
        }
    }
}

@Composable
fun welcomeToBeaMo(){
    val rowModifier = Modifier
        .padding(top = 50.dp)
        .fillMaxWidth()
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            attachWaveIcon()
            TextComponent(
                text = "Welcome to BeaMo",
                fontSize = 23,
                fontFamily = GGSansBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30
            )
        }
    }
}


