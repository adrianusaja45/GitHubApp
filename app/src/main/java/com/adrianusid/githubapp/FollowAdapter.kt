package com.adrianusid.githubapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrianusid.githubapp.databinding.RowUserBinding
import com.bumptech.glide.Glide

class FollowAdapter(private val listUser: List<Follow>) :
    RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {

        val binding = RowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder) {
            Glide.with(itemView.context)
                .load(listUser[position].avatarUrl)
                .circleCrop()
                .into(binding.imgAvatar)
            binding.tvFullName.text = listUser[position].login
            binding.tvUrl.text = listUser[position].url
            if (position == listUser.lastIndex){
                val param = itemView.layoutParams as RecyclerView.LayoutParams
                param.bottomMargin = 80
                itemView.layoutParams = param
            }else if(position == 0){

                val param = itemView.layoutParams as RecyclerView.LayoutParams
                param.topMargin = 160
                itemView.layoutParams = param


            }
        }
    }

    override fun getItemCount(): Int = listUser.size
    class ListViewHolder(val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root)

}