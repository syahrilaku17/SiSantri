package com.example.sisantri

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonlogin.setOnClickListener {
            val email = et_email_login.text.toString()
            val password = et_password_login.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email atau Password kosong",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
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
        back_to_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
  }
}