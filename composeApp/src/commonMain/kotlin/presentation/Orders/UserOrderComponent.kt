package presentation.Orders

import GGSansRegular
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Models.CustomerOrder
import domain.Models.PlacedOrderItemComponent
import kotlinx.serialization.json.Json
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent
import utils.calculatePlacedOrderTotalPrice

@Composable
fun UserOrderComponent(mainViewModel: MainViewModel, customerOrder: CustomerOrder, reviewPerformedActionUIStateViewModel: PerformedActionUIStateViewModel) {

      val itemList = Json.decodeFromString<ArrayList<PlacedOrderItemComponent>>(customerOrder.orderItems?.orderItemJson!!)
      val totalCost = calculatePlacedOrderTotalPrice(itemList)
      val navigator = LocalNavigator.currentOrThrow
      val columnModifier = Modifier
        .padding(start = 10.dp, top = 35.dp, bottom = 10.dp, end = 10.dp)
        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        .height(250.dp)
        Column(modifier = columnModifier,
            verticalArrangement = Arrangement.Top
        ) {
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                  StraightLine()

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(50.dp).fillMaxWidth()
                ) {

                   TextComponent(
                        text = customerOrder.orderStatus.toString(),
                        fontSize = 18,
                        fontFamily = GGSansRegular,
                        textStyle = TextStyle(),
                        textColor = Colors.primaryColor,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Black,
                        textModifier = Modifier.wrapContentHeight().fillMaxWidth(0.50f))

                    Box(modifier = Modifier.fillMaxWidth().clickable {
                          mainViewModel.setOrderItemComponents(itemList)
                          val details = OrderDetails()
                          details.setMainViewModel(mainViewModel)
                          details.setActionUiStateViewModel(reviewPerformedActionUIStateViewModel)
                          navigator.push(details)
                    },
                        contentAlignment = Alignment.CenterEnd) {
                        ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "drawable/forward_arrow.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
                    }
                }
                StraightLine()
            }
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth().height(120.dp),
                contentPadding = PaddingValues(6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(items = itemList) { it, item ->
                    OrderItemImage(item)
                }
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 5.dp, start = 15.dp, end = 15.dp).height(50.dp).fillMaxWidth()
            ) {
                TextComponent(
                    text = "Total",
                    fontSize = 20,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.padding(top = 5.dp).height(30.dp).fillMaxWidth(0.20f)
                )
                TextComponent(
                    text = "$$totalCost",
                    fontSize = 20,
                    fontFamily = GGSansRegular,
                    textStyle = TextStyle(),
                    textColor = Colors.primaryColor,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.padding(top = 5.dp).height(30.dp).fillMaxWidth()
                )

            }
            StraightLine()
        }
    }


@Composable
fun StraightLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(color = Color(color = 0x40CCCCCC))
    ) {
    }
}

@Composable
fun OrderItemImage(placedOrderItemComponent: PlacedOrderItemComponent) {
    val imageModifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(10.dp))

    Box(contentAlignment = Alignment.Center) {
            ImageComponent(
                imageModifier = imageModifier,
                isAsync = true,
                imageRes = placedOrderItemComponent.imageUrl!!,
                contentScale = ContentScale.Crop)
        }

}

