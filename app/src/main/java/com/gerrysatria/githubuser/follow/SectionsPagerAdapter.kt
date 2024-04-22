package com.gerrysatria.githubuser.follow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gerrysatria.githubuser.util.ARG_POSITION
import com.gerrysatria.githubuser.util.ARG_USERNAME

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username : String = ""

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_POSITION, position + 1)
            putString(ARG_USERNAME, username)
        }
        return fragment
    }
}