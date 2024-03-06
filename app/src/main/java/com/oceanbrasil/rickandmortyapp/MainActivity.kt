package com.oceanbrasil.rickandmortyapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.oceanbrasil.rickandmortyapp.api.RickAndMortyRepository
import com.oceanbrasil.rickandmortyapp.domain.CharactersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkConnectivity()

        RickAndMortyRepository.instance.getAllCharacters().enqueue(object:
            Callback<CharactersResponse> {
            override fun onResponse(
                call: Call<CharactersResponse>,
                response: Response<CharactersResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("rickandmorty", "Veio resposta OK")

                    response.body()?.let {
                        it.results.forEach { c ->
                            Log.d("rickandmorty", c.name)
                        }
                    }
                } else {
                    Log.d("rickandmorty", "API Respondeu: ${response.code()}")
                }

            }

            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                t.message?.let { Log.d("rickandmorty", it) }
            }
        })
    }

    private fun checkConnectivity() {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .build()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        "Conexão com internet ativa!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onLost(network: Network) {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        "Conexão com internet inativa!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }
}