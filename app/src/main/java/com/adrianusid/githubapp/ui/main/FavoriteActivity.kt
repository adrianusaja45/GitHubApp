package com.adrianusid.githubapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianusid.githubapp.FavoriteAdapter
import com.adrianusid.githubapp.FavoriteViewModelFactory
import com.adrianusid.githubapp.ListFavoriteViewModel
import com.adrianusid.githubapp.databinding.ActivityFavoriteBinding


class FavoriteActivity : AppCompatActivity() {
    private lateinit var listFavoriteViewModel: ListFavoriteViewModel
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

         listFavoriteViewModel = obtainViewModel(this@FavoriteActivity)
        listFavoriteViewModel.getAllFavorite().observe(this, { favoriteList ->
            if (favoriteList != null) {
                adapter.setListFavorite(favoriteList)
            }
        })

        adapter = FavoriteAdapter()

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): ListFavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ListFavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}