package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import presentation.dataModeller.WorkingHoursDataSource
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.AvailableTimeUIModel
import domain.Models.ServiceTime
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun TimeGrid(availableTimes: List<ServiceTime>? = arrayListOf(), selectedTime: ServiceTime? = null, onWorkHourClickListener: (ServiceTime) -> Unit) {
    var workHourUIModel by remember {
        mutableStateOf(
            AvailableTimeUIModel(
                selectedTime = ServiceTime(),
                availableTimes!!
            )
        )
    }
    workHourUIModel = if (selectedTime != null) {
        AvailableTimeUIModel(selectedTime, visibleTime = availableTimes!!)
    } else {
        AvailableTimeUIModel(ServiceTime(), availableTimes!!)
    }


    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier.fillMaxWidth().height(20.dp)) {

            TextComponent(
                text = "Morning",
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                textModifier = Modifier.weight(1f))

            TextComponent(
                text = "Afternoon",
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                textModifier = Modifier.weight(1f))

            TextComponent(
                text = "Evening",
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                textModifier = Modifier.weight(1f))

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth().height(250.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(workHourUIModel.visibleTime.size) { i ->
                TimeItem(workHourUIModel.visibleTime[i], onWorkHourClickListener = { it ->
                    onWorkHourClickListener(it)
                    workHourUIModel = workHourUIModel.copy(
                        selectedTime = it,
                        visibleTime = workHourUIModel.visibleTime.map { it2 ->
                            it2.copy(
                                isSelected = it2.id == it.id
                            )
                        }

                    )
                })
            }
        }
    }
}


@Composable
fun TimeItem(availableTime: ServiceTime, onWorkHourClickListener: (ServiceTime) -> Unit) {
    val color: Color = if(availableTime.isSelected){
        Colors.primaryColor
    } else if (!availableTime.isAvailable){
        Color.LightGray
    } else {
        Color.Gray
    }
    val showSelectIcon = remember { mutableStateOf(false) }

    showSelectIcon.value = availableTime.isSelected

    Row(
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp, top = 10.dp)
            .fillMaxWidth()
            .clickable {
                if (availableTime.isAvailable) {
                    onWorkHourClickListener(availableTime)
                }
            }
            .border(border = BorderStroke((1.5).dp, color), shape = RoundedCornerShape(6.dp))
            .height(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(showSelectIcon.value){
            ImageComponent(imageModifier = Modifier.size(20.dp), imageRes = "drawable/check_mark_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
        }
        TextComponent(
            text = availableTime.time!!+" PM",
            fontSize = 15,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = color,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            lineHeight = 30,
            textModifier = Modifier.padding(start = 5.dp))

    }
}