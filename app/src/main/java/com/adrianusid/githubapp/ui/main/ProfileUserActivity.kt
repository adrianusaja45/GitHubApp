package com.adrianusid.githubapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.adrianusid.githubapp.*
import com.adrianusid.githubapp.database.Favorite
import com.adrianusid.githubapp.databinding.ActivityProfileUserBinding
import com.adrianusid.githubapp.ui.insert.FavoriteAddViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class ProfileUserActivity : AppCompatActivity() {

    private lateinit var favoriteAddViewModel: FavoriteAddViewModel
    private var _binding: ActivityProfileUserBinding? = null
    private val mainViewModel: GitViewModel by viewModels()
    private var fav: Favorite? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val data = intent.getParcelableExtra<ItemsItem>(EXTRA_DATA) as ItemsItem
        mainViewModel.getDetailUser(data.login, this)
        mainViewModel.detailUser.observe(this) { detailUser ->
            getDetailData(detailUser)
        }

        Log.d("yuki", "$data")

        USER = data.login

        val followPagerAdapter = FollowPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = followPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
        supportActionBar?.elevation = 0f

        favoriteAddViewModel = obtainViewModel(this@ProfileUserActivity)

        val ref = this

        lifecycleScope.launch{
            val favs = favoriteAddViewModel.selectFavorite(data.login)

            ref.runOnUiThread {
                if (favs > 0) {
                    binding.fabFavFill.visibility = View.VISIBLE
                    binding.fabFavBorder.visibility = View.GONE
                } else {
                    binding.fabFavBorder.visibility = View.VISIBLE
                    binding.fabFavFill.visibility = View.GONE
                }
            }

        }


        fav = Favorite(data.login, data.url, data.avatarUrl)

        binding.fabFavBorder.setOnClickListener {
            favoriteAddViewModel.insert(fav as Favorite)
            binding.fabFavFill.visibility = View.VISIBLE
            binding.fabFavBorder.visibility = View.GONE
        }
        binding.fabFavFill.setOnClickListener {
            favoriteAddViewModel.delete(fav as Favorite)
            binding.fabFavBorder.visibility = View.VISIBLE
            binding.fabFavFill.visibility = View.GONE
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddViewModel::class.java)
    }

    private fun getDetailData(data: DetailUser) {


        Glide.with(this)
            .load(data.avatarUrl)
            .circleCrop()
            .into(binding.imgProfile)

        binding.tvUsername.text = data.login
        binding.tvFullname.text = data.name
        binding.tvCompany.text = data.company ?: "Data Kosong"
        binding.tvLocation.text = data.location ?: "Data Kosong"
        binding.tvRepository.text = data.repos.toString()

        Log.d("asuna", "$data")

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        var USER = "SomeData"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )


    }

}