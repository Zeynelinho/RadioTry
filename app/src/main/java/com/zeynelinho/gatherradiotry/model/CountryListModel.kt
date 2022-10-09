package com.zeynelinho.gatherradiotry.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity

class CountryListModel{
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: String? = null

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String? = null

    @PrimaryKey(autoGenerate = true)
    var countryId = 0

}