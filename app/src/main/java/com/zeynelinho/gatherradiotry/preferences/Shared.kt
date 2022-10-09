package com.zeynelinho.gatherradiotry.preferences

import android.content.Context
import com.zeynelinho.gatherradiotry.adapter.RadioListAdapters

class Shared {

    object Constant{
        fun setToken(context: Context, token: String) {
            val preferences = context.getSharedPreferences("deviceToken", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("token", token)
            editor.apply()
        }

        fun getToken(context: Context): String {
            val token: String
            val preferences = context.getSharedPreferences("deviceToken", Context.MODE_PRIVATE)
            token = preferences.getString("token", "").toString()
            return token
        }

        fun setCountryId(context: Context,countryId: String){
            val preferences = context.getSharedPreferences("Country", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("countryId", countryId)
            editor.apply()
        }

        fun getCountryId(context: Context): String {
            val token: String
            val preferences = context.getSharedPreferences("Country", Context.MODE_PRIVATE)
            token = preferences.getString("countryId", "Hidden").toString()
            return token
        }

        fun setPlaying(context: Context , name : String , streamUrl : String , imageUrl : String , position : String) {
            val preferences = context.getSharedPreferences("currentPlay",Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("currentName",name)
            editor.putString("currentLink",streamUrl)
            editor.putString("currentImageUrl",imageUrl)
            editor.putString("currentPosition",position)
            editor.apply()
        }

        fun clearPlay (context: Context) {
            val preferences = context.getSharedPreferences("currentPlay",Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.remove("currentName")
            editor.remove("currentLink")
            editor.remove("currentImageUrl")
            editor.remove("currentPosition")
            editor.apply()
        }

        fun getPlaying(context: Context) : String {

            val name : String
            val streamUrl : String
            val imageUrl : String
            val position : String
            val preferences = context.getSharedPreferences("currentPlay",Context.MODE_PRIVATE)
            name = preferences.getString("currentName","").toString()
            streamUrl = preferences.getString("currentLink","").toString()
            imageUrl = preferences.getString("currentImageUrl","").toString()
            position = preferences.getString("currentPosition","").toString()
            return name + streamUrl + imageUrl + position

        }





    }
}
