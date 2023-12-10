package com.example.githubapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.ItemUserBinding
import com.example.githubapp.networking.data.Owner

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemUserBinding.bind(itemView)
        fun populateModel(user: Owner) {
            Glide.with(itemView).load(user.avatar_url).into(binding.ivAvatar)
            binding.tvLogin.text = user.login

            itemView.setOnClickListener { onClick.invoke(user.html_url) }
        }
    }

    var usersList = listOf<Owner>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onClick: (url: String) -> Unit = {}

    fun itemClickListener(onClick: (url: String) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = usersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(usersList[position])
    }
}