package com.zeynelinho.gatherradiotry.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zeynelinho.gatherradiotry.model.RadioListModel


@Dao

interface RadioDao {

    @Query("SELECT * FROM RadioListModel")
    suspend fun getAllFavori() : List<RadioListModel>

    @Query("SELECT * FROM RadioListModel WHERE radioId = :radioId")
    suspend fun getRadioId(radioId : Int) : RadioListModel

    @Insert
    suspend fun addFavori(vararg radio : RadioListModel) : List<Long>

    @Delete
    suspend fun delFavori(radio : RadioListModel)

    @Query("DELETE FROM RadioListModel")
    suspend fun deleteAll()
}