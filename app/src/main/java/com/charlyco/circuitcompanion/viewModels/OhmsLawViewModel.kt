package com.charlyco.circuitcompanion.viewModels

import androidx.lifecycle.ViewModel
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import kotlin.math.pow
import kotlin.math.sqrt


class OhmsLawViewModel(application: Application) : ViewModel() {
    var resultSate: MutableLiveData<String> = MutableLiveData("")
    var labels: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf("", ""))

    fun setLabels(s: String, s1: String){
        labels.value = mutableListOf(s, s1)
    }

    fun calcVoltageWithPandR(p: Double, r: Double) {
        val result = buildString {
            append("Voltage = ")
            append(formatResult((p * r)))
            append(" volts")
        }
        resultSate.value = result
    }

    fun calcVoltageWithPandI(p: Double, i: Double) {
        val result = buildString {
            append("Voltage = ")
            append(formatResult((p / i)))
            append(" volts")
        }
        resultSate.value = result
    }

    fun calcVoltageWithIandR(i: Double, r: Double) {
        val result = buildString {
            append("Voltage = ")
            append(formatResult((i * r)))
            append(" volts")
        }
        resultSate.value = result
    }

    fun calcResistanceWithVandI(v: Double, i: Double) {
        val result = buildString {
            append("Resistance = ")
            append(formatResult((v / i)))
            append(" ohms")
        }
        resultSate.value = result
    }

    fun calcResistanceWithVandP(v: Double, p: Double) {
        val result = buildString {
            append("Resistance = ")
            append(formatResult((v.pow(2) / p)))
            append(" ohms")
        }
        resultSate.value = result
    }

    fun calcResistanceWithIandP(i: Double, p: Double) {
        val result = buildString {
            append("Resistance = ")
            append(formatResult((p / i.pow(2))))
            append(" ohms")
        }
        resultSate.value = result
    }

    fun calcPowerWithVandR(v: Double, r: Double) {
        val result = buildString {
            append("Power = ")
            append(formatResult((v.pow(2) / r)))
            append(" watts")
        }
        resultSate.value = result
    }

    fun calcPowerWithRandI(r: Double, i: Double) {
        val result = buildString {
            append("Power = ")
            append(formatResult((i.pow(2) * r)))
            append(" watts")
        }
        resultSate.value = result
    }

    fun calcPowerWithIandV(i: Double, v: Double) {
        val result = buildString {
            append("Power = ")
            append(formatResult((i * v)))
            append(" watts")
        }
        resultSate.value = result
    }

    fun calcCurrentWithRandV(r: Double, v: Double) {
        val result = buildString {
            append("Current = ")
            append(formatResult((v / r)))
            append(" amperes ")
        }
        resultSate.value = result
    }

    fun calcCurrentWithPandV(p: Double, v: Double) {
        val result = buildString {
            append("Current = ")
            append(formatResult((p/ v)))
            append(" amperes")
        }
        resultSate.value = result
    }

    fun calcCurrentWithRandP(r: Double, p: Double) {
        val result = buildString {
            append(" Current = ")
            append(formatResult(sqrt((p / r))))
            append(" amperes")
        }
        resultSate.value = result
    }
    private fun formatResult(p: Double) = "%.2f".format(p)
}

class OhmsLawViewModelFactory (private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OhmsLawViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OhmsLawViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}