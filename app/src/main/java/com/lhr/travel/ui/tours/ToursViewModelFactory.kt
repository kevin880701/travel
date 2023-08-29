package com.lhr.travel.ui.tours

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ToursViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToursViewModel::class.java)) {
            return ToursViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}