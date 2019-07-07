package com.example.sisantri

import android.app.AlertDialog
import android.content.Intent
import android.drm.DrmManagerClient
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, ""+p0.errorMessage,Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val PERMISSION_CODE = 9999
    }

    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var firebaseAuth: FirebaseAuth
//    lateinit var alertDialog: AlertDialog

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSION_CODE)
        {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess)
            {
                val account = result.signInAccount
                val idToken = account!!.idToken

                val credential = GoogleAuthProvider.getCredential(idToken,null)
                firebaseAuthWithGoogle(credential)
            }
            else
            {
                Log.d("EDMT_ERROR", "Login Gagal")
                Toast.makeText(this, "Login Gagal",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(credential: AuthCredential?) {
        firebaseAuth!!.signInWithCredential(credential!!)
            .addOnSuccessListener { authResult ->
                val logged_email = authResult.user.email
                val logged_activity = Intent(this@LoginActivity,MainActivity::class.java)
                logged_activity.putExtra("email", logged_email)
                startActivity(logged_activity)
            }
            .addOnFailureListener {
                e->Toast.makeText(this, ""+e.message,Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configureGoogleClient()

        firebaseAuth = FirebaseAuth.getInstance()

        googlebutton.setOnClickListener {
            signIn()
        }

        buttonlogin.setOnClickListener {
            performLogin()
        }

        back_to_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, PERMISSION_CODE)
    }

    private fun configureGoogleClient() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API,options)
            .build()
        mGoogleApiClient.connect()
    }

    private fun performLogin(){
        val email = et_email_login.text.toString()
        val password = et_password_login.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email atau Password kosong",
                Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (!it.isSuccessful){ return@addOnCompleteListener
                    val intent = Intent (this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else
                    Toast.makeText(this, "Login Sukses", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("Main", "Failed Login : ${it.message}")
                Toast.makeText(this, "Email atau Password salah", Toast.LENGTH_SHORT).show()
            }
    }
}

