package com.lfelipe.codehero.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.lfelipe.codehero.model.Result
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lfelipe.codehero.R
import com.lfelipe.codehero.util.Constants.Api.IMAGE

class MainAdapter (
    private val characters: List<Result>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hero_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(character: Result) = with(itemView) {

        findViewById<TextView>(R.id.tvHeroName).text = character.name

            val imagem = character.thumbnail.path + IMAGE + character.thumbnail.extension
            val imageView = findViewById<ImageView>(R.id.ivHero)
            Glide.with(itemView.context).load(imagem).into(imageView)




        }

    }

}
