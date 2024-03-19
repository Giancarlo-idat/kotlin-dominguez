package com.store.importacionesdominguez

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.store.importacionesdominguez.databinding.ActivityMainBinding
import com.store.importacionesdominguez.utils.preferences.SharedPreferences
import com.store.importacionesdominguez.ui.auth.login.view.OnAuthenticationSuccessListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnAuthenticationSuccessListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initNavigation()
        val isAuthenticated = SharedPreferences.isAuthenticated(this)
        updateMenuVisibility(isAuthenticated)
    }


    private fun initNavigation() {
        val navHost: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }


    // importacionesDominguez2024@gmail.com
    override fun onAuthenticationSuccess() {
        updateMenuVisibility(true)
    }

    fun updateMenuVisibility(isAuthenticated: Boolean) {
        val menu = binding.bottomNavigationView.menu
        if (isAuthenticated) {
            menu.findItem(R.id.profileFragment)?.isVisible = false
            menu.findItem(R.id.menuClientFragment)?.isVisible = true
        } else {
            menu.findItem(R.id.profileFragment)?.isVisible = true
            menu.findItem(R.id.menuClientFragment)?.isVisible = false
        }
    }

}