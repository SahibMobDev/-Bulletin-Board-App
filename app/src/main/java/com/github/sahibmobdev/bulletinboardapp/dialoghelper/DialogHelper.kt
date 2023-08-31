package com.github.sahibmobdev.bulletinboardapp.dialoghelper

import android.app.AlertDialog
import com.github.sahibmobdev.bulletinboardapp.MainActivity
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.authhelper.AccountHelper
import com.github.sahibmobdev.bulletinboardapp.databinding.SignDialogBinding

class DialogHelper(private val act: MainActivity) {

    private val accHelper = AccountHelper(act)

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(act)
        val binding = SignDialogBinding.inflate(act.layoutInflater)
        builder.setView(binding.root)

        if (index == DialogConst.SIGN_UP_STATE) {
            binding.tvSignTitle.text = act.resources.getString(R.string.ac_sign_up)
            binding.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
        } else {
            binding.tvSignTitle.text = act.resources.getString(R.string.ac_sign_in)
            binding.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
        }
        val dialog = builder.create()
        binding.btSignUpIn.setOnClickListener {
            dialog.dismiss()
            if (index == DialogConst.SIGN_UP_STATE) {
                val email = binding.edSignEmail.text.toString()
                val password = binding.edSignPassword.text.toString()
                accHelper.signUpWithEmail(email, password)
            } else {
                val email = binding.edSignEmail.text.toString()
                val password = binding.edSignPassword.text.toString()
                accHelper.signInWithEmail(email, password)
            }
        }

        dialog.show()
    }

}