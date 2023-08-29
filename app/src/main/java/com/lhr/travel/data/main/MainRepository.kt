package com.lhr.travel.data.main

import com.lhr.travel.data.attractions.AttractionsResponse
import com.lhr.travel.network.RetroInstance
import com.lhr.travel.network.ServiceManager
import com.lhr.travel.ui.attractions.AttractionsViewModel.Companion.currentCategoryIds
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainRepository {

//    val retroInstance = RetroInstance.getRetroInstance().create(ServiceManager::class.java)
//    fun getAttractions(): Observable<AttractionsResponse> {
//        return retroInstance.getAttractionsList(categoryIds = currentCategoryIds.value!!)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//    }
}