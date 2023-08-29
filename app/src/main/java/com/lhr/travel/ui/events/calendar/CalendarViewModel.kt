package com.lhr.travel.ui.events.calendar

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.travel.data.calendar.CalendarRepository
import com.lhr.travel.data.calendar.CalendarResponse
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class CalendarViewModel(application: Application) : AndroidViewModel(application) {

    val isProgressVisible: MutableLiveData<Boolean> = MutableLiveData()
    private val repository: CalendarRepository = CalendarRepository()
    var calendarList: MutableLiveData<CalendarResponse> = MutableLiveData()

    fun getCalendarObserver(): MutableLiveData<CalendarResponse> {
        return calendarList
    }

    fun fetchCalendarApi() {
        isProgressVisible.postValue(true)
        repository.getCalendar()!!
            .subscribe(object : Observer<CalendarResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    // 處理錯誤
                    Log.v("DDDD", "" +e)
                    isProgressVisible.postValue(false)
                }

                override fun onComplete() {
                    isProgressVisible.postValue(false)
                }

                override fun onNext(t: CalendarResponse) {
                    calendarList.postValue(t)
                }
            })
    }
}