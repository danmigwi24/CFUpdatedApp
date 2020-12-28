package com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.fetchfeedback

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.databinding.AddfeedbackLayoutFragmentBinding
import com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.feedbackviewmodel.FeedbackViewModelFactory
import com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.feedbackviewmodel.FeedbacksViewModel
import com.dan.jamiicfapp.utils.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AddFeebackFragment : DialogFragment(), KodeinAware {
    override val kodein by kodein()
    private val factory by instance<FeedbackViewModelFactory>()
    private lateinit var viewModel: FeedbacksViewModel
    private lateinit var binding: AddfeedbackLayoutFragmentBinding
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.addfeedback_layout_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(FeedbacksViewModel::class.java)
        sessionManager = SessionManager(requireContext())

        val memberId = sessionManager.fetchUserId().toString()

        binding.buttonAddAuthor.setOnClickListener {
            if (binding.editTextComment.text.toString().isEmpty()) {
                binding.editTextComment.error = "Please enter feedback"
            } else {
                Coroutines.main {
                    binding.progressBar3.pshow()
                    try {
                        val res =
                            viewModel.giveFeedbackVM(
                                memberId,
                                binding.editTextComment.text.toString()
                            )
                        context?.startActivity(Intent(activity, FetchFeedbackActivity::class.java))
                        requireActivity().finish()
                        Log.e("feedback", res.toString())
                        binding.progressBar3.phide()
                        dismiss()
                        context?.toast(res.message)
                    } catch (e: APIException) {
                        binding.progressBar3.phide()
                        context?.toast(e.message.toString())

                    } catch (e: NoInternetException) {
                        binding.progressBar3.phide()
                        context?.toast(e.message.toString())
                    }

                }
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

}