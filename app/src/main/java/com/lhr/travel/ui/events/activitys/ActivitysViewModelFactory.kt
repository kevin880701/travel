package com.lhr.travel.ui.events.activitys

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ActivitysViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActivitysViewModel::class.java)) {
            return ActivitysViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}