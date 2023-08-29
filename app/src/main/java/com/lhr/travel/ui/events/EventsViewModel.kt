package com.lhr.travel.ui.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.travel.data.main.MainRepository


class EventsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MainRepository = MainRepository()

    var mApplication = application
//    companion object {
//        var currentLanguage: MutableLiveData<String> =
//            MutableLiveData<String>().apply { postValue("zh-tw") }
//    }

}