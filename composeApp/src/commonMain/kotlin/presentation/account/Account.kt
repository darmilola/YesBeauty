package presentation.account


import GGSansSemiBold
import StackedSnackbarHost
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.Screens
import domain.Models.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import presentation.components.ButtonComponent
import presentation.viewmodels.MainViewModel
import presentation.widgets.ActionItemComponent
import presentation.widgets.AccountProfileImage
import presentation.widgets.TitleWidget
import presentations.components.TextComponent
import rememberStackedSnackbarHostState

@Parcelize
class AccountTab : Tab, Parcelable {

    private var mainViewModel: MainViewModel? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null
    private var userInfo: User? = null

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Profile"
            val icon = painterResource("profile_icon.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }


    @Composable
    override fun Content() {

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        Scaffold(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(color = Color.White),
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                val screenTitle =  mainViewModel!!.screenTitle.collectAsState()
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    TitleWidget(textColor = Colors.primaryColor, title = screenTitle.value)
                }
            },
            content = {

                val columnModifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = columnModifier
                ) {

                    userInfo = mainViewModel!!.currentUserInfo.value
                    AccountProfileImage(
                        profileImageUrl = userInfo!!.profileImageUrl!!,
                        showEditIcon = false,
                        isAsync = true
                    ) {}
                    UserAccountName(userInfo!!.firstname!!, userInfo!!.lastname!!)
                    EditProfileButton(
                        TextStyle(
                            fontFamily = GGSansSemiBold,
                            fontWeight = FontWeight.Black,
                            fontSize = TextUnit(18f, TextUnitType.Sp)
                        )
                    )
                    Divider(
                        color = Color(color = 0x90C8C8C8),
                        thickness = 2.dp,
                        modifier = Modifier.fillMaxWidth(0.90f).padding(top = 30.dp)
                    )
                    AttachAccountAction()
                }

            })
        }


    @Composable
    fun UserAccountName(firstname: String, lastname: String){
        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "$firstname $lastname",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }



    @Composable
    fun EditProfileButton(style: TextStyle){
        val buttonStyle = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth(0.40f)
            .background(color = Color.Transparent)
            .height(45.dp)

        ButtonComponent(modifier = buttonStyle, buttonText = "Edit Profile", borderStroke = BorderStroke(1.dp, color = Color.DarkGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 16, shape = CircleShape, textColor =  Color.DarkGray, style = style){
           if (mainViewModel!!.currentUserInfo.value.userId != null){
               mainViewModel!!.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.EDIT_PROFILE.toPath()))
           }
        }

    }



    @Composable
    fun AttachAccountAction() {
        val columnModifier = Modifier
            .padding(top = 10.dp, start = 5.dp, bottom = 100.dp)
            .fillMaxWidth()

        val actionStyle = Modifier
            .fillMaxWidth()
            .height(60.dp)

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = columnModifier
            ) {

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Orders",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/shopping_basket.png",
                    isDestructiveAction = false, onClick = {
                        mainViewModel!!.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.ORDERS.toPath()))
                    })

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Favorite Products",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/fav_icon_filled.png",
                    isDestructiveAction = false, onClick = {
                        mainViewModel!!.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.FAVORITE_PRODUCTS.toPath()))
                    })

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Payment Methods",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/cards_icon.png",
                    isDestructiveAction = false, onClick = {
                        mainViewModel!!.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.PAYMENT_METHODS.toPath()))
                    })

                if (mainViewModel!!.currentUserInfo.value.isTherapist == true) {
                    ActionItemComponent(
                        modifier = actionStyle,
                        buttonText = "Therapist Dashboard",
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        fontSize = 20,
                        textColor = Colors.darkPrimary,
                        style = TextStyle(),
                        iconRes = "drawable/dashboard_icon.png",
                        isDestructiveAction = false, onClick = {
                            mainViewModel!!.setScreenNav(
                                Pair(
                                    Screens.MAIN_SCREEN.toPath(),
                                    Screens.THERAPIST_DASHBOARD.toPath()
                                )
                            )
                        })
                }

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Switch Parlor",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/switch.png",
                    isDestructiveAction = false, onClick = {
                        mainViewModel!!.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.CONNECT_VENDOR.toPath()))
                    })

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Customer Care",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/support.png",
                    isDestructiveAction = false)

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Therapist SignIn",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/join.png",
                    isDestructiveAction = false, onClick = {
                        mainViewModel!!.setScreenNav(Pair(Screens.MAIN_SCREEN.toPath(), Screens.JOIN_SPA.toPath()))
                    })

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Privacy Policy",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.darkPrimary,
                    style = TextStyle(),
                    iconRes = "drawable/privacy_icon.png",
                    isDestructiveAction = false)

                Divider(color = Color(color = 0x70FA2D65), thickness = 0.5.dp, modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 20.dp))

                ActionItemComponent(
                    modifier = actionStyle,
                    buttonText = "Log Out",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 20,
                    textColor = Colors.pinkColor,
                    style = TextStyle(),
                    iconRes = "drawable/logout_icon.png",
                    isDestructiveAction = true
                )

                ButtonComponent(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                    buttonText = "Delete My Account",
                    borderStroke = BorderStroke(1.dp, theme.Colors.pinkColor),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 16,
                    shape = RoundedCornerShape(10.dp),
                    textColor = theme.Colors.pinkColor,
                    style = TextStyle()) {}

            }
        }
    }

