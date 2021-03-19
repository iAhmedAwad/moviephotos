/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cashu.moviephotos.data.local.PhotosDao
import com.cashu.moviephotos.data.model.Photo

@Database(
    entities = [
    Photo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PhotosDatabase: RoomDatabase() {

    abstract fun photosDao() : PhotosDao

    companion object {
        @Volatile
        private var INSTANCE: PhotosDatabase? = null

        fun getDatabase(context: Context): PhotosDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhotosDatabase::class.java,
                    "photos_database"
                ).build()
                println("PhotosDatabase synchronized!")
                INSTANCE = instance
                return instance
            }
        }
    }

}
