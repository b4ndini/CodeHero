package com.lfelipe.codehero.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.lfelipe.codehero.R
import com.lfelipe.codehero.databinding.ActivityCharacterBinding
import com.lfelipe.codehero.util.Constants.Api.LANDSCAPE_IMAGE

class CharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupShareLink()

    }

    private fun setupUI() {
        val name = intent.getStringExtra("NAME")
        val description = intent.getStringExtra("DESCRIPTION")
        val path = intent.getStringExtra("IMAGE")
        val image = "$path${LANDSCAPE_IMAGE}jpg"


        binding.tvCharactersName.text = name
        Glide.with(this@CharacterActivity).load(image).into(binding.ivHeroImage)


        if(description.isNullOrBlank() or description.isNullOrEmpty()){
            binding.tvDescription.text = resources.getString(R.string.null_description)
        }else{
            binding.tvDescription.text = description
        }
    }


    fun setupShareLink(){

        val link = intent.getStringExtra("LINK")
        binding.ivShare.setOnClickListener{
            val intent = Intent()
                intent.apply{
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT,"MARVEL SUPER HEROES:\n\n $link")
            }
            this.startActivity(Intent.createChooser(intent, "Compartilhar com"))
        }
    }

}