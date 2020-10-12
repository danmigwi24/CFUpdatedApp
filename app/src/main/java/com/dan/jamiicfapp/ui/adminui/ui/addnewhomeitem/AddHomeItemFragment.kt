package com.dan.jamiicfapp.ui.adminui.ui.addnewhomeitem

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.databinding.FragmentAddHomeItemBinding
import com.dan.jamiicfapp.ui.jcahome.ui.home.homeviewmodel.HomeViewModel
import com.dan.jamiicfapp.ui.jcahome.ui.home.homeviewmodel.HomeViewModelFactory
import com.dan.jamiicfapp.utils.*
import kotlinx.android.synthetic.main.fragment_add_home_item.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class AddHomeItemFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory by instance<HomeViewModelFactory>()

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentAddHomeItemBinding

    private var selectedImage: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_home_item, container, false)

        return binding.root
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        imageViewDisability.setOnClickListener {
            selectImage()
        }
        Coroutines.main {
            binding.buttonUpload.setOnClickListener {
                alertDialog()

            }
        }

        super.onActivityCreated(savedInstanceState)
    }

    private fun alertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Thanks For Recording this Case")
        builder.setMessage("If you have not finished please click cancel")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            addCase()

            binding.editTextdisabilityCase.setText("")
            binding.editTextdescription.setText("")
            binding.editTextamountRequired.setText("")
            binding.imageViewDisability.setImageDrawable(null)

            Toast.makeText(
                requireContext(),
                android.R.string.yes, Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                requireContext(),
                android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }

        builder.show()
    }


    private fun addCase() {
        /**
        Start
         */
        if (selectedImage == null) {
            context?.toast("Select an Image first")
        }
        val parcelFileDescriptor =
            requireActivity().contentResolver.openFileDescriptor(selectedImage!!, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
        val file = File(
            requireActivity().cacheDir,
            requireActivity().contentResolver.getFileName(selectedImage!!)
        )
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

//                val body =
//                    UploadRequestBody(
//                        file,
//                        "image"
//                    )
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


        when {
            binding.editTextdisabilityCase.text.toString().isEmpty() -> {
                context?.toast("Please Enter disabilityCase")

            }
            binding.editTextdescription.text.toString().isEmpty() -> {
                context?.toast("Please Enter description")
            }
            binding.editTextamountRequired.text.toString().isEmpty() -> {
                context?.toast("Please Enter amountRequired")
            }
            selectedImage == null -> {
                context?.toast("Select an Image first")
            }

            else -> {
                binding.progressBarAddDataToDisabilityCase.visibility = View.VISIBLE
                lifecycleScope.launch {
                    try {
                        val response = homeViewModel.postCaseVM(
                            disabilityCase,
                            description,
                            amountRequired,
                            image
                        )
                        Log.e("dis", response.message.toString())
                        if (response.isSuccessful) {
                            context?.toast(response.message.toString())
                            binding.progressBarAddDataToDisabilityCase.visibility =
                                View.GONE


                        } else {
                            context?.toast("Please check your details ")
                            binding.progressBarAddDataToDisabilityCase.visibility =
                                View.GONE
                        }

                    } catch (e: APIException) {
                        context?.toast(e.message.toString())
                        Log.e("APIException", e.toString())
                        binding.progressBarAddDataToDisabilityCase.visibility = View.GONE

                    } catch (e: NoInternetException) {
                        context?.toast(e.message.toString())
                        Log.e("NoInternetException", e.toString())
                        binding.progressBarAddDataToDisabilityCase.visibility = View.GONE

                    }
                }
            }
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