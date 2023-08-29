package com.lhr.travel.ui.events.activitys

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.travel.data.activitys.ActivitysRepository
import com.lhr.travel.data.activitys.ActivitysResponse
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ActivitysViewModel(application: Application) : AndroidViewModel(application) {

    val isProgressVisible: MutableLiveData<Boolean> = MutableLiveData()
    private val repository: ActivitysRepository = ActivitysRepository()
    var ActivitysList: MutableLiveData<ActivitysResponse> = MutableLiveData()

    fun getActivitysObserver(): MutableLiveData<ActivitysResponse> {
        return ActivitysList
    }

    fun fetchActivitysApi() {
        isProgressVisible.postValue(true)
        repository.getActivitys()!!
            .subscribe(object : Observer<ActivitysResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    // 處理錯誤
                    isProgressVisible.postValue(false)
                }

                override fun onComplete() {
                    isProgressVisible.postValue(false)
                }

                override fun onNext(t: ActivitysResponse) {
                    ActivitysList.postValue(t)
                }
            })
    }
}