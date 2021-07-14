package com.lfelipe.codehero.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lfelipe.codehero.R
import com.lfelipe.codehero.databinding.ActivityMainBinding
import com.lfelipe.codehero.util.Constants.Api.HERO_LIMIT
import com.lfelipe.codehero.util.roundToNextInt
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
        addItemDecoration()
        searchHero()
        observes()
    }

    private fun addItemDecoration() {
        val divider = DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
        divider.setDrawable(resources.getDrawable(R.drawable.divider_layer, null))
        binding.rvHeroList.addItemDecoration(divider)
    }

    private fun observes() {
        viewModel.characterLiveData.observe(this, { characters ->

            viewModel.getPaging((characters.data.total.toDouble() / HERO_LIMIT).roundToNextInt())

            binding.rvHeroList.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = MainAdapter(characters.data.results) { pos ->
                    val intent = Intent(this@MainActivity, CharacterActivity::class.java).apply {
                        putExtra("NAME", characters.data.results[pos].name)
                        putExtra("DESCRIPTION", characters.data.results[pos].description)
                        putExtra("IMAGE", characters.data.results[pos].thumbnail.path)
                    }
                    startActivity(intent)
                }


            }
        })

        viewModel.errorMsgLiveData.observe(this, {
            Toast.makeText(this, "ERRO AO CARREGAR: $it", Toast.LENGTH_LONG).show()
        })

        viewModel.listLiveData.observe(this, {
            binding.rvPaging.apply {
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
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
                    if (name.isNotEmpty())
                        viewModel.getCharacters(0, name.toString())

                }
                if (name.isNullOrBlank() or name.isNullOrEmpty()) {
                    viewModel.getCharacters(0, null)
                }


            }
        })
    }


}