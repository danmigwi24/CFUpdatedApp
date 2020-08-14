package com.dan.jamiicfapp.ui.commentui

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
import com.dan.jamiicfapp.databinding.AddcommentFragmentBinding
import com.dan.jamiicfapp.ui.commentui.cviewmodel.CommentViewModel
import com.dan.jamiicfapp.ui.commentui.cviewmodel.CommentViewModelFactory
import com.dan.jamiicfapp.utils.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AddcommentFragment : DialogFragment(), KodeinAware {
    override val kodein by kodein()
    private val factory by instance<CommentViewModelFactory>()
    private lateinit var viewModel: CommentViewModel
    private lateinit var binding: AddcommentFragmentBinding
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.addcomment_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(CommentViewModel::class.java)
        sessionManager = SessionManager(requireContext())

        val pwdId = sessionManager.fetchPwdId().toString()
        val memberId = sessionManager.fetchUserId().toString()

        binding.buttonAddAuthor.setOnClickListener {
            if (binding.editTextComment.text.toString().isEmpty()) {
                binding.editTextComment.error = "Please enter feedback"
            } else {
                Coroutines.main {
                    binding.progressBar3.pshow()
                    try {
                        val res =
                            viewModel.giveCommentVM(
                                pwdId, memberId,
                                binding.editTextComment.text.toString()
                            )
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