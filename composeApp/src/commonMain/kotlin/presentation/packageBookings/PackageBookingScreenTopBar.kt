package presentation.packageBookings

import GGSansRegular
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import presentation.widgets.PackageStepsProgressBar
import presentation.widgets.PageBackNavWidget
import presentation.widgets.StepsProgressBar
import presentations.components.TextComponent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PackageBookingScreenTopBar(pagerState: PagerState, onBackPressed: (Int) -> Unit) {

    val rowModifier = Modifier
        .fillMaxWidth()
        .height(60.dp)

    val stepList = arrayListOf<String>()
    stepList.add("Bookings")
    stepList.add("Overview")

    val colModifier = Modifier
        .fillMaxWidth()
        .height(140.dp)

    Column(modifier = colModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 10.dp),
                contentAlignment = Alignment.CenterStart) {
                if (pagerState.targetPage != 2) {
                    leftTopBarItem(pagerState, onBackPressed = {
                        onBackPressed(it)
                    })
                }
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                BookingTitle()
            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth(0.20f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
            }

        }
        Row(modifier = Modifier.fillMaxWidth().height(70.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            PackageStepsProgressBar(modifier = Modifier.wrapContentWidth().padding(start = 20.dp), numberOfSteps = 1, currentStep = pagerState.currentPage, stepItems = stepList)
        }

    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun leftTopBarItem(pagerState: PagerState, onBackPressed:(Int) -> Unit) {
    val currentPage = pagerState.currentPage
    PageBackNavWidget(){
          onBackPressed(currentPage)
       }
    }


@Composable
fun BookingTitle(){
    TextComponent(
        text = "Book Package",
        fontSize = 16,
        fontFamily = GGSansRegular,
        textStyle = MaterialTheme.typography.h6,
        textColor = Color.DarkGray,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
    )
}

