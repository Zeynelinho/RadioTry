package com.zeynelinho.gatherradiotry.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity

data class RadioListModel (

    @SerializedName("id")
    @Expose
    var id: String? = "" ,

    @ColumnInfo(name = "streamLink")
    @SerializedName("streamLink")
    @Expose
    var streamLink: String? = "" ,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String? = "" ,

    @ColumnInfo(name = "imageLink")
    @SerializedName("imageLink")
    @Expose
    var imageLink: String? = "" ,

    @ColumnInfo(name = "countryId")
    @SerializedName("countryId")
    @Expose
    var countryId: String? = "") {

    @PrimaryKey(autoGenerate = true)
    var radioId : Int = 0
}