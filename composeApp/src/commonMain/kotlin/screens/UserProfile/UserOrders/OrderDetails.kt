package screens.UserProfile.UserOrders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ImageComponent
import widgets.PageBackNavWidget

class OrderDetails() : Screen {
    @Composable
    override fun Content() {
        val rowModifier = Modifier
            .fillMaxWidth()
            .height(70.dp)

        val colModifier = Modifier
            .padding(top = 40.dp, end = 0.dp)
            .fillMaxWidth()
            .fillMaxHeight()

        Column(modifier = colModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = rowModifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1.0f)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    leftTopBarItem()
                }

            }
            OrderDetailList()
        }
    }
}

@Composable
fun leftTopBarItem() {
    val navigator = LocalNavigator.currentOrThrow
    PageBackNavWidget {
        navigator.pop()
    }
}

