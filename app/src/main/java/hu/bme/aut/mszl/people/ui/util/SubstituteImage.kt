package hu.bme.aut.mszl.people.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubstituteImage(
    name: String,
    size: Dp = 50.dp,
    fontSize: TextUnit = 24.sp
){
    Box(
        modifier = Modifier
            .size(width = size, height = size)
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        val text = String(name.split(' ').map { it.first() }.toCharArray())
        Text(
            text = "${text.first()}${text.last()}",
            fontSize = fontSize,
        )
    }
}

@Preview
@Composable
fun SubstituteImagePreview() {
    SubstituteImage(name = "Gabor Bela Bognar")
}