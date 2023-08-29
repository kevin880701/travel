package com.lhr.travel.ui.media.panos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.travel.data.panos.PanosRepository
import com.lhr.travel.data.panos.PanosResponse
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class PanosViewModel(application: Application) : AndroidViewModel(application) {

    val isProgressVisible: MutableLiveData<Boolean> = MutableLiveData()
    private val repository: PanosRepository = PanosRepository()
    var panosList: MutableLiveData<PanosResponse> = MutableLiveData()

    fun getPanosListObserver(): MutableLiveData<PanosResponse> {
        return panosList
    }

    fun fetchPanosApi() {
        isProgressVisible.postValue(true)
        repository.getPanos()
            .subscribe(object : Observer<PanosResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    // 處理錯誤
                    isProgressVisible.postValue(false)
                }

                override fun onComplete() {
                    isProgressVisible.postValue(false)
                }

                override fun onNext(t: PanosResponse) {
                    panosList.postValue(t)
                }
            })
    }
}