package com.aanyashukla.recordkeeperapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.commit
import com.aanyashukla.recordkeeperapp.cycling.CyclingFragment
import com.aanyashukla.recordkeeperapp.databinding.ActivityMainBinding
import com.aanyashukla.recordkeeperapp.running.RunningFragment
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.google.android.material.snackbar.Snackbar

const val RUNNING = "running"
const val CYCLING = "cycling"
const val ALL = "all"

class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuClickHandled = when (item.itemId) {
            R.id.reset_running -> {
                showConfirmationDialog(RUNNING)
                true
            }
            R.id.reset_cycling -> {
                showConfirmationDialog(CYCLING)
                true
            }
            R.id.reset_all -> {
                showConfirmationDialog(ALL)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return menuClickHandled
    }

    private fun showConfirmationDialog(selection: String) {
        AlertDialog.Builder(this)
            .setTitle("Reset $selection records")
            .setMessage("Are you sure you want to clear the records?")
            .setPositiveButton("Yes") { _, _ ->
                when(selection){
                    ALL -> {
                        getSharedPreferences(RUNNING, MODE_PRIVATE).edit { clear() }
                        getSharedPreferences(CYCLING, MODE_PRIVATE).edit { clear() }
                    }
                    else -> getSharedPreferences(selection, MODE_PRIVATE).edit { clear() }
                }
                refreshCurrentFragment()
                showConfirmation()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun showConfirmation() {
        val snackbar = Snackbar.make(binding.frameContent, "Records cleared successfully!", Snackbar.LENGTH_LONG)
        snackbar.anchorView = binding.bottomNav
        snackbar.show()
    }

    private fun refreshCurrentFragment() {
        when (binding.bottomNav.selectedItemId) {
            R.id.nav_running -> onRunningClicked()
            R.id.nav_cycling -> onCyclingClicked()
            else -> {}
        }
    }

//    override fun onClick(v: View?) {
//        if(v?.id == R.id.button_running){
//            Toast.makeText(this, "I have clicked on the Running button", Toast.LENGTH_LONG).show()
//        }
//        if(v?.id == R.id.button_cycling){
//            Toast.makeText(this, "I have clicked on the Cycling button", Toast.LENGTH_LONG).show()
//        }
//    }
    // This can be done but not a very ideal approach as we get quiet a big onclick method which will get bigger and bigger the more buttons we get.

    private fun onCyclingClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CyclingFragment())
        }
    }

    private fun onRunningClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_content, RunningFragment())
        }
    }

    override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.nav_cycling -> {
            onCyclingClicked()
            true
        }
        R.id.nav_running -> {
            onRunningClicked()
            true
        }
        else -> false
    }
}