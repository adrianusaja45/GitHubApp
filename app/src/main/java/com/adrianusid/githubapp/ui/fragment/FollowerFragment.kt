package com.adrianusid.githubapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianusid.githubapp.Follow
import com.adrianusid.githubapp.FollowAdapter
import com.adrianusid.githubapp.GitViewModel
import com.adrianusid.githubapp.ui.main.ProfileUserActivity
import com.adrianusid.githubapp.databinding.FragmentFollowerBinding


class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private val mainViewModel: GitViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getFollower(ProfileUserActivity.USER, requireContext())
        mainViewModel.follower.observe(viewLifecycleOwner) { follower ->
            showRecyclerList(follower)
        }
        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.show()
        } else {
            binding.progressBar.hide()
        }

    }

    private fun showRecyclerList(list: List<Follow>) {


        binding.rvFollower.layoutManager = LinearLayoutManager(context)
        val adapter = FollowAdapter(list)
        binding.rvFollower.adapter = adapter

    }

}