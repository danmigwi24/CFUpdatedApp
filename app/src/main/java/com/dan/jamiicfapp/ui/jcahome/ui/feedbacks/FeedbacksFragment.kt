package com.dan.jamiicfapp.ui.jcahome.ui.feedbacks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.databinding.FragmentFeedbacksBinding
import com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.feedbackviewmodel.FeedbackViewModelFactory
import com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.feedbackviewmodel.FeedbacksViewModel
import com.dan.jamiicfapp.utils.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class FeedbacksFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory by instance<FeedbackViewModelFactory>()

    private lateinit var feedbacksViewModel: FeedbacksViewModel
    private lateinit var binding: FragmentFeedbacksBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedbacksViewModel =
            ViewModelProvider(this, factory).get(FeedbacksViewModel::class.java)

        sessionManager = SessionManager(requireContext())

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedbacks, container, false)
        //lifecycleScope.launch {  }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val membersID = sessionManager.fetchUserId()
        Log.e("membersID", membersID.toString())


        binding.buttonSendFeedBack.setOnClickListener {
            if (binding.editTextFeedback.text.toString().isEmpty()) {
                binding.editTextFeedback.error = "Please enter feedback"
            }else{
                Coroutines.main {
                    binding.progressBar2.pshow()
                    try {
                        val res =
                            feedbacksViewModel.giveFeedbackVM(
                                membersID.toString(),
                                binding.editTextFeedback.text.toString()
                            )
                        Log.e("feedback", res.toString())
                        binding.progressBar2.phide()
                        context?.toast(res.message)
                    } catch (e: APIException) {
                        context?.toast(e.message.toString())
                        binding.progressBar2.phide()
                    } catch (e: NoInternetException) {
                        context?.toast(e.message.toString())
                        binding.progressBar2.phide()
                    }

                }
            }

        }


    }
}