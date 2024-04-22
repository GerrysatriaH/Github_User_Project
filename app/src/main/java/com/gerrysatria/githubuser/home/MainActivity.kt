package com.gerrysatria.githubuser.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.gerrysatria.githubuser.core.data.Resource
import com.gerrysatria.githubuser.core.ui.ListUsersAdapter
import com.gerrysatria.githubuser.databinding.ActivityMainBinding
import com.gerrysatria.githubuser.detail.DetailActivity
import com.gerrysatria.githubuser.util.DEEP_LNK_FAVORITE_MODULE
import com.gerrysatria.githubuser.util.EXTRA_DATA
import com.gerrysatria.githubuser.util.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val adapter = ListUsersAdapter()
        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                if(searchBar.text.isNotEmpty()){
                    viewModel.findUser(searchView.text.toString()).observe(this@MainActivity){ user ->
                        if (user != null){
                            when(user){
                                is Resource.Loading -> {
                                    progressBarList.show(true)
                                    rvUser.show(false)
                                    errListUser.show(false)
                                }
                                is Resource.Success -> {
                                    progressBarList.show(false)
                                    rvUser.show(true)
                                    errListUser.show(false)
                                    adapter.setData(user.data)
                                }
                                is Resource.Error -> {
                                    progressBarList.show(false)
                                    rvUser.show(false)
                                    errListUser.show(true)
                                }
                            }
                        }
                    }
                }
                false
            }

            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = adapter

            viewModel.findUser().observe(this@MainActivity){ user ->
                if (user != null){
                    when(user){
                        is Resource.Loading -> {
                            progressBarList.show(true)
                            rvUser.show(false)
                            errListUser.show(false)
                        }
                        is Resource.Success -> {
                            progressBarList.show(false)
                            rvUser.show(true)
                            errListUser.show(false)
                            adapter.setData(user.data)
                        }
                        is Resource.Error -> {
                            progressBarList.show(false)
                            rvUser.show(false)
                            errListUser.show(true)
                        }
                    }
                }
            }
            toFavorite.setOnClickListener {
                val uri = Uri.parse(DEEP_LNK_FAVORITE_MODULE)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }

        adapter.onItemClick = { selectedUser ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, selectedUser)
            startActivity(intent)
        }
    }
}