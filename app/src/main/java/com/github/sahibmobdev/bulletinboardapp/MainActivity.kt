package com.github.sahibmobdev.bulletinboardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.github.sahibmobdev.bulletinboardapp.activities.EditAdsAct
import com.github.sahibmobdev.bulletinboardapp.databinding.ActivityMainBinding
import com.github.sahibmobdev.bulletinboardapp.dialoghelper.DialogConst.SIGN_IN_STATE
import com.github.sahibmobdev.bulletinboardapp.dialoghelper.DialogConst.SIGN_UP_STATE
import com.github.sahibmobdev.bulletinboardapp.dialoghelper.DialogHelper
import com.github.sahibmobdev.bulletinboardapp.dialoghelper.GoogleAccConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()
    lateinit var tvAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_new_ads) {
            val i = Intent(this, EditAdsAct::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    dialogHelper.accHelper.signInFirebaseWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.d("MyLog", "Api error: ${e.message}")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    private fun init() {
        setSupportActionBar(binding.mainContent.myToolbar)
        binding.apply {
            val toggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,mainContent.myToolbar,R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            navView.setNavigationItemSelectedListener(this@MainActivity)
            tvAccount = binding.navView.getHeaderView(0).findViewById(R.id.tvAccoutnEmail)
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
                uiUpdate(null)
                mAuth.signOut()
                dialogHelper.accHelper.signOutG()
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun uiUpdate(user: FirebaseUser?) {
        tvAccount.text = if (user == null) {
            resources.getString(R.string.not_reg)
        } else {
            user.email
        }
    }
}