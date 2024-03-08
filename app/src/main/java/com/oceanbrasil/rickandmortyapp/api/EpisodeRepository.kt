package com.oceanbrasil.rickandmortyapp.api

import android.health.connect.datatypes.CervicalMucusRecord.CervicalMucusAppearance
import android.util.Log
import com.oceanbrasil.rickandmortyapp.domain.Episode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object EpisodeRepository {
    val episodesMap: MutableMap<Int, Episode> = mutableMapOf()

    fun getEpisode(episodeId: Int): Episode? {
        return episodesMap[episodeId]
    }

    fun loadAllEpisodes(rickLastAppearanceUrl: String, completion: () -> Unit) {
        val rickLastAppearance = rickLastAppearanceUrl.substringAfterLast("/").toInt()
        var requestsCompleted = 0

        for (i in 1..rickLastAppearance) { // You should define the logic for determining the range
            RickAndMortyRepository.instance.getEpisodeByNumber(i).enqueue(object : Callback<Episode> {
                override fun onResponse(
                    call: Call<Episode>,
                    response: Response<Episode>
                ) {
                    val episodeResponse = response.body()
                    if (episodeResponse != null) {
                        episodesMap[episodeResponse.id] = episodeResponse
                    }
                    synchronized(this) {
                        requestsCompleted++
                        if (requestsCompleted == rickLastAppearance) {
                            completion()
                        }
                    }

                }

                override fun onFailure(call: Call<Episode>, t: Throwable) {
                    Log.e("rickandmorty", "Não foi possível carregar a lista de episódios")
                }
            })
        }
    }
}