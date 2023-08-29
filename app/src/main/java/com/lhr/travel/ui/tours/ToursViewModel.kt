package com.lhr.travel.ui.tours

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.travel.data.tours.ToursRepository
import com.lhr.travel.data.tours.ToursResponse
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ToursViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var currentCategoryIds: MutableLiveData<String> =
            MutableLiveData<String>().apply { postValue("") }
    }

    val isProgressVisible: MutableLiveData<Boolean> = MutableLiveData()
    private val repository: ToursRepository = ToursRepository()
    var ToursList: MutableLiveData<ToursResponse> = MutableLiveData()

    fun getToursListObserver(): MutableLiveData<ToursResponse> {
        return ToursList
    }

    fun fetchToursApi() {
        isProgressVisible.postValue(true)
        repository.getTours()
            .subscribe(object : Observer<ToursResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    // 處理錯誤
                    isProgressVisible.postValue(false)
                }

                override fun onComplete() {
                    isProgressVisible.postValue(false)
                }

                override fun onNext(t: ToursResponse) {
                    ToursList.postValue(t)
                }
            })
    }
}