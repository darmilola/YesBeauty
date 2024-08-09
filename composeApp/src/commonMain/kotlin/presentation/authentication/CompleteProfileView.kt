package presentation.authentication

import StackedSnackbarHost
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import countryList
import domain.Enums.AuthType
import domain.Enums.SharedPreferenceEnum
import domain.Models.PlatformNavigator
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.DomainViewHandler.PlatformHandler
import presentation.components.ButtonComponent
import presentation.components.ToggleButton
import presentation.Screens.ConnectVendorScreen
import presentation.dialogs.LoadingDialog
import presentation.profile.ProfilePresenter
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PlatformViewModel
import presentation.widgets.DropDownWidget
import presentation.widgets.AccountProfileImage
import presentation.widgets.SnackBarType
import presentation.widgets.TitleWidget
import presentation.widgets.ShowSnackBar
import presentations.components.TextComponent
import presentations.widgets.InputWidget
import rememberStackedSnackbarHostState
import utils.InputValidator

@Composable
fun CompleteProfile(authenticationPresenter: AuthenticationPresenter, authEmail: String, authPhone: String,
                    platformNavigator: PlatformNavigator, platformViewModel: PlatformViewModel, profilePresenter: ProfilePresenter, mainViewModel: MainViewModel) {

    val placeHolderImage = "drawable/user_icon.png"
    val firstname = remember { mutableStateOf("") }
    val lastname = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("male") }
    val city = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("") }
    val completeProfileInProgress = remember { mutableStateOf(false) }
    val navigateToConnectVendor = remember { mutableStateOf(false) }
    val profileImageUrl = remember { mutableStateOf(placeHolderImage) }
    val imagePickerScope = rememberCoroutineScope()
    val preferenceSettings = Settings()
    val authType = if (authEmail.isNotEmpty()) AuthType.EMAIL.toPath() else AuthType.PHONE.toPath()
    val inputList =  ArrayList<String>()
    val isSavedClicked = remember {
        mutableStateOf(false)
    }
    val navigator = LocalNavigator.currentOrThrow
    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    //View Contract Handler Initialisation
    val handler = PlatformHandler(profilePresenter, platformViewModel)
    handler.init()

    inputList.add(firstname.value)
    inputList.add(lastname.value)



    val rootModifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color.White)

    val buttonStyle = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 30.dp)
        .fillMaxWidth()
        .height(50.dp)

    val topLayoutModifier =
            Modifier
                .padding(top = 40.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
                .background(color = Color.White)



    val authHandler = AuthenticationScreenHandler(authenticationPresenter,
        onUserLocationReady = {},
        enterPlatform = { user, whatsappPhone -> },
        completeProfile = { userEmail, userPhone -> },
        connectVendor = { user -> },
        onVerificationStarted = {},
        onVerificationEnded = {}, onCompleteStarted = {
            completeProfileInProgress.value = true
        }, onCompleteEnded = { isSuccessful -> completeProfileInProgress.value = false },
        connectVendorOnProfileCompleted = {
                country,city, profileId, apiKey ->
                preferenceSettings[SharedPreferenceEnum.COUNTRY.toPath()] = country
                preferenceSettings[SharedPreferenceEnum.CITY.toPath()] = city
                preferenceSettings[SharedPreferenceEnum.PROFILE_ID.toPath()] = profileId
                preferenceSettings[SharedPreferenceEnum.API_KEY.toPath()] = apiKey
                navigateToConnectVendor.value = true

        }, onUpdateStarted = {}, onUpdateEnded = {})
    authHandler.init()

    if (completeProfileInProgress.value) {
        Box(modifier = Modifier.fillMaxWidth(0.90f)) {
            LoadingDialog("Saving Profile...")
        }
    }

    else if (navigateToConnectVendor.value){
        val connectVendor = ConnectVendorScreen(platformNavigator)
        connectVendor.setMainViewModel(mainViewModel)
        navigator.replaceAll(connectVendor)
    }

    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
    ) {

        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    PageTitle()
                }
                AccountProfileImage(
                    profileImageUrl = profileImageUrl.value,
                    isAsync = profileImageUrl.value != placeHolderImage,
                    onUploadImageClicked = {

                    })
                Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        InputWidget(
                            iconRes = "drawable/card_icon.png",
                            placeholderText = "Firstname",
                            iconSize = 28,
                            text = firstname.value!!,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            isPasswordField = false,
                            isSingleLine = true,
                            onSaveClicked = isSavedClicked.value,
                            maxLines = 1
                        ) {
                            firstname.value = it
                        }
                    }
                    Box(modifier = Modifier.weight(1f).padding(start = 10.dp), contentAlignment = Alignment.Center) {
                        InputWidget(
                            iconRes = "drawable/card_icon.png",
                            placeholderText = "Lastname",
                            iconSize = 28,
                            text = lastname.value!!,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            isPasswordField = false,
                            isSingleLine = true,
                            onSaveClicked = isSavedClicked.value,
                            maxLines = 1
                        ) {
                            lastname.value = it
                        }
                    }
                }
                AttachCountryDropDownWidget() {
                    profilePresenter.getPlatformCities(country = it)
                    country.value = it
                }

                AttachCityDropDownWidget(platformViewModel = platformViewModel) {
                    city.value = it
                }

                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    TextComponent(
                        text = "Gender",
                        fontSize = 15,
                        textStyle = TextStyle(),
                        textColor = theme.Colors.darkPrimary,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 30,
                        textModifier = Modifier.padding(end = 10.dp, start = 10.dp)
                    )

                    ToggleButton(
                        shape = RoundedCornerShape(10.dp),
                        onLeftClicked = {
                            gender.value = "Male"
                        },
                        onRightClicked = {
                            gender.value = "Female"
                        },
                        leftText = "Male",
                        rightText = "Female",
                    )
                }

                ButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Save",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                    fontSize = 18,
                    shape = RoundedCornerShape(15.dp),
                    textColor = Color(color = 0xFFFFFFFF),
                    style = TextStyle(),
                    borderStroke = null
                ) {
                    isSavedClicked.value = true
                    profileImageUrl.value = "https://cdn.pixabay.com/photo/2016/11/29/06/08/woman-1867715_1280.jpg"
                    if (!InputValidator(inputList).isValidInput()) {
                        ShowSnackBar(title = "Input Required", description = "Please provide the required info", actionLabel = "", duration = StackedSnackbarDuration.Short, snackBarType = SnackBarType.ERROR,
                                onActionClick = {}, stackedSnackBarHostState = stackedSnackBarHostState)
                    }
                    else if (country.value?.isEmpty() == true){
                        ShowSnackBar(title = "Error",
                            description = "Please Select your country of residence",
                            actionLabel = "",
                            duration = StackedSnackbarDuration.Long,
                            snackBarType = SnackBarType.ERROR,
                            stackedSnackBarHostState = stackedSnackBarHostState,
                            onActionClick = {})
                    }
                    else if (city.value?.isEmpty() == true){
                        ShowSnackBar(title = "Error",
                            description = "Please Select your City",
                            actionLabel = "",
                            duration = StackedSnackbarDuration.Long,
                            snackBarType = SnackBarType.ERROR,
                            stackedSnackBarHostState = stackedSnackBarHostState,
                            onActionClick = {})
                    }
                    else if (profileImageUrl.value == placeHolderImage) {
                        ShowSnackBar(title = "Profile Image Required", description = "Please Upload a required Profile Image", actionLabel = "", duration = StackedSnackbarDuration.Short, snackBarType = SnackBarType.ERROR,
                            stackedSnackBarHostState,onActionClick = {})
                    }
                    else {
                        authenticationPresenter.completeProfile(
                            firstname.value, lastname.value,
                            userEmail = authEmail, authPhone = authPhone, signupType = authType, country = country.value, city = city.value,
                            gender = gender.value, profileImageUrl = profileImageUrl.value
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun AttachCountryDropDownWidget(defaultValue: String = "", onMenuItemClick : (String) -> Unit) {
    val countryList = countryList()
    val placeholder = defaultValue.ifEmpty { "Country" }
    DropDownWidget(menuItems = countryList, placeHolderText = placeholder, onMenuItemClick = {
        onMenuItemClick(countryList[it])
    })
}

@Composable
fun AttachCityDropDownWidget(defaultValue: String = "", platformViewModel: PlatformViewModel, onMenuItemClick : (String) -> Unit) {
    val cityListState = platformViewModel.platformCities.collectAsState()
    val cityList = cityListState.value
    val placeholder = defaultValue.ifEmpty { "City" }
    DropDownWidget(menuItems = cityList, iconRes = "drawable/urban_icon.png", placeHolderText = placeholder, onMenuItemClick = {
        onMenuItemClick(cityList[it])
    })
}




@Composable
fun PageTitle(){
    val rowModifier = Modifier
        .padding(bottom = 10.dp, top = 10.dp)
        .wrapContentWidth()
        .wrapContentHeight()
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TitleWidget(title = "Complete Your Profile", textColor = Colors.primaryColor)
     }
}
