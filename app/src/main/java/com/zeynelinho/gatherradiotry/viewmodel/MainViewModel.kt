package com.zeynelinho.gatherradiotry.viewmodel


import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.zeynelinho.gatherradiotry.baseAPI.RadioApiService
import com.zeynelinho.gatherradiotry.model.RadioListModel
import com.zeynelinho.gatherradiotry.roomDB.RadioDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : BaseViewModel(application){

    private val radioApiService = RadioApiService()
    private val compositeDisposable = CompositeDisposable()

    val radioData = MutableLiveData<List<RadioListModel>>()
    val radioError = MutableLiveData<Boolean>()
    val radioLoading = MutableLiveData<Boolean>()

    fun refreshData(context: Context) {
        getRadioDataFromApi(context)
    }

    private fun getRadioDataFromApi(context: Context) {

        radioLoading.value = true

        compositeDisposable.add(
            radioApiService.getRadioData(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<RadioListModel>>(){
                    override fun onSuccess(t: List<RadioListModel>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        radioLoading.value = false
                        radioError.value = true

                    }

                })
        )


    }

    private fun showRadioData(radioList : List<RadioListModel>) {
        radioData.value = radioList
        radioLoading.value = false
        radioError.value = false
    }


    private fun storeInSQLite (list : List<RadioListModel>) {

        launch {

            val dao = RadioDatabase(getApplication()).radioDao()

            dao.deleteAll()

            val listLong = dao.addFavori(*list.toTypedArray())
            var i = 0

            while (i < list.size) {
                list[i].radioId = listLong[i].toInt()
                i = i + 1
            }

            showRadioData(list)

        }

    }


}




