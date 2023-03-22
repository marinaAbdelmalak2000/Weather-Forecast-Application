package com.example.weatherforecastapplication

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.weatherforecastapplication.alerts.view.FragmentAlertList
import com.example.weatherforecastapplication.favourite.view.FragmentFavouriteList
import com.example.weatherforecastapplication.home.view.FragmentHome
import com.example.weatherforecastapplication.settings.view.FragmentSettings
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener




class MainActivity : AppCompatActivity(),OnNavigationItemSelectedListener {

    //variable
    lateinit var drawerLayout:DrawerLayout
    lateinit var navigationView:NavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout=findViewById(R.id.drawer_layout)
        navigationView=findViewById(R.id.navigator_layout)
        toolbar=findViewById(R.id.toolbar)
        setTitle("")

        //toolBar
        setSupportActionBar(toolbar)

        //navigation Drawer menu
        navigationView.bringToFront()
        var toggle:ActionBarDrawerToggle=ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.OPEN_navigation,R.string.Close_navigation)

        toggle.isDrawerIndicatorEnabled=true

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        this.onBackPressedDispatcher.addCallback(onBackPressedCallback)


    }

    //handle back button
    val onBackPressedCallback = object: OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {

            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            else{
                customExitDialog()
            }

        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)

        when(item.itemId){
            R.id.home->{
                setToolbarTitle("Home")
                changeFragment(FragmentHome())
            }
            R.id.favourite->{
                setToolbarTitle("Favourite")
                changeFragment(FragmentFavouriteList())
            }
            R.id.alter->{
                setToolbarTitle("Alter")
                changeFragment(FragmentAlertList())
            }
            R.id.setting->{
                setToolbarTitle("Setting")
                changeFragment(FragmentSettings())
            }

        }
        return true
    }

    fun setToolbarTitle(title:String){
        supportActionBar?.title=title
    }
    fun changeFragment(fragmentSelect:Fragment){
        val fragment=supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_contaner,fragmentSelect).commit()
    }

    fun customExitDialog() {
        // creating custom dialog
        val dialog = Dialog(this@MainActivity)

        // setting content view to dialog
        dialog.setContentView(R.layout.custom_exit_dialog)

        // getting reference of TextView
        val dialogButtonYes = dialog.findViewById(R.id.textViewYes) as TextView
        val dialogButtonNo = dialog.findViewById(R.id.textViewNo) as TextView

        // click listener for No
        dialogButtonNo.setOnClickListener { // dismiss the dialog
            dialog.dismiss()
        }
        // click listener for Yes
        dialogButtonYes.setOnClickListener { // dismiss the dialog and exit the exit
            dialog.dismiss()
            finish()
        }

        // show the exit dialog
        dialog.show()
    }

}