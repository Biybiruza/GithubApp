package com.example.githubapp.ui.view_screen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubapp.R
import com.example.githubapp.databinding.ItemRepositorBinding
import com.example.githubapp.networking.data.ResponseData

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.ItemHolderRepository> () {

    private lateinit var binding: ItemRepositorBinding

    inner class ItemHolderRepository(itemView: View) : ViewHolder(itemView) {
        fun populateModel(model: ResponseData) {
            binding = ItemRepositorBinding.bind(itemView)

            binding.tvNameProject.text = model.name
            binding.tvLanguage.text = model.language
            binding.tvStarCount.text = model.stargazers_count.toString()

            if (model.private){
                binding.tvPrivate.text = "Private"
            } else {
                binding.tvPrivate.text = "Public"
            }

            if (model.language == "Java"){
                binding.ivFillColor.setBackgroundColor(Color.parseColor("#b07219"))
            } else if (model.language == "Kotlin"){
                binding.ivFillColor.setBackgroundColor(Color.parseColor("#A97BFF"))
            } else if (model.language == "C++"){
                binding.ivFillColor.setBackgroundColor(Color.parseColor("#F34B7D"))
            }

        }
    }

//    fun onClick: ()

    var list = listOf<ResponseData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolderRepository {
        return ItemHolderRepository(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repositor, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemHolderRepository, position: Int) {
        holder.populateModel(list[position])
    }
}