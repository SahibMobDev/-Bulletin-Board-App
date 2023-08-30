package com.github.sahibmobdev.bulletinboardapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.github.sahibmobdev.bulletinboardapp.databinding.ActivityMainBinding
import com.github.sahibmobdev.bulletinboardapp.dialoghelper.DialogConst.SIGN_IN_STATE
import com.github.sahibmobdev.bulletinboardapp.dialoghelper.DialogConst.SIGN_UP_STATE
import com.github.sahibmobdev.bulletinboardapp.dialoghelper.DialogHelper
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            val toggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,mainContent.myToolbar,R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            navView.setNavigationItemSelectedListener(this@MainActivity)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.id_my_ads -> {
                Toast.makeText(this, "id_my_ads", Toast.LENGTH_SHORT).show()
            }
            R.id.id_car -> {
                Toast.makeText(this, "id_car", Toast.LENGTH_SHORT).show()
            }
            R.id.id_pc -> {
                Toast.makeText(this, "id_pc", Toast.LENGTH_SHORT).show()
            }
            R.id.id_smartphone -> {
                Toast.makeText(this, "id_smartphone", Toast.LENGTH_SHORT).show()
            }
            R.id.id_dm -> {
                Toast.makeText(this, "id_dm", Toast.LENGTH_SHORT).show()
            }
            R.id.id_sign_up -> {
                dialogHelper.createSignDialog(SIGN_UP_STATE)
            }
            R.id.id_sign_in -> {
                dialogHelper.createSignDialog(SIGN_IN_STATE)
            }
            R.id.id_sign_out -> {
                Toast.makeText(this, "id_sign_out", Toast.LENGTH_SHORT).show()
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}