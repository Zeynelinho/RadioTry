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


    


    private val BASE_URL = "Hidden"

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
        val token = "Hidden"
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
