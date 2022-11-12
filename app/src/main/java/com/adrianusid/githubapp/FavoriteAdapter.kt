package com.adrianusid.githubapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrianusid.githubapp.database.Favorite
import com.adrianusid.githubapp.databinding.RowUserBinding
import com.adrianusid.githubapp.ui.main.ProfileUserActivity
import com.bumptech.glide.Glide

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorite = ArrayList<Favorite>()

    fun setListFavorite(listFavorite: List<Favorite>){
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    inner class FavoriteViewHolder(private val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fav : Favorite){
            with(binding){
                Glide.with(itemView.context)
                    .load(fav.avatar_url)
                    .circleCrop()
                    .into(binding.imgAvatar)
                binding.tvFullName.text = fav.login
                binding.tvUrl.text = fav.url
                val user = ItemsItem(fav.login, fav.url!!, fav.avatar_url!!)
                itemView.setOnClickListener { intent ->
                    val detail = Intent(intent.context, ProfileUserActivity::class.java)
                    detail.putExtra(ProfileUserActivity.EXTRA_DATA,user)
                    itemView.context.startActivity(detail)
                }
            }
        }
    }
}