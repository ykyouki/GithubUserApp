package dev.rizfirsy.githubuserapp.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.rizfirsy.githubuserapp.data.response.ItemsItem
import dev.rizfirsy.githubuserapp.databinding.ItemGithubUserBinding


class GithubAdapter(private val itemsItem: List<ItemsItem>): RecyclerView.Adapter<GithubAdapter.MyViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsItem.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user =itemsItem[position]
        holder.bind(user)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(itemsItem[holder.adapterPosition])
        }
    }

    class MyViewHolder(val binding: ItemGithubUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            Glide.with(binding.ivGithubUserImage).load(user.avatarUrl).into(binding.ivGithubUserImage)
            binding.tvGithubUsername.text = user.login
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

}