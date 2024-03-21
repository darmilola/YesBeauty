package presentation.widgets

import GGSansSemiBold
import presentation.dataModeller.CalendarDataSource
import domain.Models.CalendarUiModel
import theme.styles.Colors
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import domain.Models.Date
import kotlinx.datetime.format.MonthNames
import presentation.viewmodels.BookingViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun BookingCalendar(modifier: Modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 10.dp),bookingViewModel: BookingViewModel? = null, onDateSelected: (LocalDate) -> Unit) {
    val dataSource = CalendarDataSource()
    val calendarUiModel = dataSource.getDate(lastSelectedDate = dataSource.today)
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // date selected on appointment booking process
    val unsavedAppointmentDate = bookingViewModel?.currentAppointmentBooking?.value?.appointmentDate

    Column(modifier = modifier) {
        // get CalendarUiModel from CalendarDataSource, and the lastSelectedDate is Today.
        var selectedUIModel by remember { mutableStateOf(calendarUiModel) }
        var initialVisibleDates by remember { mutableStateOf(5) }

        if (unsavedAppointmentDate != null) {
               selectedUIModel = selectedUIModel.copy(selectedDate = Date(date = unsavedAppointmentDate, isSelected = true, isToday = false),visibleDates = selectedUIModel.visibleDates.map { it2 ->
                   it2.copy(
                       isSelected = it2.date == unsavedAppointmentDate
                   )
               })
              onDateSelected(selectedUIModel.selectedDate.date)
            }
        else{
            // onDateSelected(calendarUiModel.selectedDate.date)
         }

        CalenderHeader(selectedUIModel, onPrevClickListener = { startDate ->
            coroutineScope.launch {
                if (initialVisibleDates > 0) initialVisibleDates--
                listState.animateScrollToItem(index = initialVisibleDates)
            }
           },
            onNextClickListener = { endDate ->
                coroutineScope.launch {
                    if (initialVisibleDates < listState.layoutInfo.totalItemsCount - 5) initialVisibleDates++
                    listState.animateScrollToItem(index = initialVisibleDates)
                }
            })
        CalenderContent(selectedUIModel, onDateClickListener = { it ->
            println(it.toString())
            selectedUIModel = selectedUIModel.copy(
                selectedDate = it,
                visibleDates = selectedUIModel.visibleDates.map { it2 ->
                    it2.copy(
                        isSelected = it2.date == it.date
                    )
                }
            )
            onDateSelected(selectedUIModel.selectedDate.date)
        }, listState = listState)
    }
}

@Composable
fun CalenderContent(calendarUiModel: CalendarUiModel, onDateClickListener: (Date) -> Unit, listState: LazyListState) {

    LazyRow(modifier = Modifier.padding( top = 10.dp).fillMaxWidth(), state = listState) {
        // pass the visibleDates to the UI
        items(items = calendarUiModel.visibleDates) { date ->
            ContentItem(date, onDateClickListener)
        }
    }
}



@Composable
fun ContentItem(date: Date, onClickListener: (Date) -> Unit) {
    val textColor: Color = if(date.isSelected){
        Color.White
    }
    else{
        Colors.darkPrimary
    }

    val bgColor: Color = if(date.isSelected){
        Colors.primaryColor
    }
    else{
        Colors.lighterPrimaryColor
    }


    Card(colors = CardDefaults.cardColors(
        containerColor = bgColor
    ),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier
                .width(70.dp)
                .height(80.dp)
                .clickable {
                    onClickListener(date)
                }
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextComponent(
                text = date.date.dayOfMonth.toString(),
                textModifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 23,
                fontFamily = GGSansSemiBold,
                fontWeight = FontWeight.Black,
                textColor = textColor,
                textAlign = TextAlign.Center,
                textStyle = MaterialTheme.typography.h6
            )
            TextComponent(
                text = date.date.dayOfWeek.toString().substring(0,3),
                textModifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp),
                fontSize = 13,
                fontFamily = GGSansSemiBold,
                fontWeight = FontWeight.Light,
                textColor = textColor,
                textAlign = TextAlign.Center,
                textStyle = MaterialTheme.typography.h4)
        }
    }
}



@Composable
fun CalenderHeader(calendarUiModel: CalendarUiModel, onPrevClickListener: (LocalDate) -> Unit,
                   onNextClickListener: (LocalDate) -> Unit,) {
    val imageModifier = Modifier
        .size(20.dp)

    Row {
        Row(modifier = Modifier
            .weight(1f)
            .clickable {
                onPrevClickListener(calendarUiModel.startDate.date)
            }) {
            ImageComponent(
                imageModifier = imageModifier.rotate(180f),
                imageRes = "drawable/left_arrow.png",
                colorFilter = ColorFilter.tint(color = Colors.darkPrimary)
            )
        }

        Row(modifier = Modifier.weight(2f),horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top) {
            TextComponent(
                text = if (calendarUiModel.selectedDate.isToday) {
                    "Today"
                } else {
                    val formattedMonth = calendarUiModel.selectedDate.date.month.toString()
                        .lowercase()
                        .replaceFirstChar {
                        char -> char.titlecase()
                    }
                    formattedMonth + ", " + calendarUiModel.selectedDate.date.dayOfMonth.toString()
                },
                textModifier = Modifier
                    .align(Alignment.CenterVertically),
                fontSize = 16,
                fontFamily = GGSansSemiBold,
                fontWeight = FontWeight.Bold,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                textStyle = MaterialTheme.typography.h6
            )
        }

        Row(modifier = Modifier
            .weight(1f)
            .clickable {
                onNextClickListener(calendarUiModel.endDate.date)
            },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top) {
            ImageComponent(
                imageModifier = imageModifier,
                imageRes = "drawable/left_arrow.png",
                colorFilter = ColorFilter.tint(color = Colors.darkPrimary)
            )
        }

    }
}