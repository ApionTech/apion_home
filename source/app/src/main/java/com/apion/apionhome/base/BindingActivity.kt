package com.apion.apionhome.base

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.apion.apionhome.R
import com.apion.apionhome.utils.createProgressDialog
import com.apion.apionhome.utils.showToast

abstract class BindingActivity<T : ViewDataBinding> : AppCompatActivity() {
    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected abstract val viewModel: RxViewModel?

    protected val binding: T
        get() = _binding ?: throw IllegalStateException(EXCEPTION)

    private var _binding: T? = null

    private val progressDialog by lazy {
        this.createProgressDialog()
    }

    private var exceptionDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, getLayoutResId())
        viewModel?.errorException?.observe(this, {
            showToast(it)
        })
        setupView()
        try {
            val builder = NetworkRequest.Builder()
            (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .registerNetworkCallback(builder.build(), object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        onConnection()
                    }

                    override fun onLost(network: Network) {
                        onDisConnection()
                    }
                }
                )
        } catch (e: Exception) {
        }
    }

    open fun onConnection() {
        exceptionDialog?.let {
            if (it.isShowing) it.dismiss()
        }
    }

    open fun onDisConnection() {
        exceptionDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.tittle_exception_connect))
            .setMessage(getString(R.string.message_exception_connect))
            .setPositiveButton(getString(R.string.tittle_retry)) { _, _ ->
            }.setNegativeButton(getString(R.string.tittle_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()
        exceptionDialog?.show()
    }

    fun showProgress() {
        progressDialog.show()
    }

    fun dismissProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: List<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions.filter {
                !hasPermission(it)
            }.toTypedArray().apply {
                if (this.isNotEmpty()) {
                    requestPermissions(this, requestCode)
                }
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
    }

    abstract fun setupView()

    companion object {
        private const val EXCEPTION = "Binding only is valid after onCreate"
    }
}
