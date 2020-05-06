package com.programmergabut.moviecatalogue.data

import com.google.gson.GsonBuilder
import com.programmergabut.moviecatalogue.data.api.GenreService
import com.programmergabut.moviecatalogue.data.api.NPMovieService
import com.programmergabut.moviecatalogue.data.api.OATvShowService
import com.programmergabut.moviecatalogue.data.model.json.genre.GenreApi
import com.programmergabut.moviecatalogue.data.model.json.npmovie.NPMovieApi
import com.programmergabut.moviecatalogue.data.model.json.oatvshow.OATvShowApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 *  Created by Katili Jiwo Adi Wiyono on 05/05/20.
 */

class RemoteDataSource {

    //https://api.themoviedb.org/3/movie/now_playing?api_key={apiKey}&language=en-US&page=1
    //https://api.themoviedb.org/3/tv/on_the_air?api_key={apiKey}&language=en-US&page=1

    private val strApi = "https://api.themoviedb.org/3/"
    private val apiKey = ""
    private val language = "en-US"
    private val page = "1"

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance
                ?: synchronized(this) {
                instance
                    ?: RemoteDataSource()
            }
    }


    private fun getNowPlayingMovieApi(): NPMovieService{
        return Retrofit.Builder()
            .baseUrl(strApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NPMovieService::class.java)
    }

    private fun getOnAirTvShowApi(): OATvShowService {
        return Retrofit.Builder()
            .baseUrl(strApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(OATvShowService::class.java)
    }

    private fun getGenreApi(): GenreService {
        return Retrofit.Builder()
            .baseUrl(strApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(GenreService::class.java)
    }

    suspend fun fetchNPMovieApi(): NPMovieApi = getNowPlayingMovieApi().fetchNpMovie(apiKey, language, page)

    suspend fun fetchOATvShowApi(): OATvShowApi = getOnAirTvShowApi().fetchOATvShow(apiKey, language, page)

    suspend fun fetchGenreApi(): GenreApi = getGenreApi().fetchGenre(apiKey, language)

}