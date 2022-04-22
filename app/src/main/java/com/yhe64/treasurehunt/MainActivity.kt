package com.yhe64.treasurehunt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.yhe64.treasurehunt.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener{_, destination,_ ->
            supportActionBar?.let {
                it.title = when(destination.id){
                    R.id.settingFragment -> getString(R.string.settings)
                    else -> getString(R.string.app_name)
                }
            }
        }
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_go_setting -> {
                navHostFragment.navController.navigate(R.id.action_mainFragment_to_settingFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}