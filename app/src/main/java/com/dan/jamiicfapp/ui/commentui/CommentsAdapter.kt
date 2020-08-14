package com.dan.jamiicfapp.ui.commentui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.entities.Comment
import com.dan.jamiicfapp.databinding.RecyclerCommentsBinding


class CommentsAdapter(
    private val list: List<Comment>
) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_comments,
            parent,
            false
        )
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.commentBinding.comments = list[position]
    }

    inner class ViewHolder(val commentBinding: RecyclerCommentsBinding) :
        RecyclerView.ViewHolder(commentBinding.root)


}