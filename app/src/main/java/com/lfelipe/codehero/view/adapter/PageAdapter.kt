package com.lfelipe.codehero.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.lfelipe.codehero.R
import com.lfelipe.codehero.view.activity.MainActivity
import com.lfelipe.codehero.viewModel.MainViewModel


class PageAdapter (
    private val list: MutableList<Int>,
    private val heroName: String?
) : RecyclerView.Adapter<PageAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.page_number, parent, false)
        return ViewHolder(view, heroName)
    }

    override fun onBindViewHolder(holder: PageAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, heroName: String?) : RecyclerView.ViewHolder(itemView) {

        private lateinit var viewModel: MainViewModel

        private var characterName = heroName

        fun bind(pageNumber: Int) = with(itemView) {

            viewModel = ViewModelProvider(context as MainActivity).get(MainViewModel::class.java)

            findViewById<Button>(R.id.btnNumberPage).apply{

                text = pageNumber.toString()

                if(characterName.isNullOrEmpty() or characterName.isNullOrBlank()) {
                    characterName = null
                }


                setOnClickListener {

                    viewModel.getCharacters((pageNumber-1)*4, characterName)
                }


            }



        }


    }

}
