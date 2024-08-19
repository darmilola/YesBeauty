package presentation.widgets

import GGSansRegular
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Enums.CardType
import domain.Models.PaymentCard
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors
import utils.getCardType

@Composable
fun PaymentCardItem(paymentCard: PaymentCard, onPaymentCardSelected: (PaymentCard) -> Unit) {
    val selectedBgColor: Color = if (paymentCard.isSelected) Colors.lightPrimaryColor else Color.White
    val columnModifier = Modifier
        .background(color = selectedBgColor, shape = RoundedCornerShape(15.dp))
        .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
        .clickable {
            onPaymentCardSelected(paymentCard)
        }
        .height(100.dp).fillMaxWidth()
    Row(modifier = columnModifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardTypeImage(paymentCard)
        CardDescription(paymentCard)
    }
}

@Composable
fun CardTypeImage(paymentCard: PaymentCard) {

    val cardNumber = paymentCard.cardNumber
    val firstDigit = paymentCard.cardNumber[0]
    val cardType = getCardType(firstDigit.digitToInt())
    var backgroundColor: Color = Color.Black
    var imageRes = "drawable/visa_icon.png"

    if (cardType == CardType.MASTERCARD.toPath()){
        backgroundColor = Color.Black
        imageRes = "drawable/mastercard_icon.png"
    }
    else if (cardType == CardType.VISA.toPath()){
        backgroundColor = Color(color = 0xff0253a5)
        imageRes = "drawable/visa_icon.png"
    }
    else if (cardType == CardType.AMEX.toPath()){
        backgroundColor = Color(color = 0xff3498d8)
        imageRes = "drawable/amex_icon.png"
    }
    else{
        backgroundColor = Color(color = 0xffd72927)
        imageRes = "drawable/card_error_icon.png"
    }

    Card(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp)
            .background(color = Color.Transparent)
            .height(60.dp)
            .width(80.dp),
        shape = RoundedCornerShape(10.dp),
        border = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backgroundColor)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val modifier = Modifier
                .padding(10.dp)
                .size(50.dp)
            ImageComponent(imageModifier = modifier, imageRes = imageRes)
        }
    }
}


@Composable
fun CardDescription(paymentCard: PaymentCard){
    val firstFourDigit = paymentCard.cardNumber.substring(IntRange(0,3))
    val displayDigit = "$firstFourDigit****"
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxHeight()
    Column(
        modifier = columnModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.Start,
    ) {
        TextComponent(
            text = paymentCard.firstname+" "+paymentCard.lastname,
            fontSize = 16,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 20,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2)

        TextComponent(
            text = displayDigit,
            fontSize = 16,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 20,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2)

    }
}

