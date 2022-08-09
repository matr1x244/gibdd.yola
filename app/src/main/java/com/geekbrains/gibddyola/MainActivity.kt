package com.geekbrains.gibddyola

import android.Manifest
import android.animation.ObjectAnimator
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.geekbrains.gibddyola.domain.employee.ControllerOpenFragment
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import com.geekbrains.gibddyola.ui.about.AboutFragment
import com.geekbrains.gibddyola.ui.main.MainFragment

class MainActivity : AppCompatActivity(), ControllerOpenFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSplash()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity_container, MainFragment.newInstance())
                .commitNow()
        }
        checkPermissionsCallPhone()
    }

    fun checkPermissionsCallPhone(): Boolean {
        val call = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        val listPermissions = ArrayList<String>()
        if (call != PackageManager.PERMISSION_GRANTED) {
            listPermissions.add(Manifest.permission.CALL_PHONE)
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
        when (requestCode) {
            1 -> {
                val perms = HashMap<String, Int>()
                perms[Manifest.permission.CALL_PHONE] = PackageManager.PERMISSION_GRANTED
                if (grantResults.isNotEmpty()) {
                    for (ok in permissions.indices) perms[permissions[ok]] = grantResults[ok]
                    if (perms[Manifest.permission.CALL_PHONE] == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        showDialogPhoneCopy(
                            R.string.dialog_call,
                            DialogInterface.OnClickListener { dialog, which ->
                                when (which) {
                                    DialogInterface.BUTTON_POSITIVE -> checkPermissionsCallPhone()
                                    DialogInterface.BUTTON_NEGATIVE -> checkPermissionsCallPhone()
                                }
                            })
                    }
                }
            }
        }
    }

    fun showDialogPhoneCopy(message: Int, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@MainActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .create()
            .show()
    }

    private fun startSplash() {
        val version = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
        if (version) {
            val screen = installSplashScreen()
            screen.setOnExitAnimationListener { screenProvider ->
                ObjectAnimator.ofFloat(
                    screenProvider.view,
                    View.ALPHA, 5f, 0f
                ).apply {
                    duration = 2000
                    doOnEnd {
                        screenProvider.remove()
                    }
                }.start()
            }
        }
    }

    override fun aboutFragment(localClick: EntityAvarkom) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
            .add(R.id.main_activity_container, AboutFragment.newInstance(localClick))
            .addToBackStack("")
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}