package com.charlyco.circuitcompanion.ohms_law

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.charlyco.circuitcompanion.viewModels.OhmsLawViewModel
import com.charlyco.circuitcompanion.viewModels.OhmsLawViewModelFactory
import com.example.circuitcompanion.R

@Composable
fun OhmsLaw(navController: NavController) {
    val mContext = LocalContext.current
    Scaffold(
        topBar = {
            OhmsLawToolBar(mContext, navController)
        },
        backgroundColor = MaterialTheme.colors.background
    )
    { paddingValues ->
            OhmsLawScreen(Modifier.padding(paddingValues))
    }
}

@Composable
fun OhmsLawScreen(modifier: Modifier) {
    val owner = LocalViewModelStoreOwner.current
        val ohmViewModel: OhmsLawViewModel? = owner?.let {
            viewModel(
                it,
                "OhmViewModel",
                OhmsLawViewModelFactory(
                    LocalContext.current.applicationContext as Application
                )
            )
        }
    val result: String by ohmViewModel!!.resultSate.observeAsState("")
    val labels: MutableList<String> by ohmViewModel!!.labels.observeAsState(mutableListOf("", ""))

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (text_boxes, formula_card, resut_text_view) = createRefs()

            TextBoxes(labels, modifier = modifier
                .constrainAs(text_boxes) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(formula_card.top, margin = 8.dp)
                }
            )
                FormulaCard(
                    modifier = modifier.constrainAs(formula_card) {
                        centerHorizontallyTo(parent)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                    },
                    ohmViewModel
                )

            ResultTextView(
                result,
                modifier = modifier.constrainAs(resut_text_view) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top, margin = 16.dp)
                }
            )
        }
    }


@Composable
fun ResultTextView(result: String, modifier: Modifier) {
    Text(text = result)
}

@Composable
fun OhmsLawToolBar(mContext: Context, navController: NavController) {
    var onDisplayMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(text = " Ohms Law") },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate("home_screen") {
                      popUpTo("home_screen") { inclusive = true }
                    }
                }) {
                Icon(Icons.Default.ArrowBack, contentDescription = " navigation back to home screen")
            }
        },
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

@Composable
fun FormulaCard (modifier: Modifier, viewModel: OhmsLawViewModel?) {
    Surface(
        modifier = modifier
            .paddingFromBaseline(8.dp),
        contentColor = MaterialTheme.colors.background,
        color = MaterialTheme.colors.background,
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colors.background, MaterialTheme.shapes.small)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier) {
                Image(
                    painter = painterResource(id = R.drawable.icon_1),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable(true) { viewModel!!.setLabels("power", "resistance") }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_2),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("power", "current") }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_3),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("current", "resistance") }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_4),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("voltage", "current") }
                        .size(66.dp, 67.dp)
                )
            }
            Row(modifier) {
                Image(
                    painter = painterResource(id = R.drawable.icon_5),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("voltage", "power") }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_6),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("power", "current") }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_7),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("voltage", "resistance") }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_8),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("current", "resistance") }
                        .size(66.dp, 67.dp)
                )
            }
            Row(modifier) {
                Image(
                    painter = painterResource(id = R.drawable.icon_9),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("current", "voltage") }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_10),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("voltage", "resistance") }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_11),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("power", "voltage") }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_12),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable { viewModel!!.setLabels("power", "resistance") }
                        .size(66.dp, 67.dp)
                )
            }
        }
    }
}


@Composable
fun TextBoxes(labels: MutableList<String>, modifier: Modifier) {
    var textInput1 by remember { mutableStateOf("") }
    var textInput2 by remember { mutableStateOf("")}
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextField(
            modifier = modifier
                .width(164.dp)
                .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.medium)
                .heightIn(min = 56.dp)
                .padding(horizontal = 8.dp),
            value = textInput1,
            onValueChange = {textInput1 = it},
            textStyle = TextStyle(color = MaterialTheme.colors.onBackground),
            placeholder = { Text(labels[0], color = MaterialTheme.colors.primaryVariant)},
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
            )
        TextField(
            modifier = modifier
                .width(164.dp)
                .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.medium)
                .heightIn(min = 56.dp)
                .padding(horizontal = 8.dp),
            value = textInput2,
            textStyle = TextStyle(color = MaterialTheme.colors.onBackground),
            onValueChange = {textInput2 = it},
            placeholder = { Text(labels[1], color = MaterialTheme.colors.primaryVariant)},
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
    }
}

@Preview
@Composable
fun PreviewTextView(modifier: Modifier = Modifier) {
    val label = mutableListOf<String>("Power", "Current")
    TextBoxes(labels = label, modifier = modifier)
}