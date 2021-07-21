package com.udacity.shoestore

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: StoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.welcomeFragment,
            R.id.loginFragment,
            R.id.shoeListFragment,
            R.id.shoeDetailFragment
        ).build()
        navController = findNavController(R.id.fragment_navigation)

        viewModel.user.observe(this){ user ->
            if (user.email != "" || user.password == ""){
                navController.navigate(R.id.loginFragment)
            }
        }

        navController.addOnDestinationChangedListener{ nc: NavController, nd: NavDestination, args: Bundle? ->
            checkNavigation(nd)
        }
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    private fun checkNavigation(nd: NavDestination){
        when(nd.id){
            R.id.loginFragment,
            R.id.shoeDetailFragment,
            R.id.welcomeFragment-> supportActionBar?.hide()
            else -> supportActionBar?.show()
        }
        invalidateOptionsMenu()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        if (menu != null) {
            val currentDestinationId = navController.currentDestination?.id
            val logoutMenu = menu.findItem(R.id.logout_button)
            when (currentDestinationId) {
                R.id.loginFragment,
                R.id.welcomeFragment,
                R.id.instructionFragment,
                R.id.shoeDetailFragment ->
                    logoutMenu?.isVisible = false
                else -> logoutMenu?.isVisible = true
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout_button -> viewModel.logout()
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

}
