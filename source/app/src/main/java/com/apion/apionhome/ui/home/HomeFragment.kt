package com.apion.apionhome.ui.home

import android.app.Activity
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.result.ActivityResult
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.data.model.local.District
import com.apion.apionhome.data.model.local.LocationName
import com.apion.apionhome.data.model.local.Province
import com.apion.apionhome.databinding.FragmentHomeBinding
import com.apion.apionhome.ui.search.SearchLocationBottomSheet
import com.apion.apionhome.ui.search.SearchViewModel
import com.apion.apionhome.utils.getRealPath
import com.apion.apionhome.viewmodel.HouseViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment :
    BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel by sharedViewModel<HomeViewModel>()

    private val houseViewModel by viewModel<HouseViewModel>()

    private val searchViewModel by sharedViewModel<SearchViewModel>()

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.searchVM = searchViewModel
        binding.homeVM = viewModel
        setupListener()
    }

    private val pass = CharArray(4)

    override fun onResume() {
        super.onResume()
        binding.apply {
            textShowPass.setOnClickListener {
                viewModel.setShowPass()
            }
            textInput1.apply {
                requestFocus()
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInput1.setSelection(textInput1.text.length)
                    }
                }
                doOnTextChanged { _, _, _, _ ->
                    if (textInput1.text?.length == 1 && textInput1.text[0] != pass[0]) {
                        println("text ${textInput1.text?.get(0)}")
                        pass[0] = textInput1.text?.get(0) ?: '*'
                        textInput1.setText(pass[0].toString())
                        textInput2.requestFocus()
                    } else if (textInput1.text?.length == 0) {
                        pass[0] = '*'
                    }
                }
            }

            textInput2.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInput2.setSelection(textInput2.text.length)
                    }
                }
                doOnTextChanged { _, _, _, _ ->
                    if (textInput2.text?.length == 1 && textInput2.text[0] != pass[1]) {
                        pass[1] = textInput2.text?.get(0) ?: '*'
                        textInput2.setText(pass[1].toString())
                        textInput3.requestFocus()
                    } else if (textInput2.text?.length == 0) {
                        pass[1] = '*'
                        textInput1.requestFocus()
                    }
                }
            }
            textInput3.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInput3.setSelection(textInput3.text.length)
                    }
                }
                doOnTextChanged { _, _, _, _ ->
                    if (textInput3.text?.length == 1 && textInput3.text[0] != pass[2]) {
                        pass[2] = textInput3.text?.get(0) ?: '*'
                        textInput3.setText(pass[2].toString())
                        textInputDone.requestFocus()
                    } else if (textInput3.text?.length == 0) {
                        pass[2] = '*'
                        textInput2.requestFocus()
                    }
                }
            }
            textInputDone.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        println("lenght ${textInputDone.text.length}")
                        textInputDone.setSelection(textInputDone.text.length)
                    }
                }
                doOnTextChanged { _, _, _, _ ->
                    if (textInputDone.text?.length == 1 && textInputDone.text[0] != pass[3]) {
                        pass[3] = textInputDone.text?.get(0) ?: '*'
                        textInputDone.setText(pass[3].toString())
                        textInputDone.onEditorAction(EditorInfo.IME_ACTION_DONE)
                        println(pass)
                    } else if (textInputDone.text?.length == 0) {
                        pass[3] = '*'
                        textInput3.requestFocus()
                    }
                    textInputDone.setSelection(textInputDone.text.length)
                }
            }
        }
    }

    override fun onPermissionResult(permissions: MutableMap<String, Boolean>) {
        super.onPermissionResult(permissions)
        if (hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            pickImage()
        }
    }

    override fun onActivityResult(result: ActivityResult) {
        super.onActivityResult(result)
        if (result.resultCode == Activity.RESULT_OK) {
            val images = mutableListOf<String>()
            val data = result.data

            if (data?.clipData != null) {
                val count = data.clipData?.itemCount ?: 0
                for (i in 0 until count) {
                    val imageUri = data.clipData?.getItemAt(i)?.uri
                    images.add(imageUri?.getRealPath(requireContext()) ?: "")
                }
            } else {
                val imageUri = data?.data
                images.add(imageUri?.getRealPath(requireContext()) ?: "")
            }
            if (images.isNotEmpty()) {
                println(images)
                houseViewModel.house.value?.let {
                    houseViewModel.updateHouse(images, it)
                }
            }
        }
    }

    private fun setupListener() {
        binding.buttonAddImage.setOnClickListener {
            pickImageSafety()
        }

        binding.buttonDetailHouseImage.setOnClickListener {
            println("click")
            findNavController().navigate(R.id.actionToDetail)
        }

        binding.buttonSearchProvince.setOnClickListener {
            val bottomSheetFragment = SearchLocationBottomSheet<Province>(
                getString(R.string.title_search_province),
                {
                    println("wards ${it.districts}")
                    searchViewModel.setProvince(it)
                }, {
                    searchViewModel.searchProvince(it)
                })
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.buttonSearchDistrict.setOnClickListener {
            val bottomSheetFragment = SearchLocationBottomSheet<District>(
                getString(R.string.title_search_district),
                {
                    println("wards ${it.wards}")
                    searchViewModel.setDistrict(it)
                }, {
                    searchViewModel.searchDistrict(it)
                })
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.buttonSearchWard.setOnClickListener {
            if (searchViewModel.district.value != null) {
                val bottomSheetFragment = SearchLocationBottomSheet<LocationName>(
                    getString(R.string.title_search_ward),
                    {
                        println("tittle ${it.prefix} ${it.name}")
                        searchViewModel.setWard(it)
                    }, {
                        searchViewModel.searchWard(it)
                    })
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            } else {
                showToast("Vui lòng chọn quận, huyện trước")
            }
        }

        binding.buttonSearchStreet.setOnClickListener {
            if (searchViewModel.district.value != null) {
                val bottomSheetFragment = SearchLocationBottomSheet<LocationName>(
                    getString(R.string.title_search_street),
                    {
                        println("title ${it.prefix} ${it.name}")
                        searchViewModel.setStreet(it)
                    }, {
                        searchViewModel.searchStreet(it)
                    })
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            } else {
                showToast("Vui lòng chọn quận, huyện trước")
            }
        }
    }

    private fun pickImageSafety() {
        if (hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            pickImage()
        } else {
            requestPermissionsSafely(arrayListOf(android.Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResultSafely(Intent.createChooser(intent, "Chọn ảnh"))
    }

}
