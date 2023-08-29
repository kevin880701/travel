package com.lhr.travel.network

import com.lhr.travel.data.activitys.ActivitysResponse
import com.lhr.travel.data.attractions.AttractionsResponse
import com.lhr.travel.data.audio.AudioResponse
import com.lhr.travel.data.calendar.CalendarResponse
import com.lhr.travel.data.panos.PanosResponse
import com.lhr.travel.data.news.NewsResponse
import com.lhr.travel.data.tours.ToursResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceManager {
    @Headers("accept: application/json")
    @GET("{lang}/Attractions/All")
    fun getAttractionsList(
        @Path("lang") lang: String = "zh-tw",
        @Query("categoryIds") categoryIds: String = "12",
        @Query("nlat") nlat: Int = 84,
        @Query("elong") elong: Int = 84,
        @Query("page") page: Int = 1
    ): Observable<AttractionsResponse>


    @Headers("accept: application/json")
    @GET("{lang}/Events/News")
    fun getNews(
        @Path("lang") lang: String = "zh-tw",
        @Query("begin") begin: String = "2000-01-01",
        @Query("end") end: String = "2099-01-01",
        @Query("page") page: Int = 1
    ): Observable<NewsResponse>

    @Headers("accept: application/json")
    @GET("{lang}/Events/Activity")
    fun getActivitys(
        @Path("lang") lang: String = "zh-tw",
        @Query("begin") begin: String = "2000-01-01",
        @Query("end") end: String = "2099-01-01",
        @Query("page") page: Int = 1
    ): Observable<ActivitysResponse>

    @Headers("accept: application/json")
    @GET("{lang}/Events/Calendar")
    fun getCalendar(
        @Path("lang") lang: String = "zh-tw",
        @Query("categoryId") categoryId: Int = 1,
        @Query("begin") begin: String = "2000-01-01",
        @Query("end") end: String = "2099-01-01",
        @Query("page") page: Int = 1
    ): Observable<CalendarResponse>

    @Headers("accept: application/json")
    @GET("{lang}/Media/Panos")
    fun getPanos(
        @Path("lang") lang: String = "zh-tw",
        @Query("page") page: Int = 1
    ): Observable<PanosResponse>

    @Headers("accept: application/json")
    @GET("{lang}/Media/Audio")
    fun getAudio(
        @Path("lang") lang: String = "zh-tw",
        @Query("page") page: Int = 1
    ): Observable<AudioResponse>

    @Headers("accept: application/json")
    @GET("{lang}/Tours/Theme")
    fun getTours(
        @Path("lang") lang: String = "zh-tw",
        @Query("page") page: Int = 1
    ): Observable<ToursResponse>

}