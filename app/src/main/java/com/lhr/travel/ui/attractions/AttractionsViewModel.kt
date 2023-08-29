package com.lhr.travel.ui.attractions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.travel.data.attractions.AttractionsResponse
import com.lhr.travel.data.attractions.AttractionsRepository
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class AttractionsViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var currentCategoryIds: MutableLiveData<String> =
            MutableLiveData<String>().apply { postValue("") }
    }
    var title: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    val isProgressVisible: MutableLiveData<Boolean> = MutableLiveData()
    private val repository: AttractionsRepository = AttractionsRepository()
    var attractionsList: MutableLiveData<AttractionsResponse> = MutableLiveData()

    fun getAttractionsListObserver(): MutableLiveData<AttractionsResponse> {
        return attractionsList
    }

    fun fetchAttractionsApi() {
        isProgressVisible.postValue(true)
        repository.getAttractions()
            .subscribe(object : Observer<AttractionsResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    // 處理錯誤
                    isProgressVisible.postValue(false)
                }

                override fun onComplete() {
                    isProgressVisible.postValue(false)
                }

                override fun onNext(t: AttractionsResponse) {
                    attractionsList.postValue(t)
                }
            })
    }
}