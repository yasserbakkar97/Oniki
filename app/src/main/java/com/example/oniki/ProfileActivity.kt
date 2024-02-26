package com.example.oniki

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.oniki.api.User
import com.example.oniki.storage.SharedPrefManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*


class ProfileActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    //    var toggle: ActionBarDrawerToggle? = null

    private lateinit var user : User

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        when(item.itemId){
            R.id.miFilter -> Toast.makeText(this,"No Filter Now", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(findViewById(R.id.toolbar))

        user =  SharedPrefManager.getInstance(applicationContext).getUser()

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open , R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        val eventsFragment = EventsFragment()
        val connectionsFragment = ConnectionsFragment()
        val companyFragment = CompanyFragment()
        val myQrCodeFragment = MyQrCodeFragment()
        val accountSettingsFragment = AccountSettingsFragment()
        val contactUsFragment = ContactUsFragment()
        val notificationsFragment = NotificationsFragment()
        val liveFragment = LiveFragment()


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout , eventsFragment)
            commit()
        }

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miItem1 -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout , eventsFragment)
                        commit()
                        drawerLayout.closeDrawers()
                        supportActionBar?.title = "Events"
                    }
                }
                R.id.miItem2 -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout , connectionsFragment)
                        commit()
                        drawerLayout.closeDrawers()
                        supportActionBar?.title = "Connections"
                    }
                }
                R.id.miItem3 -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout , companyFragment)
                        commit()
                        drawerLayout.closeDrawers()
                        supportActionBar?.title = "Company"
                    }
                }
                R.id.miItem4 -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout , myQrCodeFragment)
                        commit()
                        drawerLayout.closeDrawers()
                        supportActionBar?.title = "My QR Code"
                    }
                }
                R.id.miItem5 -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout , accountSettingsFragment)
                        commit()
                        drawerLayout.closeDrawers()
                        supportActionBar?.title = "Account Settings"
                    }
                }
                R.id.miItem6 -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout , contactUsFragment)
                        commit()
                        drawerLayout.closeDrawers()
                        supportActionBar?.title = "Contact Us"
                    }
                }
                R.id.miItem7 -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout , notificationsFragment)
                        commit()
                        drawerLayout.closeDrawers()
                        supportActionBar?.title = "Notification"
                    }
                }
                R.id.miItem8 -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout , liveFragment)
                        commit()
                        drawerLayout.closeDrawers()
                        supportActionBar?.title = "Live"
                    }
                }
            }
            true
        }


//        //SharedPreference
//        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
//        val editor = sharedPref.edit()
//
//        val first_name = user.first_name
//        editor.apply {
//            putString("first_name",first_name)
//            apply()
//        }
//
//        tvNavigationUserName.text.apply {
//            val first_name = sharedPref.getString("first_name",null)
//            tvNavigationUserName.setText(first_name)
//        }

        val fullName = "${user.first_name} ${user.last_name}"
        val email = "${user.email}"


        navView.getHeaderView(0).tvNavigationUserName.text = fullName
        navView.getHeaderView(0).tvEmailOfUser.text = email

        tvSignOut.setOnClickListener {

            val view = View.inflate(this@ProfileActivity , R.layout.dialog_view,null)
            val builder = AlertDialog.Builder(this@ProfileActivity).setView(view)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(true)

            view.btn_ok.setOnClickListener {
                SharedPrefManager.getInstance(applicationContext).clear()
                val intent = Intent(applicationContext, SignInActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            view.btn_cancel.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_bar_events,menu)
        return true
    }


    override fun onStart() {
        super.onStart()

        if(!SharedPrefManager.getInstance(this).isLoggedIn){

            val intent = Intent(applicationContext , SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}