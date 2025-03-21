package presentation.widgets

import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


class StatusProgressWidget {
    @Composable
    fun DotProgressProgressBarWidget(pageId: Int = 0, currentPage: Int = 0) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = Color.LightGray),
            horizontalArrangement = Arrangement.Start
        ) {
                 Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(color = if (pageId <= currentPage) Colors.primaryColor else Color.LightGray)
                        .fillMaxWidth()
                )

            }
        }
}