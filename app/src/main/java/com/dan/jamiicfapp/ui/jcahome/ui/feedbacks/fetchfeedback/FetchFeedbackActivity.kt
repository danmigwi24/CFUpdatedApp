package com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.fetchfeedback

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
import com.dan.jamiicfapp.databinding.ActivityFetchFeedbackBinding
import com.dan.jamiicfapp.ui.commentui.AddcommentFragment
import com.dan.jamiicfapp.ui.commentui.cviewmodel.CommentViewModel
import com.dan.jamiicfapp.ui.commentui.cviewmodel.CommentViewModelFactory
import com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.feedbackviewmodel.FeedbackViewModelFactory
import com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.feedbackviewmodel.FeedbacksViewModel
import com.dan.jamiicfapp.utils.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class FetchFeedbackActivity : AppCompatActivity(), KodeinAware {

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    override val kodein by kodein()
    private val factory by instance<FeedbackViewModelFactory>()
    private lateinit var binding: ActivityFetchFeedbackBinding
    private lateinit var viewModel: FeedbacksViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var commentsAdapter: FetchFeedbackAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fetch_feedback)
        viewModel = ViewModelProvider(this, factory).get(FeedbacksViewModel::class.java)


        setSupportActionBar(binding.toolbar)

//        binding.floatingActionbutton.setOnClickListener {
//            val transaction = supportFragmentManager.beginTransaction()
//            AddFeebackFragment().show(transaction, "SOME_TAG")
//        }
        try {
            viewModel.fetchFeedback()
            viewModel.fetchFeedbackInVM.observe(this, Observer { feedback ->
                if (feedback.isNotEmpty()) {
                    Log.e("HellowInn", feedback.toString())
                    commentsAdapter = FetchFeedbackAdapter(feedback)
                    binding.recyclerViewComments.apply {
                        layoutManager = LinearLayoutManager(this@FetchFeedbackActivity)
                        setHasFixedSize(true)
                        adapter = commentsAdapter
                    }
                } else {
                    binding.commentsProgressBar.pshow()
                }

            })
        } catch (e: APIException) {
            binding.commentsProgressBar.phide()
            toast(e.message.toString())
        } catch (e: NoInternetException) {
            binding.commentsProgressBar.phide()
            toast(e.message.toString())
        }

        /* Coroutines.main {
             val liveDataComments = viewModel.getCommentsInVM.await()
             liveDataComments.observe(this@CommentActivity, Observer {
                 Log.e("HellowInn", it.toString())
                 commentsAdapter = CommentsAdapter(it)
                 binding.recyclerViewComments.apply {
                     layoutManager = LinearLayoutManager(this@CommentActivity)
                     setHasFixedSize(true)
                     adapter = commentsAdapter
                 }
             })
         }*/

    }
}