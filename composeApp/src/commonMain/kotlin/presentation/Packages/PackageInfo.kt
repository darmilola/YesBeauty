package presentation.Packages

import GGSansRegular
import GGSansSemiBold
import StackedSnackbarHost
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.Screens
import domain.Models.OrderItem
import domain.Models.PackageProducts
import domain.Models.PlatformNavigator
import domain.Models.VendorPackage
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import presentation.components.ButtonComponent
import presentation.packageBookings.PackageBookingScreen
import presentation.viewmodels.MainViewModel
import presentation.widgets.PackageReviewsWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.ProductDetailBottomSheet
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors
import utils.ParcelableScreen


@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class PackageInfo(val platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent, ScreenTransition {

    @Transient
    private var mainViewModel: MainViewModel? = null
    private var vendorPackage: VendorPackage? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setSelectedPackage(vendorPackage: VendorPackage){
        this.vendorPackage = vendorPackage
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }






    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val showProductDetails = remember { mutableStateOf(false) }
        val currencyUnit = mainViewModel!!.displayCurrencyUnit.value
        val selectedProductDetails = remember { mutableStateOf(PackageProducts()) }
        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }
        val services = vendorPackage!!.packageServices
        val products = vendorPackage!!.packageProducts
        val price = vendorPackage!!.price
        val isMobileServiceAvailable = vendorPackage!!.isMobileServiceAvailable
        val mobileServicePrice = vendorPackage!!.mobileServicePrice
        val mobileServiceText = if (!isMobileServiceAvailable) "Not Available" else "$currencyUnit$mobileServicePrice"
        val packageServices = vendorPackage!!.packageServices.size
        val productsSize = vendorPackage!!.packageProducts.size
        val therapistSize = vendorPackage!!.packageTherapists.size
        val serviceCountText = if (packageServices > 0) "$packageServices Services" else "No Services"
        val productCountText = if (productsSize > 0) "$productsSize Products" else "No Products"
        val therapistCountText = if (therapistSize > 0) "$therapistSize Therapist" else "No Therapist"

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce)



        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            bottomBar = {
                BottomNavigation(modifier = Modifier
                    .height(80.dp), backgroundColor = Color.Transparent,
                    elevation = 0.dp
                )
                {
                    val nav = LocalNavigator.currentOrThrow
                    val buttonStyle2 = Modifier
                        .padding(bottom = 10.dp, start = 10.dp, end = 10.dp, top = 4.dp)
                        .fillMaxWidth()
                        .height(50.dp)

                    val bgStyle = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .fillMaxHeight()

                        Row(
                            modifier = bgStyle,
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(0.60f).fillMaxHeight(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.50f).padding(start = 30.dp), verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.Start) {
                                    TextComponent(
                                        text = "$currencyUnit$price Total",
                                        fontSize = 18,
                                        fontFamily = GGSansSemiBold,
                                        textStyle = MaterialTheme.typography.h6,
                                        textColor = Colors.primaryColor,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Top) {
                                    Box(modifier = Modifier.wrapContentWidth().padding(start = 30.dp).fillMaxHeight(), contentAlignment = Alignment.CenterStart){
                                        TextComponent(
                                            text = "Mobile Services",
                                            fontSize = 15,
                                            fontFamily = GGSansRegular,
                                            textStyle = MaterialTheme.typography.h6,
                                            textColor = Color.LightGray,
                                            textAlign = TextAlign.Start,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                        Box(
                                            modifier = Modifier.padding(start = 30.dp).wrapContentWidth()
                                                .fillMaxHeight(),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            TextComponent(
                                                text = mobileServiceText,
                                                fontSize = 16,
                                                fontFamily = GGSansRegular,
                                                textStyle = MaterialTheme.typography.h6,
                                                textColor = Color.Black,
                                                textAlign = TextAlign.Start,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }


                            ButtonComponent(
                                modifier = buttonStyle2,
                                buttonText = "Book Now",
                                colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                                fontSize = 16,
                                shape = RoundedCornerShape(15.dp),
                                textColor = Color(color = 0xFFFFFFFF),
                                style = TextStyle(),
                                borderStroke = null,
                                onClick = {
                                    val packageBooking = PackageBookingScreen(platformNavigator)
                                    packageBooking.setMainViewModel(mainViewModel!!)
                                    packageBooking.setVendorPackage(vendorPackage!!)
                                    packageBooking.setDatabaseBuilder(databaseBuilder = databaseBuilder)
                                    nav.push(packageBooking)
                                    mainViewModel!!.setScreenNav(
                                        Pair(
                                            Screens.PACKAGE_BOOKING.toPath(),
                                            Screens.DEFAULT.toPath()
                                        )
                                    )
                                }
                            )
                        }

                }
            },
            content = {

                if (showProductDetails.value) {
                    ProductDetailBottomSheet(
                        mainViewModel!!,
                        isViewOnly = true,
                        OrderItem(itemProduct = selectedProductDetails.value.productInfo),
                        onDismiss = {
                            showProductDetails.value = false
                            selectedProductDetails.value = PackageProducts()
                        },
                        onAddToCart = { isAddToCart, item -> })
                }

                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().background(color = Color(0x30CCCCCC)).verticalScroll(rememberScrollState())) {
                    Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
                        ScrollingPackageImages(vendorPackage!!)
                        leftTopBarItem {
                            mainViewModel!!.setOnBackPressed(true)
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth().height(100.dp).background(color = Color.White).padding(10.dp)) {
                        TextComponent(
                            text = vendorPackage!!.title,
                            fontSize = 23,
                            fontFamily = GGSansSemiBold,
                            textStyle = TextStyle(),
                            textColor = Colors.darkPrimary,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Black,
                            lineHeight = 30,
                            textModifier = Modifier.fillMaxWidth().padding(start = 10.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().height(60.dp)
                        ) {
                            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                                ImageComponent(imageModifier = Modifier.size(24.dp).padding(bottom = 2.dp), imageRes = "drawable/services_icon.png", colorFilter = ColorFilter.tint(color = Color.Black))
                                val rowModifier = Modifier
                                    .padding(start = 5.dp)
                                    .wrapContentWidth()
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top,
                                    modifier = rowModifier
                                ) {
                                    TextComponent(
                                        text = serviceCountText,
                                        fontSize = 16,
                                        fontFamily = GGSansRegular,
                                        textStyle = MaterialTheme.typography.h6,
                                        textColor = Color.Black,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                                ImageComponent(imageModifier = Modifier.size(24.dp).padding(bottom = 2.dp), imageRes = "drawable/product_icon.png", colorFilter = ColorFilter.tint(color = Color.Black))
                                val rowModifier = Modifier
                                    .padding(start = 5.dp)
                                    .wrapContentWidth()
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top,
                                    modifier = rowModifier
                                ) {
                                    TextComponent(
                                        text = productCountText,
                                        fontSize = 16,
                                        fontFamily = GGSansRegular,
                                        textStyle = MaterialTheme.typography.h6,
                                        textColor = Color.Black,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                                ImageComponent(imageModifier = Modifier.size(24.dp).padding(bottom = 2.dp), imageRes = "drawable/therapist_icon.png", colorFilter = ColorFilter.tint(color = Color.Black))
                                val rowModifier = Modifier
                                    .padding(start = 5.dp)
                                    .wrapContentWidth()
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Top,
                                    modifier = rowModifier
                                ) {
                                    TextComponent(
                                        text = therapistCountText,
                                        fontSize = 16,
                                        fontFamily = GGSansRegular,
                                        textStyle = MaterialTheme.typography.h6,
                                        textColor = Color.Black,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth().height(750.dp), contentAlignment = Alignment.Center) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 15.dp, end = 15.dp).fillMaxHeight()
                                .background(color = Color.White, shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                        ) {
                            TextComponent(
                                text = "Description",
                                fontSize = 20,
                                fontFamily = GGSansRegular,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment  = Alignment.Start,
                            ) {
                                val modifier = Modifier
                                    .padding(end = 20.dp, start = 20.dp, top = 10.dp)
                                    .fillMaxWidth()
                                TextComponent(
                                    text = vendorPackage!!.description,
                                    fontSize = 17,
                                    fontFamily = GGSansRegular,
                                    textStyle = MaterialTheme.typography.h6,
                                    textColor = Color.DarkGray,
                                    textAlign = TextAlign.Left,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 25,
                                    textModifier = modifier,
                                    maxLines = 10,
                                    overflow = TextOverflow.Ellipsis)

                            }


                            TextComponent(
                                text = "Services",
                                fontSize = 20,
                                fontFamily = GGSansRegular,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp))

                            for (item in services){
                                packageServicesItem(item.serviceInfo.title)
                            }

                            TextComponent(
                                text = "Products",
                                fontSize = 20,
                                fontFamily = GGSansRegular,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp))

                            for (item in products){
                                packageProductItem(item, onViewDetails = {
                                     selectedProductDetails.value = it
                                     showProductDetails.value = true
                                })
                            }


                            if (vendorPackage!!.packageReviews.isNotEmpty()) {
                                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                                    TextComponent(
                                        text = "Package Reviews",
                                        fontSize = 20,
                                        fontFamily = GGSansRegular,
                                        textStyle = TextStyle(),
                                        textColor = Colors.darkPrimary,
                                        textAlign = TextAlign.Left,
                                        fontWeight = FontWeight.Black,
                                        lineHeight = 30,
                                        textModifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp))

                                    PackageReviewsWidget(vendorPackage!!.packageReviews)
                                }
                            }

                        }
                    }
                }
            }
        )
    }

    @Composable
    fun packageServicesItem(serviceName: String){
        Row(modifier = Modifier.fillMaxWidth().height(40.dp).padding(start = 20.dp, top = 10.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxHeight().width(10.dp), contentAlignment = Alignment.Center){
                    Box(modifier = Modifier.size(10.dp).background(color = Color.Black, shape = CircleShape))
                }
                Box(modifier = Modifier.fillMaxWidth(0.80f).padding(start = 10.dp).fillMaxHeight(), contentAlignment = Alignment.CenterStart){
                    TextComponent(
                        text = serviceName,
                        fontSize = 16,
                        fontFamily = GGSansRegular,
                        textStyle = MaterialTheme.typography.h6,
                        textColor = Color.Black,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center){

                }
            }
        }
    }

    @Composable
    fun packageProductItem(packageProducts: PackageProducts, onViewDetails:(PackageProducts) -> Unit){
        Row(modifier = Modifier.fillMaxWidth().height(40.dp).padding(start = 20.dp, top = 10.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxHeight().width(10.dp), contentAlignment = Alignment.Center){
                    Box(modifier = Modifier.size(10.dp).background(color = Color.Black, shape = CircleShape))
                }
                Box(modifier = Modifier.fillMaxWidth(0.70f).padding(start = 10.dp).fillMaxHeight(), contentAlignment = Alignment.CenterStart){
                    TextComponent(
                        text = packageProducts.productInfo.productName,
                        fontSize = 16,
                        fontFamily = GGSansRegular,
                        textStyle = MaterialTheme.typography.h6,
                        textColor = Color.Black,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().clickable { onViewDetails(packageProducts) }, contentAlignment = Alignment.Center){
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth().height(40.dp)
                    ) {
                        TextComponent(
                            text = "View Details",
                            fontSize = 14,
                            fontFamily = GGSansRegular,
                            textStyle = MaterialTheme.typography.h6,
                            textColor = Colors.primaryColor,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold
                        )
                        ImageComponent(imageModifier = Modifier.size(20.dp), imageRes = "drawable/chevron_right.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
                    }

                }
            }
        }
    }

    @Composable
    fun leftTopBarItem(onBackPressed: () -> Unit) {
        PageBackNavWidget {
            onBackPressed()
        }
    }





    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ScrollingPackageImages(vendorPackage: VendorPackage){

        val packageImages = vendorPackage.packageImages
        val pagerState = rememberPagerState(pageCount = {
            packageImages.size
        })

        val  boxModifier =
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                ImageComponent(imageModifier = Modifier.fillMaxSize(), imageRes = packageImages[page].imageUrl, contentScale = ContentScale.Crop, isAsync = true)
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 4.dp, start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    var color = Color.White
                    var width = 0
                    if (pagerState.currentPage == iteration){
                        color =  Colors.primaryColor
                        width = 20
                    } else{
                        color =  Color.LightGray
                        width = 20
                    }
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .height(2.dp)
                            .width(width.dp)
                    )
                }

            }
        }

    }





    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }


}






