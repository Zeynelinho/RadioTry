package com.zeynelinho.gatherradiotry.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zeynelinho.gatherradiotry.model.RadioListModel
import java.util.concurrent.locks.Lock


@Database(entities = [RadioListModel::class] , version = 1)
abstract class RadioDatabase : RoomDatabase() {
    abstract fun radioDao() : RadioDao


    

    companion object {

       @Volatile private var instance : RadioDatabase? = null

        private val lock = Any()

       operator fun invoke(context: Context) = instance ?: synchronized(lock) {

           instance ?: makeDatabase(context).also {
               instance = it
           }
       }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,RadioDatabase::class.java,"RadioDatabase").build()

    }

}