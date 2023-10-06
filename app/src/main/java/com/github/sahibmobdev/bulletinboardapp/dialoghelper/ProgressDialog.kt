package com.github.sahibmobdev.bulletinboardapp.dialoghelper

import android.app.Activity
import android.app.AlertDialog
import com.github.sahibmobdev.bulletinboardapp.databinding.ProgressDialogLayoutBinding

object ProgressDialog {

    fun createProgressDialog(act: Activity): AlertDialog {
        val builder = AlertDialog.Builder(act)
        val binding = ProgressDialogLayoutBinding.inflate(act.layoutInflater)
        val view = binding.root
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }
}