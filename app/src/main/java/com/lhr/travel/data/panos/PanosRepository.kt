package com.lhr.travel.data.panos

import com.lhr.travel.network.RetroInstance
import com.lhr.travel.network.ServiceManager
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PanosRepository {

    val retroInstance = RetroInstance.getRetroInstance().create(ServiceManager::class.java)
    fun getPanos(): Observable<PanosResponse> {
        return retroInstance.getPanos(lang = currentLanguage.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}