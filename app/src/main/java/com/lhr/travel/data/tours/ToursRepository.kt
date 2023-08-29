package com.lhr.travel.data.tours

import com.lhr.travel.network.RetroInstance
import com.lhr.travel.network.ServiceManager
import com.lhr.travel.ui.attractions.AttractionsViewModel.Companion.currentCategoryIds
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ToursRepository {

    val retroInstance = RetroInstance.getRetroInstance().create(ServiceManager::class.java)

    fun getTours(): Observable<ToursResponse> {
        return retroInstance.getTours(lang = currentLanguage.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}