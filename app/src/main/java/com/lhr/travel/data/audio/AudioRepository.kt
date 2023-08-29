package com.lhr.travel.data.audio

import com.lhr.travel.network.RetroInstance
import com.lhr.travel.network.ServiceManager
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AudioRepository {

    val retroInstance = RetroInstance.getRetroInstance().create(ServiceManager::class.java)
    fun getAudio(): Observable<AudioResponse> {
        return retroInstance.getAudio(lang = currentLanguage.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}