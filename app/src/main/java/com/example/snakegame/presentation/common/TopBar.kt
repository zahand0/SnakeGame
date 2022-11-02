package com.example.snakegame.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.snakegame.R
import com.example.snakegame.presentation.theme.DarkGreyLighter

@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onSurface
            )
        },
        backgroundColor = if (isSystemInDarkTheme()) DarkGreyLighter else MaterialTheme.colors.surface,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_button),
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    )
}