/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.data.remote.constants

class APIQueries {

    companion object {


        //region request constants
        const val METHOD = "method"
        const val FORMAT = "format"
        const val TEXT = "text"
        const val PAGE = "page"
        const val PER_PAGE = "per_page"
        const val API_KEY = "api_key"
        const val API_KEY_VALUE = "d17378e37e555ebef55ab86c4180e8dc"
        const val FORMAT_VALUE = "json"
        const val JSON_CALLBACK = "nojsoncallback"
        const val TEXT_VALUE = "Color"
        const val MIN_PER_PAGE = 20

        //endregion

        //region image url
        const val FARM = "https://farm"
        const val DOMAIN = ".static.flickr.com/"
        const val SLASH = "/"
        const val UNDERSCORE = "_"
        const val EXTENSION = ".jpg"
        //endregion


        /*
        http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
        {
            "id": "50397567507",
            "owner": "127728062@N04",
            "secret": "855de8e6a9",
            "server": "65535",
            "farm": 66,
            "title": "Stalker-Shadow-Chernobyl-ELITE-2020-002-escape",
            "ispublic": 1,
            "isfriend": 0,
            "isfamily": 0
        }

EX:- https://farm66.static.flickr.com/65535/50397567507_855de8e6a9.jpg

         */

        //region End points
        const val METHOD_PHOTOS_SEARCH = "flickr.photos.search"
        //endregion

    }


    /*
    https://www.flickr.com/services/rest/?method=flickr.photos.search
    &format=json&nojsoncallback=50&text=Color
    &page=1&per_page=20&api_key=d17378e37e555ebef55ab86c4180e8dc
     */
}
