package com.lfelipe.codehero.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfelipe.codehero.R
import com.lfelipe.codehero.databinding.ActivityMainBinding
import com.lfelipe.codehero.model.Characters
import com.lfelipe.codehero.model.Result
import com.lfelipe.codehero.view.adapter.MainAdapter
import com.lfelipe.codehero.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getCharacters(0)
        observes()



    }

    private fun observes() {
        viewModel.characterLiveData.observe(this,{
            setupRecyclerView(it.data.results)

        })

        viewModel.errorMsgLiveData.observe(this,{
            Toast.makeText(this,"ERRO AO CARREGAR: $it", Toast.LENGTH_LONG).show()

        })
    }

    fun setupRecyclerView(hero: List<Result>) {
        binding.rvHeroList.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainAdapter(hero)

        }
    }

}