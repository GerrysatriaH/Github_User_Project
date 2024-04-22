package com.gerrysatria.githubuser.favorite.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gerrysatria.githubuser.core.ui.ListUsersAdapter
import com.gerrysatria.githubuser.R
import com.gerrysatria.githubuser.detail.DetailActivity
import com.gerrysatria.githubuser.favorite.databinding.ActivityFavoriteBinding
import com.gerrysatria.githubuser.favorite.di.favoriteModule
import com.gerrysatria.githubuser.util.EXTRA_DATA
import com.gerrysatria.githubuser.util.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(favoriteModule)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite_user)

        val adapter = ListUsersAdapter()
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)

        viewModel.favoriteUsers.observe(this){ list ->
            if (list.isNotEmpty()){
                binding.rvFavorite.show(true)
                binding.errFavoriteUser.show(false)
                adapter.setData(list)
            } else {
                binding.rvFavorite.show(false)
                binding.errFavoriteUser.show(true)
            }
        }

        adapter.onItemClick = { selectedUser ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, selectedUser)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}