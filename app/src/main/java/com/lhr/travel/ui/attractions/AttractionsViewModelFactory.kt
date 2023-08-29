package com.lhr.travel.ui.attractions

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AttractionsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AttractionsViewModel::class.java)) {
            return AttractionsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}