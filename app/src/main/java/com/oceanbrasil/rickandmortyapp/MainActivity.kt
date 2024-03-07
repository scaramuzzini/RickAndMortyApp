package com.oceanbrasil.rickandmortyapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oceanbrasil.rickandmortyapp.api.RickAndMortyRepository
import com.oceanbrasil.rickandmortyapp.domain.Character
import com.oceanbrasil.rickandmortyapp.domain.CharactersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()  {

    private lateinit var rvCharacterList: RecyclerView
    private lateinit var adapter: CharacterAdapter
    private var characterList = mutableListOf<Character>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bind TextView
        // val tvNomePersonagens = findViewById<TextView>(R.id.tvNomePersonagens)
        // tvNomePersonagens.text = "Novo texto..."
        rvCharacterList = findViewById(R.id.rvCharacterList)
        rvCharacterList.layoutManager = LinearLayoutManager(this)
        //rvCharacterList.layoutManager = GridLayoutManager(this,2)
        adapter = CharacterAdapter(this, characterList)
        rvCharacterList.adapter = adapter




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
                        Log.d("rickandmorty", "Count: ${it.info.count}")
                        characterList.clear()
                        characterList.addAll(it.results)
                        runOnUiThread {
                            adapter.notifyDataSetChanged()
                            Log.d("rickandmorty", "itemCount do Adapter: ${adapter.itemCount}")
                        }

                        var nomes = ""
                        it.results.forEach { c ->
                            // Log.d("rickandmorty", c.name)
                            nomes += "${c.name}\n"
                        }

                        Log.d("rickandmorty", nomes)
                        // tvNomePersonagens.text = nomes
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