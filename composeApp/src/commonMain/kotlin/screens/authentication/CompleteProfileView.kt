package screens.authentication

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansSemiBold
import Styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import components.ButtonComponent
import components.ImageComponent
import components.TextFieldComponent
import components.ToggleButton
import screens.main.MainScreen
import widgets.DropDownWidget
import widgets.PageBackNavWidget
import widgets.SubtitleTextWidget
import widgets.TitleWidget

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
fun CompleteProfileCompose() {
    val viewModel: AuthenticationViewModel = AuthenticationViewModel()
    val authenticationScreenData = viewModel.authenticationScreenData ?: return

    val  rootModifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color.White)

    val buttonStyle = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        .fillMaxWidth()
        .height(60.dp)

    val navigator = LocalNavigator.currentOrThrow


    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        val topLayoutModifier =
            Modifier
                .padding(top = 40.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .background(color = Color.White)


        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                AttachBackIcon()
                PageTitle()
                SubtitleTextWidget(text = "Lorem ipsum is placeholder text commonly used in Printing")
                ProfileImageUpdate()
                InputWidget(iconRes = "drawable/card_icon.png", placeholderText = "Preferred Name", iconSize = 40)
                InputWidget(iconRes = "drawable/email_icon.png", placeholderText = "Email", iconSize = 24)
                //InputWidget(iconRes = "drawable/security_icon.png", placeholderText = "Password", iconSize = 24, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), isPasswordField = true)
                AttachDropDownWidget()
                ToggleButton(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(15.dp), style = MaterialTheme.typography.h4, onLeftClicked = {

                }, onRightClicked = {

                }, leftText = "Male", rightText = "Female")

                /* EmailInput()
                PasswordInput()
                CountryInput()
                GenderInput()*/
                ButtonComponent(modifier = buttonStyle, buttonText = "Continue", colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = RoundedCornerShape(30.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4, borderStroke = null) {
                    navigator.replaceAll(MainScreen)
                }
            }
        }
    }
}


@Composable
fun ProfileImageUpdate() {
    Box(Modifier.fillMaxWidth().height(220.dp), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .padding(top = 20.dp, bottom = 5.dp)
                .size(200.dp)
                .clip(CircleShape)
                .border(
                    width = (2.5).dp,
                    color = Colors.primaryColor,
                    shape = CircleShape)
                .background(color = Color.Transparent)
        ) {
            val modifier = Modifier
                .padding(3.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = CircleShape
                )
                .fillMaxSize()
            ImageComponent(imageModifier = modifier, imageRes = "1.jpg")
        }
            EditProfilePictureButton()

    }
}

@Composable
fun AttachDropDownWidget(){
    val serviceTypes = listOf("SouthAfrica", "Nigeria","SouthAfrica", "Nigeria","SouthAfrica", "Nigeria","SouthAfrica",
        "Nigeria","SouthAfrica", "Nigeria","SouthAfrica", "Nigeria","SouthAfrica", "Nigeria","SouthAfrica", "Nigeria","SouthAfrica",
        "Nigeria","SouthAfrica", "Nigeria","SouthAfrica", "Nigeria","SouthAfrica", "Nigeria",)

        DropDownWidget(menuItems = serviceTypes)
}

@Composable
fun InputWidget(iconRes: String, placeholderText: String, iconSize: Int, keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), isPasswordField: Boolean = false) {
         var text by remember { mutableStateOf("") }
         var borderStroke by remember { mutableStateOf(BorderStroke(2.dp, color  = Color.Transparent)) }

            val modifier  = Modifier
                .padding(end = 10.dp, start = 10.dp, top = 20.dp)
                .fillMaxWidth()
                .height(70.dp)
                .border(border = borderStroke, shape = RoundedCornerShape(15.dp))
                .background(color = Colors.lightPrimaryColor, shape = RoundedCornerShape(15.dp))

             Row(modifier = modifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {

                Box(modifier = Modifier.fillMaxHeight().width(50.dp), contentAlignment = Alignment.Center){
                    ImageComponent(imageModifier = Modifier
                        .size(iconSize.dp), imageRes = iconRes, colorFilter = ColorFilter.tint(color = Color.Gray))
                }
                TextFieldComponent(
                    text = text,
                    readOnly = false,
                    textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp), fontFamily = GGSansSemiBold, fontWeight = FontWeight.Normal, textAlign = TextAlign.Start, color = Color.Gray),
                    modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(end = 10.dp),
                    keyboardOptions = keyboardOptions,
                    onValueChange = {
                        text = it
                    } , isSingleLine = true, placeholderText = placeholderText, onFocusChange = {
                        it ->
                        borderStroke = if (it){
                            BorderStroke(2.dp, color  = Colors.primaryColor)
                        } else{
                            BorderStroke(2.dp, color  = Color.Transparent)
                        }
                    },
                    isPasswordField = isPasswordField
                )
            }
       }


@Composable
fun EditProfilePictureButton() {
    Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.BottomEnd) {
        val modifier = Modifier
            .padding(end = 10.dp)
            .background(color = Colors.surfaceColor, shape = CircleShape)
            .size(width = 60.dp, height = 60.dp)

        Box(modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(imageModifier = Modifier.size(35.dp).clickable {
            }, imageRes = "drawable/recycle_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
        }
    }
}

@Composable
fun AttachBackIcon() {
    val navigator = LocalNavigator.currentOrThrow
    PageBackNavWidget(){
        navigator.popUntilRoot()
    }
}





@Composable
fun PageTitle(){
    val rowModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp, top = 30.dp)
        .fillMaxWidth()
        .wrapContentHeight()
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TitleWidget(title = "Complete Your Profile", textColor = Colors.primaryColor)
        }
}
