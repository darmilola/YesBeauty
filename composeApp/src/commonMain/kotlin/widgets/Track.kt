package widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.TextComponent

@Composable
fun StepsProgressBar(modifier: Modifier = Modifier, numberOfSteps: Int, currentStep: Int) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (step in 0..numberOfSteps) {
            if(step == numberOfSteps){
                Step(
                    isCompete = step < currentStep,
                    isCurrent = step == currentStep,
                    isLastStep = true
                )
            }
            else {
                Step(
                    modifier = Modifier.weight(1F),
                    isCompete = step < currentStep,
                    isCurrent = step == currentStep
                )
            }
        }
    }
}

@Composable
fun TrackOrderProgress(modifier: Modifier = Modifier, numberOfSteps: Int, currentOrderProgress: Int) {
    Column (
        modifier = modifier,
    ) {
        for (currentStep in 0..numberOfSteps) {
            when (currentStep) {
                0 -> {
                    TrackOrderStepView(viewHeightMultiplier = 4, currentStep = currentStep, currentOrderProgress = currentOrderProgress, isLastStep = true)
                }
                numberOfSteps -> {
                    TrackOrderStepView(viewHeightMultiplier = 2, currentStep = currentStep, currentOrderProgress = currentOrderProgress, isInitialStep = true)
                }
                else -> {
                    TrackOrderStepView(viewHeightMultiplier = 2, currentStep = currentStep, currentOrderProgress = currentOrderProgress)
                }
            }
        }
    }
}

@Composable
fun OrderStatusTextView(){

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        OrderStatusDate()
        OrderStatusText()

    }
}


@Composable
fun OrderStatusText(){
    Column (modifier = Modifier.wrapContentSize()) {

        TextComponent(
            text = "Your payment is successful",
            fontSize = 23,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Normal,
            lineHeight = 25,
            textModifier = Modifier.wrapContentSize().padding(top = 5.dp))

        TextComponent(
            text = "We just confirmed your Order.",
            fontSize = 20,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Color.Gray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 25,
            textModifier = Modifier.wrapContentSize().padding(top = 5.dp))
    }
}

@Composable
fun OrderStatusDate(){
    Row(modifier = Modifier.wrapContentSize()) {
        TextComponent(
            text = "SATURDAY DEC 23, 2023",
            fontSize = 18,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Color.Gray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier.wrapContentSize().padding(top = 5.dp))

        Box(modifier = Modifier.size(5.dp).padding(start = 5.dp, end = 5.dp).background(color = Color.DarkGray, shape = CircleShape))

        TextComponent(
            text = "3:11 PM",
            fontSize = 18,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Color.Gray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier.wrapContentSize().padding(top = 5.dp))
    }
}

@Composable
fun TrackOrderStepView(viewHeightMultiplier: Int = 0, currentStep: Int, currentOrderProgress: Int, isInitialStep: Boolean = false,  isLastStep: Boolean = false){
     Row(modifier = Modifier.height((viewHeightMultiplier * 65).dp).fillMaxWidth()) {
         EnhancedStep(
             modifier = Modifier.width(80.dp),
             isCompete = currentStep > currentOrderProgress,
             isCurrent = currentStep == currentOrderProgress,
             dividerMultiplier = viewHeightMultiplier,
             isLastStep = isInitialStep
         )
         Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            if(isInitialStep || !isLastStep) OrderStatusTextView()
         }

    }
}

@Composable
fun Step(modifier: Modifier = Modifier, isCompete: Boolean, isCurrent: Boolean) {
    val color = if (isCompete || isCurrent) Color(color = 0xFFFA2D65) else Color.LightGray
    val innerCircleColor = if (isCompete) Color.Red else Color.LightGray


    Box(modifier = modifier) {

        //Line
        Divider(
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 20.dp, end = 10.dp),
            color = color,
            thickness = 2.dp
        )

        //Circle
        Canvas(modifier = Modifier
            .size(10.dp)
            .align(Alignment.CenterStart)
            .border(
                shape = CircleShape,
                width = 1.dp,
                color = color
            ),
            onDraw = {
                drawCircle(color = innerCircleColor)
            }
        )
    }
}

@Composable
fun Step(modifier: Modifier = Modifier, isCompete: Boolean, isCurrent: Boolean, isLastStep: Boolean) {
    val color = if (isCompete || isCurrent) Color(color = 0xFFFA2D65) else Color.LightGray
    val innerCircleColor = if (isCompete) Color.Red else Color.LightGray

    Box(modifier = modifier) {
        //Circle
        Canvas(modifier = Modifier
            .size(10.dp)
            .align(Alignment.CenterStart)
            .border(
                shape = CircleShape,
                width = 1.dp,
                color = color
            ),
            onDraw = {
                drawCircle(color = innerCircleColor)
            }
        )
    }
}


@Composable
fun EnhancedStep(modifier: Modifier = Modifier, isCompete: Boolean = false, isCurrent: Boolean = false, dividerMultiplier: Int = 1, isLastStep: Boolean = false) {
    val dividerColor = if (isCurrent || isCompete) Color.DarkGray else Color.LightGray
    val dividerHeight = dividerMultiplier * 65


     Column (modifier = modifier.background(color = Color.Transparent),
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally) {
       if (isCurrent) CurrentDotIndicator(isCompete, isCurrent) else DotIndicator(isCompete, isCurrent)
        //Line
     if(!isLastStep) {
         Divider(
             modifier = Modifier.height(dividerHeight.dp).width(2.dp),
             color = dividerColor,
             thickness = 2.dp
         )
     }
  }
}

@Composable
fun CurrentDotIndicator(isCompete: Boolean, isCurrent: Boolean) {
    val circleColor = if (isCompete || isCurrent) Color.DarkGray else Color.LightGray
    val boxBg = Color(0x35444444)
    Box(
        modifier = Modifier.size(40.dp).background(color = boxBg, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(20.dp).background(color = circleColor, shape = CircleShape)) {}
    }
}

@Composable
fun DotIndicator(isCompete: Boolean, isCurrent: Boolean) {
    val circleColor = if (isCompete || isCurrent) Color.DarkGray else Color.LightGray
    Box(modifier = Modifier.size(20.dp).background(color = circleColor, shape = CircleShape)){}
}
