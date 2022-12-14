package com.geekbrains.gibddyola.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class CheckCallPermission(private val activity: FragmentActivity) {

    fun showPermissionDialog() {
        if (checkPermissions()) {
            CallIntent.start(activity)
        } else {
            requestPermission()
            if (checkPermissions()) {
                CallIntent.start(activity)
            } else {
                val builder = AlertDialog.Builder(activity)
                builder.setOnDismissListener {
                    if (checkPermissions()) {
                        CallIntent.start(activity)
                    }
                }
                builder.setTitle("Необходимо разрешение")
                    .setMessage("Для вызова аварийного комиссара необходимо разрешение на осуществление звонков")
                    .setCancelable(false)
                    .setPositiveButton("OK") { _, _ ->
                        requestPermission()
                    }
                    .create()
                    .show()

            }

        }
    }

    private fun checkPermissions(): Boolean {
        val call =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
        return call == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        val permissions = ArrayList<String>()
        permissions.add(Manifest.permission.CALL_PHONE)
        ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), 1)
    }
}