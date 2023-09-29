package dev.rizfirsy.githubuserapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.rizfirsy.githubuserapp.data.database.FavoriteGithubUser
import dev.rizfirsy.githubuserapp.data.response.ItemsItem
import dev.rizfirsy.githubuserapp.databinding.ItemGithubUserBinding

class FavoriteGithubUserAdapter(private val favoriteUser: List<FavoriteGithubUser>) : RecyclerView.Adapter<FavoriteGithubUserAdapter.FavoriteUserViewHolder>() {

    private lateinit var onItemClickCallback: FavoriteGithubUserAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: FavoriteGithubUserAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class FavoriteUserViewHolder(private val binding: ItemGithubUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavoriteGithubUser) {
            with(binding) {
                Glide.with(ivGithubUserImage).load(user.avatarUrl).into(ivGithubUserImage)
                tvGithubUsername.text = user.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoriteUser.size
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val user = favoriteUser[position]
        holder.bind(user)

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(favoriteUser[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(favoriteUser: FavoriteGithubUser)
    }
}