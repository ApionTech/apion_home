package com.apion.apionhome.ui.image

import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.databinding.FragmentDetailImageBinding
import com.apion.apionhome.ui.adapter.ImageAdapter
import com.apion.apionhome.ui.home.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailImageFragment :
    BindingFragment<FragmentDetailImageBinding>(FragmentDetailImageBinding::inflate) {

    override val viewModel by sharedViewModel<HomeViewModel>()

    private val adapter = ImageAdapter({})

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.imageDetail.adapter = adapter
        viewModel.dashBoard.observe(this, {
            binding.images = viewModel.getImages()
        })

        binding.imageDetail.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                println("current $position")
                super.onPageSelected(position)
                binding.current = position + 1
            }
        })
    }
}
