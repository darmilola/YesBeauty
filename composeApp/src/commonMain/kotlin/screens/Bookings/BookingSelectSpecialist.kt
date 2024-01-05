package screens.Bookings

import GGSansSemiBold
import Models.AvailableSlotsUIModel
import Models.AvailableTherapistUIModel
import Models.TherapistDataSource
import Models.WorkingHoursDataSource
import Styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.TextComponent
import widgets.AttachTherapistWidget
import widgets.ReviewsWidget
import widgets.TimeGrid

@Composable
fun BookingSelectSpecialist() {

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TherapistContent()
        AvailableTimeContent()
        AttachServiceReviews()
    }


}

@Composable
fun AvailableTimeContent() {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 10.dp, top = 15.dp, bottom = 10.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Available Time",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            textModifier = Modifier.fillMaxWidth()
        )

        TimeGrid()
    }
}

@Composable
fun TherapistContent() {
    val dataSource = TherapistDataSource()
    // get CalendarUiModel from CalendarDataSource, and the lastSelectedDate is Today.
    val selectedTherapist = AvailableTherapistUIModel.AvailableTherapist(0, true, true)
    val availableTherapist = dataSource.getAvailableTherapist(lastSelectedTherapist = selectedTherapist)

    var selectedTherapistUIModel by remember { mutableStateOf(availableTherapist) }
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 10.dp, top = 5.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Choose Specialist",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            textModifier = Modifier.fillMaxWidth())

        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(230.dp),
            contentPadding = PaddingValues(6.dp)
        ) {
            items(selectedTherapistUIModel.visibleTherapist.size) { i ->
                AttachTherapistWidget(selectedTherapistUIModel.visibleTherapist[i], onTherapistSelectedListener = {
                        it -> selectedTherapistUIModel = selectedTherapistUIModel.copy(
                    selectedTherapist = it,
                    visibleTherapist = selectedTherapistUIModel.visibleTherapist.map { it2 ->
                        it2.copy(
                            isSelected = it2.therapistId == it.therapistId
                        )
                    }
                )})
            }


        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttachServiceReviews(){

    TextComponent(
        text = "Jenny's Reviews",
        fontSize = 18,
        fontFamily = GGSansSemiBold,
        textStyle = TextStyle(),
        textColor = Colors.darkPrimary,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.Black,
        lineHeight = 30,
        textModifier = Modifier
            .padding(bottom = 5.dp, start = 15.dp, top = 20.dp)
            .fillMaxWidth()
    )

    val pagerState = rememberPagerState(pageCount = {
        5
    })

    val boxModifier =
        Modifier
            .padding(bottom = 20.dp, top = 20.dp, start = 15.dp)
            .fillMaxHeight()
            .fillMaxWidth()

    val boxBgModifier =
        Modifier
            .padding(bottom = 10.dp, top = 10.dp, start = 15.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .border(border = BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(topStart = 7.dp, bottomStart = 7.dp))


    Box(modifier = boxBgModifier) {

        Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                ReviewsWidget()
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    var color = Color.LightGray
                    var width = 0
                    if (pagerState.currentPage == iteration) {
                        color = Colors.primaryColor
                        width = 20
                    } else {
                        color = Color.LightGray
                        width = 20
                    }
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .height(3.dp)
                            .width(width.dp)
                    )
                }

            }
        }
    }

}


