package com.lfelipe.codehero.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfelipe.codehero.databinding.ActivityMainBinding
import com.lfelipe.codehero.model.Result
import com.lfelipe.codehero.view.adapter.MainAdapter
import com.lfelipe.codehero.view.adapter.PageAdapter
import com.lfelipe.codehero.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getCharacters(0, null)
        searchHero()
        observes()



    }

    private fun observes() {
        viewModel.characterLiveData.observe(this,{

            viewModel.getPaging(it.data.total/4)

            binding.rvHeroList.apply{
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = MainAdapter(it.data.results)
            }
        })

        viewModel.errorMsgLiveData.observe(this,{
            Toast.makeText(this,"ERRO AO CARREGAR: $it", Toast.LENGTH_LONG).show()
        })

        viewModel.listLiveData.observe(this,{
            binding.rvPaging.apply {
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = PageAdapter(it, binding.etSearch.text.toString())
            }
        })

    }


    private fun searchHero() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(name: CharSequence?, start: Int, before: Int, count: Int) {
                if (name != null) {
                    if(name.isNotEmpty())
                        viewModel.getCharacters(0, name.toString())

                }
                if(name.isNullOrBlank() or name.isNullOrEmpty()) {
                    viewModel.getCharacters(0, null)
                }


            }
        })
    }



}