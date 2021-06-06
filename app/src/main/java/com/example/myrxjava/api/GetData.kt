package com.example.myrxjava.api

import com.example.myrxjava.CurrancyItems
import retrofit2.http.GET
import io.reactivex.Observable


interface GetData {

    @GET("daily_json.js")
    fun getData(): Observable<List<CurrancyItems>>
}