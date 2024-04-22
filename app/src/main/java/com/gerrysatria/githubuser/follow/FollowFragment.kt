package com.gerrysatria.githubuser.follow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gerrysatria.githubuser.R
import com.gerrysatria.githubuser.core.data.Resource
import com.gerrysatria.githubuser.core.ui.ListUsersAdapter
import com.gerrysatria.githubuser.databinding.FragmentFollowBinding
import com.gerrysatria.githubuser.detail.DetailActivity
import com.gerrysatria.githubuser.util.ARG_POSITION
import com.gerrysatria.githubuser.util.ARG_USERNAME
import com.gerrysatria.githubuser.util.EXTRA_DATA
import com.gerrysatria.githubuser.util.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowViewModel by viewModel()

    private var position: Int = 0
    private var username: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            val adapter = ListUsersAdapter()
            rvFollow.adapter = adapter
            rvFollow.layoutManager = LinearLayoutManager(requireContext())

            adapter.onItemClick = { selectedUser ->
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(EXTRA_DATA, selectedUser)
                startActivity(intent)
            }

            arguments?.let {
                position = it.getInt(ARG_POSITION, 0)
                username = it.getString(ARG_USERNAME)
            }

            username?.let {
                if (position == 1){
                    viewModel.getListFollowers(it).observe(viewLifecycleOwner){ follower->
                        when(follower){
                            is Resource.Loading -> {
                                progressBarFollow.show(true)
                                rvFollow.show(false)
                                errListFollow.show(false)
                            }
                            is Resource.Success -> {
                                progressBarFollow.show(false)
                                if (follower.data?.isEmpty() == true){
                                    rvFollow.show(false)
                                    errListFollow.show(true)
                                    errListFollow.text = getString(R.string.follower_is_empty)
                                } else {
                                    rvFollow.show(true)
                                    adapter.setData(follower.data)
                                    errListFollow.show(false)
                                }
                            }
                            is Resource.Error -> {
                                progressBarFollow.show(false)
                                rvFollow.show(false)
                                errListFollow.show(true)
                                errListFollow.text = getString(R.string.follower_is_empty)
                            }
                        }
                    }
                } else if(position == 2) {
                    viewModel.getListFollowings(it).observe(viewLifecycleOwner){ following ->
                        when(following){
                            is Resource.Loading -> {
                                progressBarFollow.show(true)
                                rvFollow.show(false)
                                errListFollow.show(false)
                            }
                            is Resource.Success -> {
                                progressBarFollow.show(false)
                                if (following.data?.isEmpty() == true){
                                    rvFollow.show(false)
                                    errListFollow.show(true)
                                    errListFollow.text = getString(R.string.following_is_empty)
                                } else {
                                    rvFollow.show(true)
                                    adapter.setData(following.data)
                                    errListFollow.show(false)
                                }
                            }
                            is Resource.Error -> {
                                progressBarFollow.show(false)
                                rvFollow.show(false)
                                errListFollow.show(true)
                                errListFollow.text = getString(R.string.following_is_empty)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}