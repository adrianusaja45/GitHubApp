package com.adrianusid.githubapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianusid.githubapp.databinding.ActivityMainBinding
import com.adrianusid.githubapp.ui.main.FavoriteActivity
import com.adrianusid.githubapp.ui.main.ProfileUserActivity

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: GitViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.progressBar.hide()
        binding.tvMain.visibility = View.VISIBLE
        binding.rvGithub.setHasFixedSize(true)
        applyTheme()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService<SearchManager>()

        val searchView = menu.findItem(R.id.usr_search).actionView as SearchView
        mainViewModel.response.observe(this) { response ->
            showRecyclerList(response)
        }
        mainViewModel.isLoading.observe(this@MainActivity) {
            showLoading(it)

        }

        searchView.setSearchableInfo(searchManager?.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUser(query, this@MainActivity)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })



        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.fav_usr -> {
                val user = Intent(this, FavoriteActivity::class.java)
                startActivity(user)
                true
            }
            R.id.setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    private fun applyTheme(){

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showRecyclerList(list: List<ItemsItem>) {
        binding.rvGithub.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        binding.rvGithub.adapter = listUserAdapter

        listUserAdapter.setProfileCardClick(object : ProfileCardClick{
            override fun onCardClicked(data: ItemsItem) {
                val moveDataIntent = Intent(this@MainActivity, ProfileUserActivity::class.java)
                moveDataIntent.putExtra(ProfileUserActivity.EXTRA_DATA, data)
                startActivity(moveDataIntent)

            }
        })

    }



    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.show()
        } else {
            binding.progressBar.hide()
            binding.tvMain.visibility = View.GONE
        }

    }


}