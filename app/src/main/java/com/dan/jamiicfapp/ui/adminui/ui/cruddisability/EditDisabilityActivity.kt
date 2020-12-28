package com.dan.jamiicfapp.ui.adminui.ui.cruddisability

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.entities.DisabledCaseDetails
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.databinding.ActivityEditdisabilityBinding
import com.dan.jamiicfapp.ui.adminui.AdminActivity
import com.dan.jamiicfapp.ui.adminui.ui.cruddisability.viewmodel.CrudDisabilityViewModel
import com.dan.jamiicfapp.ui.adminui.ui.cruddisability.viewmodel.CrudDisabilityViewModelFactory
import com.dan.jamiicfapp.utils.APIException
import com.dan.jamiicfapp.utils.NoInternetException
import com.dan.jamiicfapp.utils.getFileName
import com.dan.jamiicfapp.utils.toast
import kotlinx.android.synthetic.main.fragment_add_home_item.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class EditDisabilityActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory by instance<CrudDisabilityViewModelFactory>()

    private lateinit var crudDisabilityViewModel: CrudDisabilityViewModel
    private lateinit var sessionManager: SessionManager

    private var selectedImage: Uri? = null

    private lateinit var binding: ActivityEditdisabilityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editdisability)

        crudDisabilityViewModel =
            ViewModelProvider(this, factory).get(CrudDisabilityViewModel::class.java)

        sessionManager = SessionManager(this)

        val detail =
            intent.getParcelableExtra<DisabledCaseDetails>(CrudDisabilityFragment.INTENT_PARCELABLE_CRUD)


        binding.editTextdisabilityCase.setText(detail.disabilityCase)
        binding.editTextdescription.setText(detail.description)
        binding.editTextamountRequired.setText(detail.amount_required)

        //Glide.with(view).load(url).into(view)
        Glide.with(binding.imageViewDisability).load(detail.image_url)
            .into(binding.imageViewDisability)

        binding.imageViewDisability.setOnClickListener {
            selectImage()
        }
        Log.e("edit", sessionManager.fetchDisabilityCaseId().toString())
        binding.buttonUpdate.setOnClickListener {

            /**
            Start
             */
            if (selectedImage == null) {
                toast("Select an Image first")
            }
            val parcelFileDescriptor =
                selectedImage?.let { it1 -> contentResolver.openFileDescriptor(it1, "r", null) }
            val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
            val file = File(
                cacheDir,
                contentResolver.getFileName(selectedImage!!)
            )
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val image =
                MultipartBody.Part.createFormData("image", file.name, requestBody)

            /**
            End
             */
            val disabilityCase = binding.editTextdisabilityCase.text.toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val description = binding.editTextdescription.text.toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val amountRequired = binding.editTextamountRequired.text.toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())
            lifecycleScope.launch {
                try {
                    Log.e("edit", sessionManager.fetchDisabilityCaseId().toString())
                    Log.e("edit", image.toString())
                    if (sessionManager.fetchDisabilityCaseId()!!.toInt() != 0) {
                        val response = crudDisabilityViewModel.updateCaseVM(
                            sessionManager.fetchDisabilityCaseId()!!.toInt(),
                            disabilityCase,
                            description,
                            amountRequired,
                            image
                        )
                        if (response.isSuccessful) {
                            toast(response.message.toString())

                            startActivity(
                                Intent(
                                    this@EditDisabilityActivity,
                                    AdminActivity::class.java
                                )
                            )
                            this@EditDisabilityActivity.finish()
                            Log.e("edit1", response.message.toString())
                        } else {
                            toast("Please try again later ")

                        }
                    } else {
                        toast("Please to reload this page again ")

                    }


                } catch (e: APIException) {
                    toast(e.message.toString())
                    Log.e("APIException", e.toString())


                } catch (e: NoInternetException) {
                    toast(e.message.toString())
                    Log.e("NoInternetException", e.toString())


                }
            }
        }

    }

    private fun selectImage() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeType = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
            startActivityForResult(
                it,
                REQUEST_IMAGE_CODE_PICKER
            )

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CODE_PICKER -> {
                    selectedImage = data?.data
                    imageViewDisability.setImageURI(selectedImage)
                }
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CODE_PICKER = 100
    }
}