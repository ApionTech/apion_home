package com.apion.apionhome.ui.search

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.apion.apionhome.base.BindingFragmentBottomSheet
import com.apion.apionhome.data.model.local.ILocation
import com.apion.apionhome.databinding.BottomsheetSearchLocationBinding
import com.apion.apionhome.ui.adapter.SearchLocationAdapter
import com.apion.apionhome.utils.RxSearchView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit

class SearchLocationBottomSheet<L : ILocation>(
    private val hint: String,
    private val itemClick: (L) -> Unit,
    private val onSearch: (String) -> Unit
) :
    BindingFragmentBottomSheet<BottomsheetSearchLocationBinding>(BottomsheetSearchLocationBinding::inflate) {

    override val viewModel by sharedViewModel<SearchViewModel>()

    private val suggestionsAdapter = SearchLocationAdapter(::onItemClick)

    private var disposeable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread(
            Runnable {
                onSearch("")
            }
        ).start()

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.searchViewModel = viewModel
        binding.recyclerView.adapter = suggestionsAdapter
        setUpSearchView()
    }

    override fun onDestroy() {
        println("on destroy")
        super.onDestroy()
        viewModel.clearSearch()
        disposeable?.dispose()
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun onItemClick(item: L) {
        itemClick(item)
        dismiss()
    }

    private fun setUpSearchView() {
        binding.apply {
            disposeable = RxSearchView.fromSearchView(search) {
                search.setQuery("", true)
                search.clearFocus()

            }
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onSearch(it)
                }
        }
    }
}
