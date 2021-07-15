package com.apion.apionhome.base

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.apion.apionhome.R
import com.apion.apionhome.utils.showToast


abstract class BindingFragment<T : ViewDataBinding> : Fragment() {
    @LayoutRes
    abstract fun getLayoutResId(): Int

    abstract val viewModel: RxViewModel

    private var _binding: T? = null

    protected val binding: T
        get() = _binding ?: throw IllegalStateException(EXCEPTION)

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            onPermissionResult(permissions)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil
            .inflate<T>(inflater, getLayoutResId(), container, false)
            .apply { _binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorException.observe(viewLifecycleOwner, {
            showToast(getString(R.string.default_error))
        })
        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    open fun onPermissionResult(permissions: MutableMap<String, Boolean>) {
        permissions.entries.forEach {
            Log.e("DEBUG", "${it.key} = ${it.value}")
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: List<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions.filter {
                !hasPermission(it)
            }.toTypedArray().apply {
                requestMultiplePermissions.launch(this)
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
    }

    fun showToast(msg: String) = requireContext().showToast(msg)

    fun showDialog(
        tittle: String,
        content: String,
        onPositive: (DialogInterface) -> Unit,
        onNegative: (DialogInterface) -> Unit
    ) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(tittle)
        dialog.setMessage(content)
        dialog.setPositiveButton(getString(R.string.tittle_button_agree)) { dialogShow, _ ->
            onPositive(dialogShow)
        }

        dialog.setNegativeButton(getString(R.string.tittle_button_cancel)) { dialogShow, _ ->
            onNegative(dialogShow)
        }
        dialog.show()
    }

    abstract fun setupView()

    companion object {
        private const val EXCEPTION = "Binding only is valid after onCreateView"
    }
}
