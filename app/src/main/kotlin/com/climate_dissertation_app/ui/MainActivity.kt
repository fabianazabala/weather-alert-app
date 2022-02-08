package com.climate_dissertation_app.ui


import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.climate_dissertation_app.R
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureNavigationDrawer()
        weatherFragment()
    }

    private fun configureNavigationDrawer() {
        toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar_main,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer_layout.addDrawerListener(
            toggle
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.weather_item -> weatherFragment()
        R.id.settings_item -> settingsFragment()
        else -> {
            throw NotImplementedError("Yo dude implement me pls")
        }
    }

    private fun settingsFragment(): Boolean {
        return true
    }

    private fun weatherFragment(): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(fragment_placeholder.id, CurrentRecommendationFragment())
        transaction.commit()
        return true
    }
}