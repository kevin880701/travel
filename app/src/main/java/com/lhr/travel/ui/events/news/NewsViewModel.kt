package com.lhr.travel.ui.events.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.travel.data.news.NewsRepository
import com.lhr.travel.data.news.NewsResponse
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    val isProgressVisible: MutableLiveData<Boolean> = MutableLiveData()
    private val repository: NewsRepository = NewsRepository()
    var NewsList: MutableLiveData<NewsResponse> = MutableLiveData()

    fun getNewsObserver(): MutableLiveData<NewsResponse> {
        return NewsList
    }

    fun fetchNewsApi() {
        isProgressVisible.postValue(true)
        repository.getNews()!!
            .subscribe(object : Observer<NewsResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    // 處理錯誤
                    isProgressVisible.postValue(false)
                }

                override fun onComplete() {
                    isProgressVisible.postValue(false)
                }

                override fun onNext(t: NewsResponse) {
                    NewsList.postValue(t)
                }
            })
    }
}