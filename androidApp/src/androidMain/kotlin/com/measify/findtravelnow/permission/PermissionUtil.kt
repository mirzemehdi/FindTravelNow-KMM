package com.measify.findtravelnow.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

fun Context.hasPermission(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED


fun ComponentActivity.permissionUtil(): Lazy<PermissionUtil> = lazy(LazyThreadSafetyMode.NONE) {
    PermissionUtil(this)
}

class PermissionUtil(private val activity: ComponentActivity) {

    private var mOnResult: ((Boolean) -> Unit)? = null

    private val requestPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            mOnResult?.invoke(isGranted)
        }

    fun askPermission(permission: String, onResult: (Boolean) -> Unit) {
        askIfNotHasPermission(permission = permission,onResult=onResult)
    }

    fun askNotificationPermission(onResult: (Boolean) -> Unit = {}) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            askIfNotHasPermission(permission = permission, onResult = onResult)
        } else onResult(true)
    }


    private fun askIfNotHasPermission(permission: String, onResult: (Boolean) -> Unit = {}) {
        if (activity.hasPermission(permission)) {
            onResult(true)
        } else {
            mOnResult = onResult
            requestPermissionLauncher.launch(permission)
        }
    }


}