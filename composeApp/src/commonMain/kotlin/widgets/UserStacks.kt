package widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.ImageComponent

@Composable
fun attachImage() {
         val modifier = Modifier
            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(25.dp))
            .size(50.dp)
            .clip(CircleShape)
        ImageComponent(imageModifier = modifier, imageRes = "therap1.jpg")
    }


@Composable
fun AttachImageStacks() {
    Box(modifier = Modifier.padding(top = 15.dp)){
        val modifier = Modifier
            .border(width = 3.dp, color = Color.White, shape = RoundedCornerShape(22.dp))
            .size(44.dp)
            .clip(CircleShape)
        ImageComponent(imageModifier = modifier, imageRes = "therap1.jpg")
        val modifier2 = Modifier
            .padding(start = 30.dp)
            .border(width = 3.dp, color = Color.White, shape = RoundedCornerShape(22.dp))
            .size(44.dp)
            .clip(CircleShape)
        ImageComponent(imageModifier = modifier2, imageRes = "doctor.jpg")
    }

}


