package com.example.githubapp.ui.view_screen

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubapp.R
import com.example.githubapp.databinding.ItemRepositorBinding
import com.example.githubapp.networking.data.ResponseData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.ItemHolderRepository>() {

    private lateinit var binding: ItemRepositorBinding

    inner class ItemHolderRepository(itemView: View) : ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun populateModel(model: ResponseData) {
            binding = ItemRepositorBinding.bind(itemView)

            binding.apply {
                tvNameProject.text = model.name
                tvLanguage.text = model.language
                tvStarCount.text = model.stargazers_count.toString()

                if (model.private) {
                    tvPrivate.text = "Private"
                } else {
                    tvPrivate.text = "Public"
                }

                if (model.language == "Java") {
                    ivFillColor.setBackgroundColor(Color.parseColor("#b07219"))
                } else if (model.language == "Kotlin") {
                    ivFillColor.setBackgroundColor(Color.parseColor("#A97BFF"))
                } else if (model.language == "C++") {
                    ivFillColor.setBackgroundColor(Color.parseColor("#F34B7D"))
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    val date = LocalDate.parse(model.updated_at, firstApiFormat)

                    tvUpdate.text =
                        "Updated on " +
                                "${date.month.toString().removeRange(3, date.month.toString().length)
                                .lowercase()} " +
                                "${date.dayOfMonth}, ${date.year}"
                } else {
                    tvUpdate.text = "date.dayOfWeek.toString()"
                }
            }

            itemView.setOnClickListener {
                onClick.invoke(model.svn_url)
            }

        }
    }

    private var onClick: (url: String) -> Unit = {}

    fun itemClickListener(onClick: (url: String) -> Unit) {
        this.onClick = onClick
    }

    var list = listOf<ResponseData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolderRepository {
        return ItemHolderRepository(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repositor, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemHolderRepository, position: Int) {
        holder.populateModel(list[position])
    }
}