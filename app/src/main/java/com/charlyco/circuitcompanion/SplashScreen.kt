package com.charlyco.circuitcompanion

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.example.circuitcompanion.R
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier

@Composable
fun SplashScreen(navController: NavHostController) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splash_anim))
    val progress by animateLottieCompositionAsState(composition = composition)

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (animation, text) = createRefs()

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.constrainAs(animation) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top, margin = 64.dp)
            }
        )

        Text(
            "Charlyco Technologies",
            Modifier.constrainAs(text) {
                centerHorizontallyTo(parent)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            },
            color = MaterialTheme.colors.primary
        )
    }

    LaunchedEffect(key1 = true) {
        delay(4000)
        navController.navigate("home_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }
}