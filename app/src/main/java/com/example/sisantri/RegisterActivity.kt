package com.example.sisantri

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerbutton.setOnClickListener {
            performRegister()
        }

        back_to_login.setOnClickListener {
            Log.d("RegisterActivity", "Try to show login activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }

    private fun performRegister() {
        val email = et_email_register.text.toString()
        val password = et_password_register.text.toString()

        Log.d("RegisterActivity", "Email is:" + email)
        Log.d("RegisterActivity", "Password: $password")

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Masukin Email atau Passwordnya ga boleh kosong cuy",
                Toast.LENGTH_SHORT).show()
            return
        }

        // firebase authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
            if (!it.isSuccessful){ return@addOnCompleteListener
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "Berhasil Membuat Akun", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("Register", "Gagal membuat akun: ${it.message}")
                Toast.makeText(this, "Gagal Membuat Akun", Toast.LENGTH_SHORT).show()
            }

    }

}