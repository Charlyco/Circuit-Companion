package com.charlyco.circuitcompanion.star_delta

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.charlyco.circuitcompanion.viewModels.StarDeltaViewModel
import com.charlyco.circuitcompanion.viewModels.StarDeltaViewModelFactory
import com.example.circuitcompanion.R

@Composable
fun StarDelta(navController: NavHostController) {
    val mContext = LocalContext.current
    Scaffold(topBar = { StarDeltaToolBar(mContext, navController) }) {paddingValues ->
        StarDeltaScreen(Modifier.padding(paddingValues))
    }
}

@Composable
fun StarDeltaToolBar(mContext: Context, navController: NavHostController) {
        var onDisplayMenu by remember { mutableStateOf(false) }
        TopAppBar(
            title = {
                Text(text = "Star/Delta Transformation") },
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
fun StarDeltaScreen(modifier: Modifier) {
    val owner = LocalViewModelStoreOwner.current
    val starDeltaViewModel: StarDeltaViewModel? = owner?.let {
        viewModel(
            it,
            "StarDeltaViewModel",
            StarDeltaViewModelFactory(
                LocalContext.current.applicationContext as Application
            )
        )
    }
    val result: String by starDeltaViewModel!!.resultState.observeAsState("")
    val radioOptions = listOf("Star to Delta", "Delta to Star") 
    val userInputs = mutableListOf<Double>()
    val (selectedOption, setSelected) = remember { mutableStateOf(radioOptions[0]) }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResultTextView(modifier = modifier, result)
        Spacer(modifier = modifier.height(16.dp))
        ImageCard(modifier = modifier,
            selectedOption,
            setSelected,
            radioOptions)
        TextBoxes(modifier = modifier, userInputs)
        CalcButton(
            modifier = modifier,
            viewModel = starDeltaViewModel!!,
            selectedFormula = selectedOption,
            values = userInputs )
    }
}

@Composable
fun ResultTextView(modifier: Modifier, result: String) {
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
fun ArrowAnimation(key: String, modifier: Modifier) {
    val composition1 by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.arrow_left))
    val composition2 by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.arrow_right))

    val progress1 by animateLottieCompositionAsState(composition = composition1)
    val progress2 by animateLottieCompositionAsState(composition = composition2)
    when(key) {
        "Star to Delta" -> LottieAnimation(
                            modifier = modifier
                                .height(64.dp)
                                .width(64.dp),
                            composition = composition2,
                            progress = { progress2 },
                            renderMode = RenderMode.AUTOMATIC,
                            contentScale = ContentScale.Inside)

        "Delta to Star" -> LottieAnimation(
                            modifier = modifier
                                .height(64.dp)
                                .width(64.dp),
                            composition = composition1,
                            progress = { progress1 },
                            renderMode = RenderMode.AUTOMATIC,
                            contentScale = ContentScale.Inside)
    }
}

@Composable
fun StarDeltaRadioGroup(
    modifier: Modifier,
    options: List<String>,
    selected: String,
    onOptionSelected: (String) -> Unit,
) {
    Row(modifier.selectableGroup()) {
            options.forEach { option ->
                Row(
                    modifier = modifier.selectable(
                        selected = (option == selected),
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    )
                ) {
                    RadioButton(
                        selected = (option == selected),
                        onClick = null,
                        colors = RadioButtonDefaults
                            .colors(MaterialTheme.colors.secondary,
                                MaterialTheme.colors.secondaryVariant
                            )
                    )
                    Text(
                        text = option,
                        modifier = modifier.padding(horizontal = 8.dp),
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }

@Composable 
fun ImageCard(modifier: Modifier,
              selectedOption: String,
              setSelected: (String) -> Unit,
              radioOptions: List<String>
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp - 16
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Surface(
            modifier = modifier
                .width(screenWidth.dp)
                .height(164.dp)
                .paddingFromBaseline(8.dp),
            elevation = 8.dp,
            contentColor = MaterialTheme.colors.background,
            color = MaterialTheme.colors.background,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = modifier
                    //.fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Select conversion type",
                    color = MaterialTheme.colors.primary,
                    modifier = modifier.paddingFromBaseline(bottom = 8.dp))

                StarDeltaRadioGroup(
                    modifier = modifier.padding(bottom = 8.dp),
                    options = radioOptions,
                    selected = selectedOption,
                    onOptionSelected = setSelected)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.star),
                        contentDescription = "Star Arrangement",
                        modifier = modifier.padding(horizontal = 16.dp)
                    )

                    ArrowAnimation(
                        key = selectedOption,
                        modifier = modifier
                    )

                    Image(
                        painter = painterResource(
                            id = R.drawable.delta),
                        contentDescription = "Delta Arrangement",
                        modifier = modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CalcButton(
    modifier: Modifier,
    viewModel: StarDeltaViewModel,
    selectedFormula: String,
    values: MutableList<Double>
) {
Button(
    onClick = {viewModel.selectFormularAndCalculate(selectedFormula, values)},
    modifier = modifier.height(40.dp),
    contentPadding = PaddingValues(horizontal = 64.dp)
) {
    Text(text = "Calculate")
    }
}

@Composable
fun TextBoxes(modifier: Modifier, userInputs: MutableList<Double>) {
    var textInput1 by remember { mutableStateOf("") }
    var textInput2 by remember { mutableStateOf("")}
    var textInput3 by remember { mutableStateOf("")}
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
                userInputs.add(textInput1.toDouble()) },
            placeholder = { Text("Enter value", color = MaterialTheme.colors.primaryVariant)},
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
                userInputs.add(textInput2.toDouble()) },
            placeholder = { Text("Enter value", color = MaterialTheme.colors.primaryVariant)},
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
        TextField(
            modifier = modifier
                .width((screenWidth / 2).dp)
                .border(2.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.medium)
                .heightIn(min = 48.dp)
                .padding(horizontal = 8.dp)
                .background(color = MaterialTheme.colors.background),
            value = textInput3,
            onValueChange = {textInput3 = it
                userInputs.add(textInput3.toDouble()) },
            placeholder = { Text("Enter value", color = MaterialTheme.colors.primaryVariant)},
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
    }
}

@Preview
@Composable
fun PreviewScreen(modifier: Modifier = Modifier) {
    StarDeltaScreen(modifier = modifier)
}