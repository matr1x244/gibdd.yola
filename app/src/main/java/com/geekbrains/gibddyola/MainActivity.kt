package com.geekbrains.gibddyola

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
import java.io.File

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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        checkConnection()
        databaseCopy()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity_container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun checkConnection() {
        viewModel.checkConnection()
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(getString(R.string.no_internet_title))
            .setMessage(getString(R.string.no_internet_message))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.no_internet_ok_button)) { _, _ ->
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

    private fun databaseCopy() {
        val copiedFile = File("${this.dataDir}/databases/", "game_questions")
        if (!copiedFile.exists()) {
            this.assets.open("game_questions").use { input ->
                copiedFile.outputStream().use { output ->
                    input.copyTo(output, 1024)
                }
            }
        } else {
            Log.i("", "database already exist")
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
