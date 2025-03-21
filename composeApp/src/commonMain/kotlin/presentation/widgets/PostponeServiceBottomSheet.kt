package presentation.widgets

import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import presentation.components.ButtonComponent
import presentation.viewmodels.MainViewModel
import presentations.components.TextComponent
import theme.styles.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostponeServiceBottomSheet() {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    ModalBottomSheet(
        modifier = Modifier.padding(top = 20.dp),
        onDismissRequest = { },
        sheetState = modalBottomSheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color(0xFFF3F3F3),
        dragHandle = {},
    ) {
        PostponeServiceBottomSheetContent(onDismissRequest = {

        }, onConfirmation = {

        })
    }
}


@Composable
fun PostponeServiceBottomSheetContent(onDismissRequest: () -> Unit,
                  onConfirmation: () -> Unit){

    Card(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        shape = RoundedCornerShape(10.dp),
        elevation = 15.dp, border = BorderStroke((0.5).dp, color = Colors.primaryColor)
    ) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

            Box(modifier = Modifier.fillMaxWidth().height(90.dp).background(color = Colors.primaryColor), contentAlignment = Alignment.Center) {
                TitleWidget(title = "Postpone Service", textColor = Color.White)
            }
          //  NewDateContent()
            ButtonContent(onDismissRequest = {
                onDismissRequest()
            }, onConfirmation = {
                onConfirmation()
            })
        }
    }

}

@Composable
fun ButtonContent(onDismissRequest: () -> Unit,
                  onConfirmation: () -> Unit){
    val buttonStyle = Modifier
        .fillMaxWidth(0.50f)
        .height(45.dp)

    val buttonStyle2 = Modifier
        .padding(start = 10.dp)
        .fillMaxWidth()
        .height(45.dp)
    Row (horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 30.dp, start = 20.dp, end = 20.dp)) {
        ButtonComponent(modifier = buttonStyle, buttonText = "Cancel", borderStroke = BorderStroke(1.dp, Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 18, shape = RoundedCornerShape(10.dp), textColor = Colors.primaryColor, style = TextStyle()){
            onDismissRequest()
        }
        ButtonComponent(modifier = buttonStyle2, buttonText = "Save", colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = RoundedCornerShape(10.dp), textColor = Color(color = 0xFFFFFFFF), style = TextStyle(), borderStroke = null){
            onConfirmation()
        }
    }
}


@Composable
fun NewDateContent(mainViewModel: MainViewModel, onDateSelected: (LocalDate) -> Unit, onUnAvailableDateSelected: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "New Date",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            textModifier = Modifier.fillMaxWidth().padding(start = 10.dp)
        )

        BookingCalendar(vendorAvailableDays = mainViewModel.dayAvailability.value,  onUnAvailableDateSelected = {
            onUnAvailableDateSelected()
        }, onDateSelected = {
            onDateSelected(it)
        })
    }
}



