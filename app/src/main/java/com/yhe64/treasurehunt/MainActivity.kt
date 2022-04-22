package com.yhe64.treasurehunt

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.yhe64.treasurehunt.ui.main.MainFragment
import androidx.preference.PreferenceManager
import com.yhe64.treasurehunt.TreasurehuntApp.Companion.SHOW_MESSAGE_AT_START
import com.yhe64.treasurehunt.TreasurehuntApp.Companion.CARD_COLOR
import com.yhe64.treasurehunt.TreasurehuntApp.Companion.SHOW_POINT_SCORE

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Treasurehunt)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener{_, destination,_ ->
            supportActionBar?.let {
                it.title = when(destination.id){
                    R.id.settingFragment -> getString(R.string.settings)
                    R.id.infoFragment -> getString(R.string.info)
                    else -> getString(R.string.app_name)
                }
            }
        }

        if (savedInstanceState == null){
            if (prefs.getBoolean(SHOW_MESSAGE_AT_START, false)){
                welcomeAlert()
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
            R.id.action_go_info ->{
                navHostFragment.navController.navigate(R.id.action_mainFragment_to_infoFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun welcomeAlert(){
        val msg = resources.getString(R.string.welcomeMsg)
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle(R.string.welcome)
            setMessage(msg)
            setPositiveButton(R.string.ok,null)
            show()
        }
    }
}