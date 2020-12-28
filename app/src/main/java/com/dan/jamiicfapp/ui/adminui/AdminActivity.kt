package com.dan.jamiicfapp.ui.adminui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.ui.WelcomeActivity
import com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.fetchfeedback.FetchFeedbackActivity
import com.google.android.material.navigation.NavigationView

class AdminActivity : AppCompatActivity() {

    //Not in Git
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        /*val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view_admin)
        val navController = findNavController(R.id.nav_host_fragment_admin)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_disabilities, R.id.nav_admin, R.id.nav_addhomeitem, R.id.nav_donations
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.admin, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {

                startActivity(Intent(this, WelcomeActivity::class.java))
                this.finish()
                Toast.makeText(this, "Thank you for being part of Our process", Toast.LENGTH_SHORT)
                    .show()
            }
            R.id.action_Feedback -> {
                startActivity(Intent(this, FetchFeedbackActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_admin)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}