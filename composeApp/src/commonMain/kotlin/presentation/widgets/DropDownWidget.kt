package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import domain.Models.PhoneExtensionModel
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.components.ImageComponent
import presentation.components.TextComponent


@Composable
fun DropDownWidgetView(menuItems: List<String>,
                   iconRes: String = "drawable/country_icon.png",
                   iconSize: Int = 25,
                   placeHolderText: String,
                   menuExpandedState: Boolean,
                   selectedIndex : Int,
                   updateMenuExpandStatus : () -> Unit,
                   onDismissMenuView : () -> Unit,
                   onMenuItemClick : (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),
    ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            ) {

                Box(modifier = Modifier.fillMaxHeight().width(50.dp), contentAlignment = Alignment.Center){
                    ImageComponent(imageModifier = Modifier
                        .size(iconSize.dp), imageRes = iconRes, colorFilter = ColorFilter.tint(color = Colors.primaryColor))
                }

                Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.8f), contentAlignment = Alignment.Center){

                    TextComponent(
                        text =  if(selectedIndex != -1) menuItems[selectedIndex] else placeHolderText,
                        fontSize = if(selectedIndex != -1) 20 else 18,
                        fontFamily = if(selectedIndex != -1) GGSansSemiBold else  GGSansRegular,
                        textStyle = TextStyle(),
                        textColor = if(selectedIndex != -1) Colors.darkPrimary else  Color.Gray,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        textModifier = Modifier.wrapContentHeight().fillMaxWidth().padding(end = 5.dp))
                }

                Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center){
                    val imageModifier = Modifier
                        .size(30.dp)
                        .padding(start = 10.dp, top = 3.dp)
                    ImageComponent(
                        imageModifier = imageModifier,
                        imageRes = "drawable/chevron_down_icon.png",
                        colorFilter = ColorFilter.tint(color = Colors.primaryColor)
                    )
                }

            }
        }

        DropdownMenu(
            expanded = menuExpandedState,
            onDismissRequest = { onDismissMenuView() },
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .background(Color.White)
        ) {
            menuItems.forEachIndexed { index, title ->
                DropdownMenuItem(
                    onClick = {
                        if (index != 0) {
                            onMenuItemClick(index)
                        }
                    }) {
                    SubtitleTextWidget(text = title, fontSize = 20)
                }
            }
        }
}

@Composable
fun DropDownWidget(menuItems: List<String>, iconRes: String = "drawable/country_icon.png", placeHolderText: String, iconSize: Int = 25) {

    val expandedMenuItem = remember { mutableStateOf(false) }

    val selectedMenuIndex = remember { mutableStateOf(-1) }

    val modifier  = Modifier
        .padding(end = 10.dp, start = 10.dp, top = 20.dp)
        .fillMaxWidth()
        .height(65.dp)
        .border(border = BorderStroke(2.dp, color  = Colors.primaryColor), shape = RoundedCornerShape(15.dp))
        .background(color = Colors.lightPrimaryColor, shape = RoundedCornerShape(15.dp))

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,

        ) {
        DropDownWidgetView(
            menuItems = menuItems,
            iconRes = iconRes,
            iconSize = iconSize,
            placeHolderText = placeHolderText,
            menuExpandedState = expandedMenuItem.value,
            selectedIndex = selectedMenuIndex.value,
            updateMenuExpandStatus = {
                expandedMenuItem.value = true
            },
            onDismissMenuView = {
                expandedMenuItem.value = false
            },
            onMenuItemClick = { index ->
                selectedMenuIndex.value = index
                expandedMenuItem.value = false
            }
        )
    }
}


@Composable
fun CountryCodeDropDownWidget(menuItems: List<PhoneExtensionModel>) {

    val expandedMenuItem = remember { mutableStateOf(false) }

    val selectedMenuIndex = remember { mutableStateOf(0) }

    Column (
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        TextDropDownWidgetView(
            menuItems = menuItems,
            menuExpandedState = expandedMenuItem.value,
            selectedIndex = selectedMenuIndex.value,
            updateMenuExpandStatus = {
                expandedMenuItem.value = true
            },
            onDismissMenuView = {
                expandedMenuItem.value = false
            },
            onMenuItemClick = { index ->
                selectedMenuIndex.value = index
                expandedMenuItem.value = false
            }
        )
    }
}


@Composable
fun TextDropDownWidgetView(menuItems: List<PhoneExtensionModel>,
                           menuExpandedState: Boolean,
                           selectedIndex : Int,
                           updateMenuExpandStatus : () -> Unit,
                           onDismissMenuView : () -> Unit,
                           onMenuItemClick : (Int) -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),
    ) {

            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center){
                CountryCodeView(countryCode = menuItems[selectedIndex].countryCode, countryFlagRes = menuItems[selectedIndex].countryFlagRes)
            }
        }
    DropdownMenu(
        expanded = menuExpandedState,
        onDismissRequest = { onDismissMenuView() },
        modifier = Modifier
            .fillMaxWidth(0.30f)
            .background(Color.White)
    ) {
        menuItems.forEachIndexed { index, title ->
            DropdownMenuItem(
                onClick = {
                        onMenuItemClick(index)
                }) {
                CountryCodeView(countryCode = menuItems[index].countryCode, countryFlagRes =  menuItems[index].countryFlagRes)
            }
        }
    }
}





