package screens.main

import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansSemiBold
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import components.TextComponent
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import screens.Bookings.BookingScreen
import screens.Bookings.ServiceInformationPage
import screens.Products.CartScreen
import screens.UserProfile.ConnectBusiness.ConnectPage
import screens.UserProfile.UserOrders.UserOrders
import screens.authentication.AuthenticationComposeScreen
import screens.consultation.ConsultationScreen
import screens.consultation.VirtualConsultationRoom

object MainScreen : Screen {

    @Composable
    override fun Content() {

        val mainViewModel: MainViewModel = MainViewModel()
        var screenTitle: State<String> =  mainViewModel.screenTitle.observeAsState()
        val screenId: State<Int> =  mainViewModel.screenId.observeAsState()
        println(screenId.value)

        if (screenId.value == 1){
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(BookingScreen(mainViewModel))
        }
        if (screenId.value == 2){
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(ConsultationScreen(mainViewModel))
        }

        if (screenId.value == 3){
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(CartScreen(mainViewModel))
        }
        if (screenId.value == 4){
            val navigator = LocalNavigator.currentOrThrow
            navigator.replace(AuthenticationComposeScreen(currentScreen = 4))
        }
        if (screenId.value == 5){
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(UserOrders(mainViewModel))
        }
        if (screenId.value == 6){
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(ConnectPage)
        }
        if (screenId.value == 7){
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(ServiceInformationPage)
        }
        if (screenId.value == 8){
            val navigator = LocalNavigator.currentOrThrow
            navigator.push(VirtualConsultationRoom)
        }

        TabNavigator(showDefaultTab(mainViewModel)) {
            Scaffold(
                topBar = {
                     MainTopBar(mainViewModel)
                },
                content = {
                     CurrentTab()
                },
                backgroundColor = Color(0xFFF3F3F3),
                bottomBar = {
                    BottomNavigation(modifier = Modifier
                        .padding(bottom = 10.dp)
                        .height(80.dp), backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    )
                    {
                        TabNavigationItem(HomeTab(mainViewModel))
                        TabNavigationItem(ShopTab(mainViewModel))
                        TabNavigationItem(ConsultTab(mainViewModel))
                        TabNavigationItem(BookingsTab(mainViewModel))
                        TabNavigationItem(AccountTab(mainViewModel))
                    }
                }
            )
            if(screenId.value == 0){
                val tabNavigator = LocalTabNavigator.current
                tabNavigator.current =  HomeTab(mainViewModel)
            }
        }
    }
}

private fun showDefaultTab(mainViewModel: MainViewModel): HomeTab {

       return  HomeTab(mainViewModel)
}




@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = {
                    tabNavigator.current = tab
                  },
        selectedContentColor = Color(color = 0xFFFA2D65),
        unselectedContentColor = Color.Gray,
        label = {
            MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
                TextComponent(
                    text = tab.options.title,
                    fontSize = 14,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(fontFamily = GGSansSemiBold),
                    textColor = LocalContentColor.current,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    textModifier = Modifier.padding(top = 15.dp).wrapContentWidth()
                )
            }

        },
        icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = tab.options.title, modifier = Modifier.size(28.dp).padding(bottom = 5.dp)) } }
    )
}

object MainScreenLanding : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        Row(modifier = Modifier.fillMaxSize()) {}
    }
}





