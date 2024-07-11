package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Models.ItemComponent
import presentation.viewmodels.MainViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent


@Composable
fun OrderItemDetail(mainViewModel: MainViewModel, itemList: ArrayList<ItemComponent>) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        .fillMaxHeight()
        Column(modifier = columnModifier,
            verticalArrangement = Arrangement.Top
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth().height((150 * 3).dp),
                contentPadding = PaddingValues(6.dp)
            ) {
                itemsIndexed(items = itemList) { it, item ->
                        OrderProductItemComponent(item)
                }
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 5.dp, start = 20.dp, end = 20.dp).height(50.dp).fillMaxWidth()
            ) {
                TextComponent(
                    text = "Total",
                    fontSize = 20,
                    fontFamily = GGSansRegular,
                    textStyle = TextStyle(),
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.padding(top = 5.dp).height(30.dp).fillMaxWidth(0.20f)
                )
                TextComponent(
                    text = "$2,300",
                    fontSize = 20,
                    fontFamily = GGSansRegular,
                    textStyle = TextStyle(),
                    textColor = Colors.primaryColor,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.padding(top = 5.dp).height(30.dp).fillMaxWidth()
                )

            }
        }
    }

@Composable
fun OrderProductItemComponent(itemComponent: ItemComponent) {
    val columnModifier = Modifier
        .padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
        .height(110.dp)
        Row(modifier = columnModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            OrderProductItemImage()
            OrderProductItemName(itemComponent)
        }
    }


@Composable
fun OrderProductItemImage() {
    val imageModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    Card(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .background(color = Color.White)
            .height(100.dp)
            .width(100.dp),
        shape = RoundedCornerShape(8.dp),
        border = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(
                imageModifier = imageModifier,
                imageRes = "drawable/woman2.jpg",
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun OrderProductItemName(itemComponent: ItemComponent){
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxHeight()
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment  = Alignment.Start,
        ) {

            val modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight()

            TextComponent(
                text = itemComponent.productName,
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = modifier,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            OrderProductItemQty(itemComponent)
        }
    }


@Composable
fun OrderProductItemQty(itemComponent: ItemComponent) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxHeight()
            .padding(top = 5.dp),
    ) {

        TextComponent(
            text = itemComponent.itemCount.toString()+"x",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Color.LightGray,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Medium,
            lineHeight = 30,
            textModifier = Modifier
                .padding(end = 10.dp)
                .wrapContentSize())

        TextComponent(
            text = itemComponent!!.productPrice.toString()+"$",
            fontSize = 20,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Colors.primaryColor,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .wrapContentSize())
    }
}


@Composable
fun OrderID(orderReference: Int) {
    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxHeight(),
    ) {
        Column(modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start) {

            TextComponent(
                text = "ORDER ID",
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Color.LightGray,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Medium,
                lineHeight = 30,
                textModifier = Modifier
                    .wrapContentSize())

            TextComponent(
                text = orderReference.toString(),
                fontSize = 17,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Black,
                lineHeight = 30,
                textModifier = Modifier
                    .padding(end = 10.dp, top = 5.dp)
                    .wrapContentSize())
        }

        Column(modifier = Modifier.weight(1f).fillMaxHeight(),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.End) {
            TextComponent(
                text = "Copy",
                fontSize = 16,
                fontFamily = GGSansRegular,
                textStyle = TextStyle(),
                textColor = Colors.primaryColor,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Black,
                lineHeight = 30,
                textModifier = Modifier
                    .wrapContentSize())
        }

    }
}







