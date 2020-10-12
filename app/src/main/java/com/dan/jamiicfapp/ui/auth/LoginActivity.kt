package com.dan.jamiicfapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.databinding.ActivityLoginBinding
import com.dan.jamiicfapp.ui.adminui.AdminActivity
import com.dan.jamiicfapp.ui.auth.viewmodel.AuthViewModel
import com.dan.jamiicfapp.ui.auth.viewmodel.AuthViewModelFactory
import com.dan.jamiicfapp.ui.jcahome.HomeActivity
import com.dan.jamiicfapp.utils.APIException
import com.dan.jamiicfapp.utils.NoInternetException
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    val factory by instance<AuthViewModelFactory>()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var loginViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        sessionManager = SessionManager(this)
        loginViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        loginViewModel.getUserVmRoom().observe(this, Observer { user ->
            if (user != null) {
               /* Intent(this@LoginActivity, HomeActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(this)
                }*/
                sessionManager.saveUserId(user.id.toString())
                sessionManager.savePhoneNumber(user.phonenumber)
                Log.e(
                    "phonenumber", user.phonenumber
                )

            }

        })


        binding.buttonLogin.setOnClickListener {
            editTextChangedListener()
            Log.e("phn", binding.editTextPhonenumber.text.toString())
            loginUser()
        }

        binding.buttonLoginadmin.setOnClickListener {
            val phonenumber = binding.editTextPhonenumber.text.toString()
            if (phonenumber.isEmpty()) {
                binding.editTextPhonenumber.error = "Please enter valid number"
            }
            val length = binding.editTextPin.text.length
            if (binding.editTextPin.text.toString().isEmpty() && length != 4) {
                binding.editTextPin.error = "Please enter valid number"
            }
            if (binding.editTextPin.text.toString() == "1234" && binding.editTextPhonenumber.text.toString() == "123456789"
            ) {
                startActivity(Intent(this@LoginActivity, AdminActivity::class.java))
            }
        }
    }

    private fun loginUser() {

        Log.e("phn", binding.editTextPhonenumber.text.toString())
        val phonenumber = ccp.selectedCountryCode + binding.editTextPhonenumber.text.toString()
        Log.e("fdfff", phonenumber)

        if (phonenumber.isEmpty() && phonenumber == "254") {
            binding.editTextPhonenumber.error = "Please enter valid number"
        }
        val length = binding.editTextPin.text.length
        if (binding.editTextPin.text.toString().isEmpty() && length != 4) {
            binding.editTextPin.error = "Please enter valid number"
        } else {
            progressBar.visibility = View.VISIBLE
            lifecycleScope.launch {
                try {
                    val response = loginViewModel.loginUser(
                        phonenumber,
                        binding.editTextPin.text.toString().trim()
                    )
                    //sessionManager.savePhoneNumber(binding.editTextPhonenumber.text.toString())
                    if (response.user != null) {
                        loginViewModel.saveUserVmRoom(response.user)
                        progressBar.visibility = View.GONE
                        Intent(this@LoginActivity, HomeActivity::class.java).apply {
                            startActivity(this)
                        }
                    } else {
                        toast(response.message.toString())
                        Log.e(
                            "response is ${response.success.toString()}",
                            response.message.toString()
                        )
                        Log.e("Exception", phonenumber)
                        progressBar.visibility = View.GONE
                    }
                    sessionManager.savePhoneNumber(
                        ccp.selectedCountryCode + binding.editTextPhonenumber.text.toString().trim()
                    )

                } catch (e: APIException) {
                    toast(e.toString())
                    progressBar.visibility = View.GONE
                    Log.e("Exception", phonenumber)
                    Log.e("Exception", e.toString())
                } catch (e: NoInternetException) {
                    toast(e.toString())
                    progressBar.visibility = View.GONE
                }
            }
        }


    }

    private fun editTextChangedListener() {
        binding.editTextPhonenumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().length == 10 && s.toString().startsWith("0")) {
                    s.clear()
                }
            }
        })
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}