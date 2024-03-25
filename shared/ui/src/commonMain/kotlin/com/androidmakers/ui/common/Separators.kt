package com.androidmakers.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.theme.AMAlpha

@Composable
fun separatorColor() = MaterialTheme.colorScheme.onSurface.copy(alpha = AMAlpha.big)

/**
 * A surface that's a bit darker or lighter to distinguish from the background
 */
@Composable
fun surfaceColor2() = MaterialTheme.colorScheme.onSurface.copy(alpha = AMAlpha.small)

val separatorHeight = 0.5.dp
