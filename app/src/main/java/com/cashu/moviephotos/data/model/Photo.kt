package com.cashu.moviephotos.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos_tbl")
data class Photo(
    @PrimaryKey(autoGenerate = true)
    var primaryId: Int = 0,
    var farm: Int = 0,
    var id: String = "",
    var isfamily: Int = 0,
    var isfriend: Int = 0,
    var ispublic: Int = 0,
    var owner: String = "",
    var secret: String = "",
    var server: String = "",
    var title: String = ""
)
