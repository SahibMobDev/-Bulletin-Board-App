package com.github.sahibmobdev.bulletinboardapp.dialoghelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
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

        setDialogState(index, binding)

        val dialog = builder.create()
        binding.btSignUpIn.setOnClickListener {
            setOnClickSignUpIn(index, binding, dialog)
        }
        binding.btForgetP.setOnClickListener {
            setOnClickResetPassword(binding, dialog)
        }

        dialog.show()
    }

    private fun setOnClickResetPassword(
        binding: SignDialogBinding,
        dialog: AlertDialog?
    ) {
        val email = binding.edSignEmail.text.toString()
        if (email.isNotEmpty()) {
            act.mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(act, R.string.email_reset_password_was_sent, Toast.LENGTH_LONG).show()
                }
            }
            dialog?.dismiss()
        } else {
            binding.tvDialogMessage.visibility = View.VISIBLE
        }
    }

    private fun setOnClickSignUpIn(index: Int, binding: SignDialogBinding, dialog: AlertDialog?) {
        dialog?.dismiss()
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

    private fun setDialogState(index: Int, binding: SignDialogBinding) {
        if (index == DialogConst.SIGN_UP_STATE) {
            binding.tvSignTitle.text = act.resources.getString(R.string.ac_sign_up)
            binding.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
        } else {
            binding.tvSignTitle.text = act.resources.getString(R.string.ac_sign_in)
            binding.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
            binding.btForgetP.visibility = View.VISIBLE
        }
    }

}