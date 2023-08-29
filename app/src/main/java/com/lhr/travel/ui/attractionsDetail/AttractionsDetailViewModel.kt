package com.lhr.travel.ui.attractionsDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.travel.data.attractionsDetail.AttractionsDetailRepository


class AttractionsDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AttractionsDetailRepository = AttractionsDetailRepository()

    var mApplication = application
    var introduction: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    var address: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    var title: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    var modified: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    var url: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }


}