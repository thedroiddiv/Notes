package com.dxn.notes.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colors.onBackground,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.h4
    )
}

@Composable
fun HeadingText(
    modifier: Modifier = Modifier,
    text: String,
    weight: FontWeight = FontWeight.SemiBold,
    maxLines: Int = 1
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colors.onBackground,
        fontWeight = weight,
        style = MaterialTheme.typography.body1,
        maxLines = maxLines
    )
}


@Composable
fun BodyText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colors.onBackground.copy(0.7f),
        fontWeight = FontWeight.Normal,
        style = MaterialTheme.typography.body1,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

