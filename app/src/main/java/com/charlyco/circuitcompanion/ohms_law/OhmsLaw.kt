package com.charlyco.circuitcompanion.ohms_law

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import kotlin.properties.Delegates

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
    val formulaId: Int by ohmViewModel!!.formulaId.observeAsState(0)

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (text_boxes, formula_card, result_text_view, calc_button) = createRefs()

            TextBoxes(labels, modifier = modifier
                .constrainAs(text_boxes) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(calc_button.top, margin = 8.dp)
                }
            )
                FormulaCard(
                    modifier = modifier.constrainAs(formula_card) {
                        centerHorizontallyTo(parent)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    },
                    ohmViewModel
                )

            ResultTextView(
                result,
                modifier = modifier.constrainAs(result_text_view) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top, margin = 8.dp)
                }
            )
            CalcButton(
                modifier = modifier.constrainAs(calc_button) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(formula_card.top, margin = 8.dp)
                },
                formulaId,
                ohmViewModel
            )
        }
    }

@Composable
fun CalcButton(modifier: Modifier, formulaId: Int, viewModel: OhmsLawViewModel?) {
    Button(
        onClick = { viewModel?.selectFormularAndcalculate(formulaId, value1, value2) },
        modifier = modifier.height(40.dp),
        contentPadding = PaddingValues(horizontal = 64.dp)
    ) {
        Text(text = "Calculate")
    }
}


@Composable
fun ResultTextView(result: String, modifier: Modifier) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Surface(modifier = modifier
        .paddingFromBaseline(8.dp)
        .border(2.dp, MaterialTheme.colors.primaryVariant, MaterialTheme.shapes.medium),
        contentColor = MaterialTheme.colors.background,
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium) {
        Row(
            modifier
                .height(84.dp)
                .width((screenWidth - 16).dp)
                .background(color = MaterialTheme.colors.primaryVariant)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = result)
        }
    }
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
                        .clickable(true) {
                            viewModel!!.setLabelsAndFormulaId(
                                "power",
                                "resistance",
                                R.drawable.icon_1
                            )
                        }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_2),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "power",
                                "current",
                                R.drawable.icon_2
                            )
                        }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_3),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "current",
                                "resistance",
                                R.drawable.icon_3
                            )
                        }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_4),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "voltage",
                                "current",
                                R.drawable.icon_4
                            )
                        }
                        .size(66.dp, 67.dp)
                )
            }
            Row(modifier) {
                Image(
                    painter = painterResource(id = R.drawable.icon_5),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "voltage",
                                "power",
                                R.drawable.icon_5
                            )
                        }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_6),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "power",
                                "current",
                                R.drawable.icon_6
                            )
                        }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_7),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "voltage",
                                "resistance",
                                R.drawable.icon_7
                            )
                        }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_8),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "current",
                                "resistance",
                                R.drawable.icon_8
                            )
                        }
                        .size(66.dp, 67.dp)
                )
            }
            Row(modifier) {
                Image(
                    painter = painterResource(id = R.drawable.icon_9),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "current",
                                "voltage",
                                R.drawable.icon_9
                            )
                        }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_10),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "voltage",
                                "resistance",
                                R.drawable.icon_10
                            )
                        }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_11),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "power",
                                "voltage",
                                R.drawable.icon_11
                            )
                        }
                        .size(66.dp, 67.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_12),
                    contentDescription = "",
                    modifier = modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel!!.setLabelsAndFormulaId(
                                "power",
                                "resistance",
                                R.drawable.icon_12
                            )
                        }
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
    val screenWidth = LocalConfiguration.current.screenWidthDp - 32
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextField(
            modifier = modifier
                .width((screenWidth / 2).dp)
                .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.medium)
                .heightIn(min = 48.dp)
                .padding(horizontal = 8.dp)
                .background(color = MaterialTheme.colors.background),
            value = textInput1,
            onValueChange = {textInput1 = it
                            value1 = textInput1 },
            placeholder = { Text(labels[0], color = MaterialTheme.colors.primaryVariant)},
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
        )
        Spacer(modifier = modifier.padding(8.dp))
        TextField(
            modifier = modifier
                .width((screenWidth / 2).dp)
                .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.medium)
                .heightIn(min = 48.dp)
                .padding(horizontal = 8.dp)
                .background(color = MaterialTheme.colors.background),
            value = textInput2,
            onValueChange = {textInput2 = it
                                value2 = textInput2 },
            placeholder = { Text(labels[1], color = MaterialTheme.colors.primaryVariant)},
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
    }
}

private var value1: String = ""
private  var value2: String = ""


@Preview
@Composable
fun PreviewTextView(modifier: Modifier = Modifier) {
    val label = mutableListOf<String>("Power", "Current")
    TextBoxes(labels = label, modifier = modifier)
}