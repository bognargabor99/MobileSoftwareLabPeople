package hu.bme.aut.mszl.people.ui.util

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import hu.bme.aut.mszl.people.R

@Composable
fun CategorySaverDialog(
    subtitle: String,
    categories: List<String>,
    closeDialog: () -> Unit,
    onChoose: (String) -> Unit
) {
    var chosenCategory by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { closeDialog() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.choose_category),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            tint = Color(0xffa0a0a0),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { closeDialog() }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = subtitle)
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn {
                        items(categories.size) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp)
                                    .border(
                                        width = if (categories[it] == chosenCategory) 1.dp else 0.dp,
                                        color = MaterialTheme.colorScheme.run {if (categories[it] == chosenCategory) primary else background },
                                        shape = RoundedCornerShape(3.dp)
                                    )
                                    .clickable { chosenCategory = categories[it] },
                                text = categories[it],
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp)
                                    .border(
                                        width = if (stringResource(R.string.new_str) == chosenCategory) 1.dp else 0.dp,
                                        color = MaterialTheme.colorScheme.run {if (stringResource(R.string.new_str) == chosenCategory) primary else background },
                                        shape = RoundedCornerShape(3.dp)
                                    )
                                    .clickable { chosenCategory = "New" },
                                text = stringResource(id = R.string.new_category),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                if (chosenCategory.isEmpty()) {
                                    return@Button
                                }
                                onChoose(chosenCategory)
                            },
                            shape = RoundedCornerShape(25.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = stringResource(R.string.choose))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CategorySaverDialogPreview() {
    CategorySaverDialog(
        subtitle = stringResource(R.string.choose_category_detailed),
        categories = listOf("School", "Family", "Friends", "Work"),
        closeDialog = { },
        onChoose = { }
    )
}