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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Models.PlatformNavigator
import domain.Models.VendorPackage
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import presentation.viewmodels.MainViewModel
import presentation.widgets.AttachAppointmentStatus
import presentation.widgets.PageBackNavWidget
import presentation.widgets.TitleWidget
import presentation.widgets.VendorInfoTopBarItem
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

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setSelectedPackage(vendorPackage: VendorPackage){
        this.vendorPackage = vendorPackage
    }





    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce)


        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            content = {
                Column(modifier = Modifier.fillMaxSize().background(color = Color(0x30CCCCCC)).verticalScroll(rememberScrollState())) {
                    Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
                        ScrollingPackageImages(vendorPackage!!)
                        leftTopBarItem {
                            mainViewModel!!.setOnBackPressed(true)
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth().height(100.dp).background(color = Color.White).padding(10.dp)) {
                        TextComponent(
                            text = "Saalbach Hinterglemm",
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
                                        text = "2 Services",
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
                                        text = "3 Products",
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
                                        text = "2 Therapists",
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

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 15.dp, end = 15.dp).height(400.dp)
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
                                text = "Reviews",
                                fontSize = 20,
                                fontFamily = GGSansRegular,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp))


                        }
                    }
                }
            }
        )
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






