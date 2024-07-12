package presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import theme.styles.Colors
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Enums.MainTabEnum
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import presentation.Products.ShopProductTab
import presentation.appointments.AppointmentsTab
import presentation.account.AccountTab
import presentation.home.HomeTab
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ProductResourceListEnvelopeViewModel
import presentation.viewmodels.ProductViewModel
import presentations.components.ImageComponent

@Parcelize
class MainTab(private val platformNavigator: PlatformNavigator): Tab, KoinComponent, Parcelable {



    @Transient
    private var mainViewModel: MainViewModel? = null
    private var homeTab: HomeTab? = null
    private var shopProductTab: ShopProductTab? = null
    private var appointmentsTab: AppointmentsTab? = null
    private var accountTab: AccountTab? = null
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Main"
            val icon = painterResource("drawable/home_icon.png")

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

    @Composable
    override fun Content() {

         var homePageViewModel: HomePageViewModel? = null

        if (homePageViewModel == null) {
            homePageViewModel = kmpViewModel(
                factory = viewModelFactory {
                    HomePageViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        var isBottomNavSelected by remember { mutableStateOf(true) }
            val userInfo = mainViewModel!!.currentUserInfo.collectAsState()
            val vendorInfo = mainViewModel!!.connectedVendor.collectAsState()

            TabNavigator(showDefaultTab(mainViewModel!!, homePageViewModel!!)) {
                it2 ->
                Scaffold(
                    topBar = {
                        if (userInfo.value.userId != null && vendorInfo.value.vendorId != null)
                        MainTopBar(mainViewModel!!)

                    },
                    content = {
                        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                            CurrentTab()

                        }
                    },
                    backgroundColor = Color.White,
                    bottomBar = {
                        Box(modifier = Modifier.fillMaxWidth().height(80.dp),
                            contentAlignment = Alignment.Center) {
                            BottomNavigation(
                                modifier = Modifier.height(60.dp).padding(start = 10.dp, end = 10.dp)
                                    .background(shape = RoundedCornerShape(15.dp), color = Colors.darkPrimary),
                                backgroundColor = Color.Transparent,
                                elevation = 0.dp
                            )
                            {
                                homeTab = HomeTab()
                                homeTab!!.setMainViewModel(mainViewModel!!)
                                homeTab!!.setHomePageViewModel(homePageViewModel)
                                TabNavigationItem(
                                    homeTab!!,
                                    selectedImage = "drawable/home_icon.png",
                                    unselectedImage = "drawable/home_outline.png",
                                    labelText = "Home",
                                    imageSize = 22,
                                    currentTabId = 0,
                                    tabNavigator = it2,
                                    mainViewModel = mainViewModel!!
                                ) {
                                    isBottomNavSelected = true
                                }
                                shopProductTab = ShopProductTab()
                                shopProductTab!!.setMainViewModel(mainViewModel!!)
                                TabNavigationItem(
                                    shopProductTab!!,
                                    selectedImage = "drawable/shopping_basket.png",
                                    unselectedImage = "drawable/shopping_basket_outline.png",
                                    labelText = "Shop",
                                    imageSize = 22,
                                    currentTabId = 1,
                                    tabNavigator = it2,
                                    mainViewModel = mainViewModel!!
                                ) {
                                    isBottomNavSelected = true
                                }
                                appointmentsTab = AppointmentsTab(platformNavigator)
                                appointmentsTab!!.setMainViewModel(mainViewModel!!)
                                TabNavigationItem(
                                    appointmentsTab!!,
                                    selectedImage = "drawable/appointment_icon.png",
                                    unselectedImage = "drawable/appointment_outline.png",
                                    labelText = "History",
                                    imageSize = 25,
                                    currentTabId = 2,
                                    tabNavigator = it2,
                                    mainViewModel = mainViewModel!!
                                ) {
                                    isBottomNavSelected = true
                                }
                                accountTab = AccountTab()
                                accountTab!!.setMainViewModel(mainViewModel!!)
                                TabNavigationItem(
                                    accountTab!!,
                                    selectedImage = "drawable/user_icon_filled.png",
                                    unselectedImage = "drawable/user_icon_outline.png",
                                    labelText = "More",
                                    imageSize = 25,
                                    currentTabId = 3,
                                    tabNavigator = it2,
                                    mainViewModel = mainViewModel!!
                                ) {
                                    isBottomNavSelected = true
                                }
                            }
                        }
                    }
                )
            }
        }



    private fun showDefaultTab(mainViewModel: MainViewModel, homePageViewModel: HomePageViewModel): HomeTab {
        homeTab = HomeTab()
        homeTab!!.setMainViewModel(mainViewModel)
        homeTab!!.setHomePageViewModel(homePageViewModel)

        return  homeTab!!
    }
    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab, selectedImage: String, unselectedImage: String, imageSize: Int = 30, labelText: String ,currentTabId: Int = 0, tabNavigator: TabNavigator, mainViewModel: MainViewModel, onBottomNavSelected:() -> Unit) {
        var imageStr by remember { mutableStateOf(unselectedImage) }
        var imageTint by remember { mutableStateOf(Color.White) }
        var handleTint by remember { mutableStateOf(Colors.darkPrimary) }

        if (tabNavigator.current is HomeTab && currentTabId == 0) {
            imageStr = selectedImage
            imageTint = Color.White
            handleTint = Color.White
            val screenTitle = "Home"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
            mainViewModel.setDisplayedTab(MainTabEnum.HOME.toPath())
        }
        else  if (tabNavigator.current is ShopProductTab && currentTabId == 1) {
            imageStr = selectedImage
            imageTint = Color.White
            handleTint = Color.White
            val screenTitle = "Products"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
            mainViewModel.setDisplayedTab(MainTabEnum.SHOP.toPath())
        } else if (tabNavigator.current is AppointmentsTab && currentTabId == 2) {
            imageStr = selectedImage
            imageTint = Color.White
            handleTint = Color.White
            val screenTitle = "Appointments"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
            mainViewModel.setDisplayedTab(MainTabEnum.APPOINTMENT.toPath())

        } else if (tabNavigator.current is AccountTab && currentTabId == 3) {
            imageStr = selectedImage
            imageTint = Color.White
            handleTint = Color.White
            val screenTitle = "Manage"
            onBottomNavSelected()
            mainViewModel.setTitle(screenTitle)
            mainViewModel.setDisplayedTab(MainTabEnum.PROFILE.toPath())
        } else {
            imageTint = Color.White
            handleTint = Colors.darkPrimary
            imageStr = unselectedImage
        }



        BottomNavigationItem(
            selected = tabNavigator.current == tab,
            modifier = Modifier.fillMaxHeight(),
            onClick = {
                tabNavigator.current = tab
            },
            selectedContentColor = Colors.primaryColor,
            unselectedContentColor = Colors.darkPrimary,

            icon = {
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Box(modifier = Modifier.fillMaxWidth(0.50f).fillMaxHeight(0.05f)
                        .background(color = handleTint, shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)), contentAlignment = Alignment.Center){}

                    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {
                        ImageComponent(
                            imageModifier = Modifier.size(imageSize.dp),
                            imageRes = imageStr,
                            colorFilter = ColorFilter.tint(imageTint)
                        )
                    }
                }
            }

        )
    }
}