package com.gerrysatria.githubuser.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat.getParcelableExtra
import com.bumptech.glide.Glide
import com.gerrysatria.githubuser.R
import com.gerrysatria.githubuser.core.data.Resource
import com.gerrysatria.githubuser.core.domain.model.User
import com.gerrysatria.githubuser.databinding.ActivityDetailBinding
import com.gerrysatria.githubuser.follow.SectionsPagerAdapter
import com.gerrysatria.githubuser.util.EXTRA_DATA
import com.gerrysatria.githubuser.util.show
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    private var data: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dataUser = getParcelableExtra(intent, EXTRA_DATA, User::class.java)

        dataUser?.login?.let {
            with(binding){
                viewModel.getDetailUser(it).observe(this@DetailActivity){ detail ->
                    if (detail != null){
                        when(detail){
                            is Resource.Loading -> progressBarDetail.show(true)
                            is Resource.Success -> {
                                progressBarDetail.show(false)
                                supportActionBar?.title = detail.data?.name
                                data = detail.data
                                showDataUser(detail.data)
                            }
                            is Resource.Error -> {
                                progressBarDetail.show(false)
                            }
                        }
                    }
                }

                val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity)
                sectionsPagerAdapter.username = dataUser.login

                binding.viewPager.adapter = sectionsPagerAdapter
                TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()

                viewModel.getFavoriteUserByUsername(dataUser.login).observe(this@DetailActivity) { existingUser ->
                    val isFavorite = existingUser.isNotEmpty()

                    val favoriteDrawableRes = if (isFavorite) R.drawable.ic_favorite_full else R.drawable.ic_favorite_border
                    ivFavorite.setImageDrawable(ContextCompat.getDrawable(ivFavorite.context, favoriteDrawableRes))

                    val favoriteText = if (isFavorite) R.string.remove_from_favorite else R.string.add_to_favorite
                    tvFavorite.text = getString(favoriteText)

                    ivFavorite.setOnClickListener {
                        if (isFavorite) {
                            viewModel.deleteFavorite(dataUser.login)
                        } else {
                            viewModel.insertFavorite(data!!)
                        }
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showDataUser(data: User?){
        if (data != null) {
            with(binding){
                Glide.with(this@DetailActivity)
                    .load(data.avatarUrl)
                    .into(imgUser)

                tvUsername.text = data.login
                tvName.text = data.name
                tvFollowers.text = String.format(getString(R.string.followers_temp), data.follower.toString())
                tvFollowing.text = String.format(getString(R.string.following_temp), data.following.toString())
            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }
}