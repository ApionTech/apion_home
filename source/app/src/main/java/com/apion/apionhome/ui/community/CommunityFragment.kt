package com.apion.apionhome.ui.community

import com.apion.apionhome.MyApplication
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.data.model.House
import com.apion.apionhome.data.model.community.Community
import com.apion.apionhome.data.model.community.Participant
import com.apion.apionhome.databinding.FragmentCommunityBinding
import com.apion.apionhome.ui.adapter.CommunityAdapter
import com.apion.apionhome.ui.adapter.PostCommunityAdapter
import com.apion.apionhome.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CommunityFragment :
    BindingFragment<FragmentCommunityBinding>(FragmentCommunityBinding::inflate) {

    override val viewModel by sharedViewModel<HomeViewModel>()

    private val postCommunityAdapter = PostCommunityAdapter(::onItemPostClick)

    private val communityAdapter = CommunityAdapter(::onItemCommunityClick, ::onButtonJoinClick)


    override fun setupView() {
        binding.lifecycleOwner = this
        binding.homeVM = viewModel
        binding.yourGroups =
            MyApplication.sessionUser.value?.participants ?: emptyList<Participant>()
        binding.recyclerViewFeatureCommunity.adapter = postCommunityAdapter
        binding.recyclerViewCommunity.adapter = communityAdapter
    }

    private fun onItemPostClick(house: House) {
        println(house)
    }

    private fun onItemCommunityClick(community: Community) {
        println(community)
    }

    private fun onButtonJoinClick(isJoinCommunity: Boolean) {
        println(isJoinCommunity)
    }
}
