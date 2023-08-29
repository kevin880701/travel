package com.lhr.travel.ui.media.audio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.travel.data.audio.AudioRepository
import com.lhr.travel.data.audio.AudioResponse
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class AudioViewModel(application: Application) : AndroidViewModel(application) {

    val isProgressVisible: MutableLiveData<Boolean> = MutableLiveData()
    private val repository: AudioRepository = AudioRepository()
    var audioList: MutableLiveData<AudioResponse> = MutableLiveData()

    fun getAudioListObserver(): MutableLiveData<AudioResponse> {
        return audioList
    }

    fun fetchAudioApi() {
        isProgressVisible.postValue(true)
        repository.getAudio()
            .subscribe(object : Observer<AudioResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    // 處理錯誤
                    isProgressVisible.postValue(false)
                }

                override fun onComplete() {
                    isProgressVisible.postValue(false)
                }

                override fun onNext(t: AudioResponse) {
                    audioList.postValue(t)
                }
            })
    }
}