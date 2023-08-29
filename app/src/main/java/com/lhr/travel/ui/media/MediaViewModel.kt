package com.lhr.travel.ui.media

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MediaViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var previousMediaId: MutableLiveData<Int> =
            MutableLiveData<Int>().apply { postValue(0) }
        var currentMediaId: MutableLiveData<Int> =
            MutableLiveData<Int>().apply { postValue(null) }
        var isPlaying: MutableLiveData<Boolean> =
            MutableLiveData<Boolean>().apply { postValue(false) }
    }
}