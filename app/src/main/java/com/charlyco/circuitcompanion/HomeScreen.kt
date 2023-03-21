package com.charlyco.circuitcompanion

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.circuitcompanion.R


@Composable
fun HomeScreen(navController: NavController) {
    val mContext = LocalContext.current
    Scaffold(
        topBar = {
            HomeToolBar(mContext) },
        contentColor = MaterialTheme.colors.background
        ) {contentPadding ->
        HomeScreenContent(Modifier.padding(contentPadding)) {
            when(it) {
                R.string.ohms_law_btn ->  navigateToScreen(navController,"ohms_law")
                R.string.color_code_btn -> navigateToScreen(navController,"color_code")
                R.string.rlc_cct_button -> navigateToScreen(navController,"rlc_circuit")
                R.string.star_delta_transform -> navigateToScreen(navController,"star_delta")
            }
        }
    }
}

fun navigateToScreen(navController: NavController, screen: String) {
    navController.navigate(screen){
        //popUpTo("home_screen") { inclusive = true }
    }
}

@Composable
fun HomeScreenContent(modifier: Modifier, clickedIcon: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(listOfFeatureIcons) {item ->
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colors.primaryVariant,
                modifier = modifier
                    .paddingFromBaseline(40.dp, 8.dp)
                    .clickable { clickedIcon(item.text) },
                elevation = 8.dp
            ) {
            FeatureIcon(modifier = modifier, image = item.drawable , text = item.text )
            }
        }
    }
}


@Composable
fun FeatureIcon(
        modifier: Modifier,
        @DrawableRes image: Int,
        @StringRes text: Int
    ) {

        Column(
            modifier = modifier.size(96.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = "icon image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Text(text = stringResource(id = text), color = MaterialTheme.colors.primary)
        }
}

@Composable
fun HomeToolBar(mContext: Context) {
    var onDisplayMenu by remember {mutableStateOf(false)}
    TopAppBar(
        title = {
            Text(text = "Circuit Companion")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(painterResource(id = R.drawable.home_toolbar_icon), "appIcon")
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        elevation = 0.dp,
        actions = {
            IconButton(onClick = { onDisplayMenu = !onDisplayMenu }) {
                Icon(Icons.Default.MoreVert, "Option menu")
            }

            DropdownMenu(
                expanded = onDisplayMenu,
                onDismissRequest = { onDisplayMenu = false }) {
                
                DropdownMenuItem(
                    onClick = { Toast.makeText(mContext, "Settings", Toast.LENGTH_SHORT).show() }) {
                    Text(text = "Settings") }

                DropdownMenuItem(
                    onClick = { Toast.makeText(mContext, "Feedback", Toast.LENGTH_SHORT).show() }) {
                    Text(text = "Feedback")
                }
            }
        }
    )
}

val listOfFeatureIcons = listOf(
    R.drawable.rlc to R.string.rlc_cct_button,
    R.drawable.stardelta to R.string.star_delta_transform,
    R.drawable.ohm_law to R.string.ohms_law_btn,
    R.drawable.ohm_symbol to R.string.color_code_btn
).map { DrawableStringPair(it.first, it.second) }

data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int,
)
