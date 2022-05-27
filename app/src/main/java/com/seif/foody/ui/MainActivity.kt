package com.seif.foody.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.seif.foody.R
import com.seif.foody.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Foody)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_drawer)

        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration.Builder(
            setOf(
                R.id.recipeFragment,
                R.id.favouriteRecipeFragment,
                R.id.foodJokeFragment,
            )
        ).setOpenableLayout(drawerLayout)
            .build()


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.navDrawer.setNavigationItemSelectedListener(this)
        binding.bottomNavigationView.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> shareApp()
            R.id.menu_rate -> rateApp()
            R.id.menu_review -> reviewApp()
            R.id.menu_our_apps -> ourApps()
            R.id.menu_about -> aboutDeveloper()
        }
        binding.drawerLayout.close()
        return true
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isOpen){
            binding.drawerLayout.close()
        }
        else{
            super.onBackPressed()
        }
    }

    private fun shareApp() {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Download Todo It app from here:\n" +
                    "https://play.google.com/store/apps/details?id=com.seif.foody"
        )
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, "Choose the app you want to share with:"))
    }

    private fun rateApp() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "market://details?id=com.seif.foody"
                )
            )
        )
    }

    private fun reviewApp() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "mailto:" + "seifeldinmohamed101@gmail.com"
                            + "?subject=" + "Message from Foody"
                )
            )
        )
    }

    private fun ourApps() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "https://play.google.com/store/apps/dev?id=8697500693164992079"
                )
            )
        )
    }

    private fun aboutDeveloper() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.about_dialog)
        val btnOk = dialog.findViewById<Button>(R.id.btn_ok_about)
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}