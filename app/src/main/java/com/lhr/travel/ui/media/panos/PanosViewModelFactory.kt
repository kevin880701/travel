package com.lhr.travel.ui.media.panos

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PanosViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PanosViewModel::class.java)) {
            return PanosViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}