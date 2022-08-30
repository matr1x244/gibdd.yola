package com.geekbrains.gibddyola

import android.Manifest
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        checkPermissions()
    }

    private fun checkPermissions(): Boolean {
        val call = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        val memory = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

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
                val permisson = HashMap<String, Int>()
                permisson[Manifest.permission.CALL_PHONE] = PackageManager.PERMISSION_GRANTED
                permisson[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                if (grantResults.isNotEmpty()) {
                    for (i in permissions.indices) permisson[permissions[i]] = grantResults[i]

                    /**
                     * тут надо вставлять проверку на версию ?
                     * потому что на API выше 29 сразу уходит в ветку else
                     */

                    /*-----*/
                    if (permisson[Manifest.permission.CALL_PHONE] == PackageManager.PERMISSION_GRANTED && permisson[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "ВЕТКА IF ITS OKKKK", Toast.LENGTH_LONG).show()
                    /*-----*/

                    } else {

                        Toast.makeText(this, "ВЕТКА ELSE NEXT ALERT DIALOG", Toast.LENGTH_SHORT).show()
                        showDialogCopyPermisson(R.string.dialog_permisson,
                            DialogInterface.OnClickListener { _, which ->
                                when (which) {
                                    DialogInterface.BUTTON_POSITIVE -> checkPermissions()
                                }
                            })
                        }
                    }
                }
            }
         }




    private fun showDialogCopyPermisson(message: Int, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
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
            .setCustomAnimations(
                R.anim.to_left_in, R.anim.to_left_out, R.anim.to_right_in, R.anim.to_right_out
            )
            .replace(R.id.main_activity_container, AboutFragment.newInstance(localClick))
            .addToBackStack("")
            .commit()
    }
}