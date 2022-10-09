package com.zeynelinho.gatherradiotry.baseAPI

import android.content.Context
import com.zeynelinho.gatherradiotry.model.CountryListModel
import com.zeynelinho.gatherradiotry.model.RadioListModel
import com.zeynelinho.gatherradiotry.preferences.Shared
import com.zeynelinho.gatherradiotry.service.CountryListAPI
import com.zeynelinho.gatherradiotry.service.RadioListAPI
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RadioApiService {


    //private val radio_list_url = "http://apiradio.gathermedia.com.tr/api/radio/listnew/"
    //radio api -> api/radio/listnew/

    //var radio_country_url: String = "http://apiradio.gathermedia.com.tr/api/radio/countrylist/"
    //country api -> api/radio/countrylist/


    private val BASE_URL = "http://apiradio.gathermedia.com.tr/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RadioListAPI::class.java)

    private val api2 = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(CountryListAPI::class.java)


    fun getRadioData(context : Context) : Single<List<RadioListModel>> {
        val token = "d81d8adf7d462f104695e359d268af50"
        val countryId = Shared.Constant.getCountryId(context)
        val tokenDevice = Shared.Constant.getToken(context)
        return api.getRadioListApi(
            token,
            countryId,
            tokenDevice,
            "android",
            "Radio")
    }

    /*fun getCountryData() : Single<List<CountryListModel>> {
        return api2.countryListApi("apitoken","uid","teltip","app","dil")
    }

     */
}