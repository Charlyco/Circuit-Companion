package com.limitless.circuitcalulator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainScreenViewModel(private val application: Application) : ViewModel() {
    // TODO: Implement the ViewModel
}

class MainScreenViewModelFactory (private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainScreenViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}