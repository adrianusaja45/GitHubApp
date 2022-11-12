package com.adrianusid.githubapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrianusid.githubapp.databinding.RowUserBinding
import com.bumptech.glide.Glide

class ListUserAdapter(private val listUser: List<ItemsItem>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickSwitch: ProfileCardClick


    fun setProfileCardClick(onItemClickSwitch: ProfileCardClick) {
        this.onItemClickSwitch = onItemClickSwitch
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {

        val binding =
            RowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
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
            itemView.setOnClickListener { onItemClickSwitch.onCardClicked(listUser[position]) }
        }
    }

    override fun getItemCount(): Int = listUser.size
    class ListViewHolder(val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root)

}