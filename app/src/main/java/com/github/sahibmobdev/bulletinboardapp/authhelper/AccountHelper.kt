package com.github.sahibmobdev.bulletinboardapp.authhelper

import android.util.Log
import android.widget.Toast
import com.github.sahibmobdev.bulletinboardapp.MainActivity
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.constants.FirebaseAuthConstants
import com.github.sahibmobdev.bulletinboardapp.dialoghelper.GoogleAccConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AccountHelper(private val act: MainActivity) {

    private lateinit var signInClient: GoogleSignInClient

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            act.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    sendEmailVerification(task.result?.user!!)
                    act.uiUpdate(task.result.user)
                    Toast.makeText(act, "Sign up done", Toast.LENGTH_LONG).show()
                } else {
                    Log.d("MyLog", "Exception : ${task.exception}")
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        val exception = task.exception as FirebaseAuthUserCollisionException
                        if (exception.errorCode == FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE) {
                            //Toast.makeText(act, FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE, Toast.LENGTH_SHORT).show()
                            //Link email
                            linkEmailToG(email, password)
                        }
                    } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        val exception = task.exception as FirebaseAuthInvalidCredentialsException
                        if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
                            Toast.makeText(act, FirebaseAuthConstants.ERROR_INVALID_EMAIL, Toast.LENGTH_SHORT).show()
                        }
                    }
                    if (task.exception is FirebaseAuthWeakPasswordException) {
                        val exception = task.exception as FirebaseAuthWeakPasswordException
                        //Log.d("MyLog", "Exception : ${exception.errorCode}")
                        if (exception.errorCode == FirebaseAuthConstants.ERROR_WEAK_PASSWORD) {
                            Toast.makeText(act, exception.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    Toast.makeText(act, act.resources.getString(R.string.sign_up_error), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            act.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    act.uiUpdate(task.result.user)
                    Toast.makeText(act, "Sign in done", Toast.LENGTH_LONG).show()
                } else {
                    Log.d("MyLog", "Exception : ${task.exception}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                        val exception = task.exception as FirebaseAuthInvalidCredentialsException

                        if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
                            Toast.makeText(act, FirebaseAuthConstants.ERROR_INVALID_EMAIL, Toast.LENGTH_SHORT).show()
                        } else if (exception.errorCode == FirebaseAuthConstants.ERROR_WRONG_PASSWORD) {
                            Toast.makeText(act, FirebaseAuthConstants.ERROR_WRONG_PASSWORD, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun linkEmailToG(email: String, password: String) {
        if (act.mAuth.currentUser != null) {
            val credential = EmailAuthProvider.getCredential(email, password)
            act.mAuth.currentUser?.linkWithCredential(credential)?.addOnCompleteListener {task ->

                if (task.isSuccessful) {
                    Toast.makeText(act, R.string.link_done, Toast.LENGTH_SHORT).show()
                }

            }
        } else {
            Toast.makeText(act, R.string.enter_to_G, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSignInClient() : GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(act.getString(R.string.default_web_client_id)).requestEmail()
            .build()
        return GoogleSignIn.getClient(act,gso)
    }

    fun signInWithGoogle() {
        signInClient = getSignInClient()
        val intent = signInClient.signInIntent
        act.startActivityForResult(intent, GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        act.mAuth.signInWithCredential(credential).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                act.uiUpdate(task.result.user)
                Toast.makeText(act, "Sign in done", Toast.LENGTH_LONG).show()
            } else {
                Log.d("MyLog", "Google Sign In Exception : ${task.exception}")
            }
        }
    }

    private fun sendEmailVerification(user:FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(act, act.resources.getString(R.string.send_verification_done), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(act, act.resources.getString(R.string.send_verification_error), Toast.LENGTH_LONG).show()
            }
        }
    }
}