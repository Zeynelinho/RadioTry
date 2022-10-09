package com.zeynelinho.gatherradiotry.service

import com.zeynelinho.gatherradiotry.model.CountryListModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface CountryListAPI {

    @GET("api/radio/countrylist/{uid}/{teltip}/{app}")
    fun countryListApi(@Header("apitoken")apitoken: String?,
                    @Path("uid") uid: String?,
                    @Path("teltip") teltip: String?,
                    @Path("app") app: String?,
                    @Query("dil") dil: String?) : Single<List<CountryListModel>>



}