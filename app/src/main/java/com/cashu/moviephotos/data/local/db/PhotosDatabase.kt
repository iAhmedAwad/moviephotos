package com.cashu.moviephotos.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class PhotosDatabase: RoomDatabase() {

    abstract fun photosDao()

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
