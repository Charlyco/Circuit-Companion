package com.charlyco.circuitcompanion.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.math.pow

class ColorCodeViewModel(private val application: Application): ViewModel() {

    fun calculateResistorValue(
        firstBand: Int,
        secondBand: Int,
        thirdBand: Int,
        fourthBand: String
    ): String {
        val firstColours = "${firstBand - 1}${secondBand - 1}"

        val resistorValue = firstColours.toInt() * 10.0.pow(thirdBand - 1)
        val result = buildString {
            append("Value of Resistor: ")
            append("${resistorValue.toInt()}")
            append(" ohms")
            if (resistorValue > 999) {
                append("(")
                append("%.2f".format((resistorValue / 1000)))
                append(" kilo ohms)")
            }
            append("\n\n Tolerance: ")
            append("${getTolerance(fourthBand) * 100}% ")
        }
        return   result
    }

    private fun getTolerance(colour: String): Float {
        var tolerance = 0f
        when (colour) {
            "Silver" -> tolerance = 0.1f
            "Gold" -> tolerance = 0.05f
        }
        return tolerance
    }

}

class ColorCodeViewModelFactory (private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColorCodeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ColorCodeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}