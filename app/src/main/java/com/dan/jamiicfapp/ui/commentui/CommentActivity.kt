package com.dan.jamiicfapp.ui.commentui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.databinding.ActivityCommentBinding
import com.dan.jamiicfapp.ui.commentui.cviewmodel.CommentViewModel
import com.dan.jamiicfapp.ui.commentui.cviewmodel.CommentViewModelFactory
import com.dan.jamiicfapp.utils.Coroutines
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class CommentActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory by instance<CommentViewModelFactory>()
    private lateinit var binding: ActivityCommentBinding
    private lateinit var viewModel: CommentViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var commentsAdapter: CommentsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment)
        viewModel = ViewModelProvider(this, factory).get(CommentViewModel::class.java)


        setSupportActionBar(binding.toolbar)

        binding.floatingActionbutton.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            AddcommentFragment().show(transaction, "SOME_TAG")
        }

        Coroutines.main {
            val liveDataComments = viewModel.getCommentsInVM.await()
            liveDataComments.observe(this@CommentActivity, Observer {
                Log.e("HellowInn",it.toString())
                commentsAdapter = CommentsAdapter(it)
                binding.recyclerViewComments.apply {
                    layoutManager = LinearLayoutManager(this@CommentActivity)
                    setHasFixedSize(true)
                    adapter = commentsAdapter
                }
            })
        }

    }
}