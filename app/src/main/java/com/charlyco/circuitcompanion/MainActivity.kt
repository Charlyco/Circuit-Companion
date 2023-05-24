package com.charlyco.circuitcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.charlyco.circuitcompanion.ohms_law.OhmsLaw
import com.charlyco.circuitcompanion.resistor_color_coding.ResistorColorCode
import com.charlyco.circuitcompanion.rlc_circuit.RlcCircuit
import com.charlyco.circuitcompanion.star_delta.StarDelta
import com.charlyco.circuitcompanion.ui.theme.CircuitCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    CircuitCompanionTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "splash_screen") {
            composable("splash_screen") {
                SplashScreen(navController = navController)
            }
            composable("home_screen") {
                HomeScreen(navController = navController)
            }
            composable("ohms_law") {
                OhmsLaw(navController)
            }
            composable("star_delta") {
                StarDelta(navController)
            }
            composable("rlc_circuit") {
                RlcCircuit()
            }
            composable("color_code") {
                ResistorColorCode()
            }
        }
    }
}

