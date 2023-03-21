package com.limitless.circuitcalulator.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.math.PI
import kotlin.math.sqrt

class TankCircuitViewModel (private val application: Application): ViewModel() {
    fun calcInductance(frequency: Double, capacitance: Double): String {
        val inductance = (sqrt((1 / (2 * PI * frequency))) / (capacitance / 1000000))
        val result = buildString {
            append("Inductance = SquareRoot((1/2 * PI * ")
            append(frequency)
            append(") / ")
            append(capacitance)
            append(") \n\n")
            append("Inductance = ${"%.2f".format(inductance)}H")
        }
        return result
    }

    fun calcCapacitance(frequency: Double, inductance: Double): String {
        val capacitance = (sqrt((1 / (2 * PI * frequency))) / inductance)

        val result = buildString {
            append("Capacitance = SquareRoot((1/2 * PI * ")
            append(frequency)
            append(") / ")
            append(inductance)
            append(") \n\n")
            append ("Capacitance = ${ "%.2f".format(capacitance * 1000000) }uF")
        }
        return result
    }

    fun calcFrequency(capacitance: Double, inductance: Double): String {
        val resonanceFreq = 1 / (2 * PI * (sqrt((capacitance / 1000000) * inductance)))

        val result = buildString {
            append("Resonance Frequency = 1/ (2 * PI * SquareRoot(")
            append(inductance)
            append(" * ")
            append(capacitance)
            append("). \n\n")
            append("Resonance Frequency = ${"%.1f".format(resonanceFreq)}Hz")
        }
        return result
    }

}

class TankCircuitViewModelFactory (private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TankCircuitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TankCircuitViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}