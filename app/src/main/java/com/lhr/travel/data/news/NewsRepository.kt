package com.lhr.travel.data.news

import com.lhr.travel.network.RetroInstance
import com.lhr.travel.network.ServiceManager
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsRepository {

    val retroInstance = RetroInstance.getRetroInstance().create(ServiceManager::class.java)

    fun getNews(): Observable<NewsResponse>? {
        return retroInstance.getNews(lang = currentLanguage.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}