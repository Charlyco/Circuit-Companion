package com.charlyco.circuitcompanion.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StarDeltaViewModel(private val application: Application): ViewModel() {
    public val resultState: MutableLiveData<String> = MutableLiveData("")

    fun calcStarParam(a: Double, b: Double, c: Double) {
        val Ra: Double = (a * b) / (a + b + c)
        val Rb: Double = (b * c) / (a + b + c)
        val Rc: Double = (a * c) / (a + b + c)
        val result  = buildString {
            append("The Resulting Delta Connection:\n\n")
            append("Ra = (Rab * Rac) / (Rab + Rac+ Rbc) = ")
            append("%.2f".format(Ra))
            append(" Ohms\n\n")
            append("Rb = (Rab * Rbc) / (Rab + Rac+ Rbc) = ")
            append("%.2f".format(Rb))
            append(" Ohms\n\n")
            append("Rc = (Rac * Rbc) / (Rab + Rac+ Rbc) = ")
            append("%.2f".format(Rc))
            append(" Ohms\n\n")
        }
        resultState.value = result
    }

    fun calcDeltaParam(a: Double, b: Double, c: Double) {
        val Rac = a + b + ((a * b)/c)
        val Rab = b + c + ((b * c)/a)
        val Rbc = a + c + ((a * c)/b)
        val result = buildString {

            append("The Resulting Star Connection:\n\n")
            append("Rac = a + b + ((a * b)/c) = ")
            append("%.2f".format(Rac))
            append(" Ohms\n\n")
            append("Rab = b + c + ((b * c)/a) = ")
            append("%.2f".format(Rab))
            append(" Ohms\n\n")
            append("Rbc = a + c + ((a * c)/b) = ")
            append("%.2f".format(Rbc))
            append(" Ohms\n\n")
        }
        resultState.value = result
    }

    fun selectFormularAndCalculate(selectedFormula: String, values: MutableList<Double>) {
        when(selectedFormula) {
            "start to delta" -> calcDeltaParam(values[0], values[1], values[2])
            "delta to star" -> calcStarParam(values[0], values[1], values[2])
        }
    }

}

class StarDeltaViewModelFactory (private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StarDeltaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StarDeltaViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}