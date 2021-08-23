package com.apion.apionhome.ui.home

import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.apion.apionhome.R
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.model.User
import com.apion.apionhome.data.model.dashboard.Banner
import com.apion.apionhome.databinding.FragmentHomeBinding
import com.apion.apionhome.ui.adapter.HouseAdapter
import com.apion.apionhome.ui.adapter.ImageSliderAdapter
import com.apion.apionhome.ui.adapter.UserOnlineAdapter
import com.apion.apionhome.ui.search.SearchViewModel
import com.apion.apionhome.viewmodel.CommunityViewModel
import com.apion.apionhome.viewmodel.HouseViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment :
    BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel by sharedViewModel<HomeViewModel>()

    private val searchViewModel by sharedViewModel<SearchViewModel>()

    private val sliderHandler = Handler(Looper.getMainLooper())

    private val adapterImage = ImageSliderAdapter(::onItemBannerClick)

    private val adapterFeature = HouseAdapter(::onItemHouseClick)

    private val adapterHanoi = HouseAdapter(::onItemHouseClick)

    private val adapterSaiGon = HouseAdapter(::onItemHouseClick)

    private val adapterUserOnline = UserOnlineAdapter(::onItemUserOnlineClick, ::onChatNowClick)

    private var isCheck = false

    private val runnable by lazy {
        Runnable {
            if (isCheck) {
                var current = binding.imageSlider.currentItem
                if (adapterImage.itemCount - 1 == current) {
                    current = 0
                } else {
                    current++
                }
                binding.imageSlider.currentItem = current
            }
        }
    }

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.searchVM = searchViewModel
        binding.homeVM = viewModel
        binding.recyclerViewFeature.adapter = adapterFeature
        binding.recyclerViewHaNoi.adapter = adapterHanoi
        binding.recyclerViewSaiGon.adapter = adapterSaiGon
        binding.pagerUserOnline.adapter = adapterUserOnline
        setupBanner()
        setupListener()
    }

    private fun setupListener() {
        binding.layoutHeader.layoutPrice.setOnClickListener {
            findNavController().navigate(R.id.actionToBottomSheetPriceAcrea)
        }

        binding.layoutHeader.editTextCity.setOnClickListener {
            findNavController().navigate(R.id.actionToSearchProvinceFragment)
        }

        binding.layoutHeader.editTextDistrict.setOnClickListener {
            findNavController().navigate(R.id.actionToSearchDistrictFragment)
        }

        binding.layoutHeader.editTextWard.setOnClickListener {
            if (searchViewModel.district.value != null) {
                findNavController().navigate(R.id.actionToSearchWardFragment)
            } else {
                showToast(getString(R.string.error_select_ward))
            }
        }

        binding.layoutHeader.editTextStreet.setOnClickListener {
            if (searchViewModel.district.value != null) {
                findNavController().navigate(R.id.actionToSearchStreetFragment)
            } else {
                showToast(getString(R.string.error_select_ward))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        isCheck = false
    }

    private fun setupBanner() {
        isCheck = true
        binding.imageSlider.adapter = adapterImage
        TabLayoutMediator(binding.tabLayout, binding.imageSlider) { _, _ ->
        }.attach()
        binding.imageSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(runnable)
                sliderHandler.postDelayed(runnable, 3000)
            }
        })
    }

    private fun onItemBannerClick(banner: Banner) {
        println(banner.link)
    }

    private fun onItemHouseClick(house: House) {
        val destination = HomeFragmentDirections.actionToDetail(house)
        findNavController().navigate(destination)
    }


    private fun onChatNowClick(user: User) {
        println(user)
    }

    private fun onItemUserOnlineClick(user: User) {
        println(user)
    }
}
