package com.geekbrains.gibddyola

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.geekbrains.gibddyola.domain.employee.ControllerOpenFragment
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import com.geekbrains.gibddyola.ui.about.AboutFragment
import com.geekbrains.gibddyola.ui.main.MainFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

private const val MAIN_ACTIVITY_SCOPE_ID = "mainActivityScopeId"

class MainActivity : AppCompatActivity(), ControllerOpenFragment {

    private val scope by lazy {
        getKoin().getOrCreateScope<MainActivity>(MAIN_ACTIVITY_SCOPE_ID)
    }

    private val viewModel: MainActivityViewModel by lazy {
        scope.get(named("mainActivityViewModel"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkConnection()
        if (savedInstanceState == null) {
            startContainerAlphaAnimator()
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity_container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun startContainerAlphaAnimator() {
        ObjectAnimator.ofFloat(findViewById(R.id.main_activity_container), View.ALPHA, 0.2f, 1.0f)
            .setDuration(700)
            .start()
    }

    private fun checkConnection() {
        viewModel.checkConnection()
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(getString(R.string.no_internet_title))
            .setMessage(getString(R.string.no_internet_message))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.no_internet_refresh_button)) { _, _ ->
                val fragment =
                    supportFragmentManager.findFragmentById(R.id.main_activity_container)!!
                supportFragmentManager.beginTransaction()
                    .detach(fragment)
                    .commit()
                supportFragmentManager.beginTransaction()
                    .attach(fragment)
                    .commit()
            }
            .setNegativeButton(getString(R.string.no_internet_close_app)) { _, _ ->
                finish()
            }
        val dialog = dialogBuilder.create()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectionStatus.collect { status ->
                    when (status) {
                        true -> {
                            if (dialog.isShowing) {
                                dialog.dismiss()
                            }
                        }
                        else -> {
                            dialog.show()
                        }
                    }
                }
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