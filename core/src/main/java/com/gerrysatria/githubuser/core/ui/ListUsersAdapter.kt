package com.gerrysatria.githubuser.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gerrysatria.githubuser.core.R
import com.gerrysatria.githubuser.core.databinding.UserListItemBinding
import com.gerrysatria.githubuser.core.domain.model.User
import java.util.ArrayList

class ListUsersAdapter : RecyclerView.Adapter<ListUsersAdapter.ListViewHolder>() {

    private var listData = ArrayList<User>()
    var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.user_list_item,parent, false))

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<User>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserListItemBinding.bind(itemView)
        fun bind(data: User){
            with(binding){
                Glide.with(itemView.context)
                    .load(data.avatarUrl)
                    .into(imgUserItem)

                userNameItem.text = data.login
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}