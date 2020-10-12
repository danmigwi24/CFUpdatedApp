package com.dan.jamiicfapp.ui.auth


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.databinding.ActivityRegisterBinding

import com.dan.jamiicfapp.ui.auth.viewmodel.AuthViewModel
import com.dan.jamiicfapp.ui.auth.viewmodel.AuthViewModelFactory
import com.dan.jamiicfapp.utils.APIException
import com.dan.jamiicfapp.utils.NoInternetException
import com.dan.jamiicfapp.utils.isEmailValid
import com.dan.jamiicfapp.utils.toast
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class RegisterActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory by instance<AuthViewModelFactory>()
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


//        viewModel.getUserVmRoom().observe(this, Observer {
//            if (it != null) {
//                Intent(this, LoginActivity::class.java).apply {
//                    startActivity(this)
//                }
//            }
//        })

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {

        val surname = binding.editTextSurname.text.toString().trim()
        val name = binding.editTextName.text.toString().trim()
        val phonenumber = binding.ccp.selectedCountryCode + binding.editTextPhonenumber.text.toString()
        val address = binding.editTextAddress.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString()
        val password_confirmation = binding.editTextPasswordConfirmation.text.toString()
        if (name.isEmpty()) {
            toast("Please Enter Name")
            return
        } else if (!email.isEmailValid()) {
            toast("Please Enter valid Email")
            return
        } else if (password.isEmpty()) {
            toast("Password  is required")
            return
        } else if (password != password_confirmation) {
            toast("The password confirmation does not match")
            return
        } else {
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch {
                try {
                    val authResponse =
                        viewModel.registerUser(
                            surname,
                            name,
                            phonenumber,
                            address,
                            email,
                            password,
                            password_confirmation
                        )
                    if (authResponse.user != null) {
                        viewModel.saveUserVmRoom(authResponse.user)
                        Intent(this@RegisterActivity, LoginActivity::class.java).apply {
                            startActivity(this)
                        }
                        toast(authResponse.message.toString())

                        Log.e("YesisSuccessful", authResponse.message.toString())
                        binding.progressBar.visibility = View.GONE
                    } else {
                        toast(authResponse.message!!)
                        Log.e("NotisSuccessful", authResponse.message)
                        binding.progressBar.visibility = View.GONE
                    }
                } catch (e: APIException) {
                    toast(e.toString())
                    Log.e("APIException", e.toString())
                    binding.progressBar.visibility = View.GONE

                } catch (e: NoInternetException) {
                    toast(e.toString())
                    Log.e("NoInternetException", e.toString())
                    binding.progressBar.visibility = View.GONE

                }
            }
        }


    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("editTextString1", binding.editTextSurname.text.toString())
        savedInstanceState.putString("editTextString2", binding.editTextName.text.toString())
        savedInstanceState.putString("editTextString3", binding.editTextPhonenumber.text.toString())
        savedInstanceState.putString("editTextString4", binding.editTextAddress.text.toString())
        savedInstanceState.putString("editTextString5", binding.editTextEmail.text.toString())
        savedInstanceState.putString("editTextString6", binding.editTextPassword.text.toString())
        savedInstanceState.putString(
            "editTextString7",
            binding.editTextPasswordConfirmation.text.toString()
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.editTextSurname.setText(
            savedInstanceState.getString("editTextString1"),
            TextView.BufferType.EDITABLE
        )
        binding.editTextName.setText(
            savedInstanceState.getString("editTextString2"),
            TextView.BufferType.EDITABLE
        )
        binding.editTextPhonenumber.setText(
            savedInstanceState.getString("editTextString3"),
            TextView.BufferType.EDITABLE
        )
        binding.editTextAddress.setText(
            savedInstanceState.getString("editTextString4"),
            TextView.BufferType.EDITABLE
        )
        binding.editTextEmail.setText(
            savedInstanceState.getString("editTextString5"),
            TextView.BufferType.EDITABLE
        )
        binding.editTextPassword.setText(
            savedInstanceState.getString("editTextString6"),
            TextView.BufferType.EDITABLE
        )
    }


}