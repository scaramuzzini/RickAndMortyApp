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
    private var currentPage = 1
    private var isFetching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bind TextView
        rvCharacterList = findViewById(R.id.rvCharacterList)
        rvCharacterList.layoutManager = LinearLayoutManager(this)
        adapter = CharacterAdapter(this, characterList)
        rvCharacterList.adapter = adapter

        checkConnectivity()

        loadCharacters(currentPage)
        recyclerViewScrollListener()

    }

    private fun loadCharacters(page: Int) {
        Toast.makeText(this,"Baixando página ${page}", Toast.LENGTH_SHORT).show()
        isFetching = true
        RickAndMortyRepository.instance.getAllCharacters(page).enqueue(object : Callback<CharactersResponse> {
            override fun onResponse(call: Call<CharactersResponse>, response: Response<CharactersResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        characterList.addAll(it.results)
                        runOnUiThread {
                            adapter.notifyDataSetChanged()
                        }
                        currentPage += 1
                        isFetching = false

                        val next = it.info.next
                        if (next == null) {
                            // This indicates there are no more pages to load
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                isFetching = false
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun recyclerViewScrollListener() {
        rvCharacterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isFetching) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        loadCharacters(currentPage)
                    }
                }
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