package com.zeynelinho.gatherradiotry.service

import com.zeynelinho.gatherradiotry.model.RadioListModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RadioListAPI {

    @GET("api/radio/listnew/{id}/{uid}/{teltip}/{app}")
    fun getRadioListApi(
        @Header("apitoken") apitoken: String?,
        @Path("id") id: String?,
        @Path("uid") uid: String?,
        @Path("teltip") teltip: String?,
        @Path("app") app: String?
    ): Single<List<RadioListModel>>

}