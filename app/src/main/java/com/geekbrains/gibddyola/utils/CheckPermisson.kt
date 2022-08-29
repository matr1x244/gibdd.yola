package com.geekbrains.gibddyola.utils

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.geekbrains.gibddyola.R

class CheckPermisson() : AppCompatActivity() {

    fun checkPermissions(): Boolean {
        val call = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        val memory =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val listPermissions = ArrayList<String>()
        if (call != PackageManager.PERMISSION_GRANTED || memory != PackageManager.PERMISSION_GRANTED) {
            listPermissions.add(Manifest.permission.CALL_PHONE)
            listPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (listPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissions.toTypedArray(), 1)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                val perms = HashMap<String, Int>()
                perms[Manifest.permission.CALL_PHONE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] =
                    PackageManager.PERMISSION_GRANTED
                if (grantResults.isNotEmpty()) {
                    for (ok in permissions.indices) perms[permissions[ok]] = grantResults[ok]
                    if (!(perms[Manifest.permission.CALL_PHONE] != PackageManager.PERMISSION_GRANTED || perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] != PackageManager.PERMISSION_GRANTED)) {
                    } else {
                        showDialogCopyPermisson(
                            R.string.dialog_permisson,
                            DialogInterface.OnClickListener { dialog, which ->
                                when (which) {
                                    DialogInterface.BUTTON_POSITIVE -> checkPermissions()
                                    DialogInterface.BUTTON_NEGATIVE -> checkPermissions()
                                }
                            })
                    }
                }
            }
        }
    }

    fun showDialogCopyPermisson(message: Int, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .create()
            .show()
    }
}