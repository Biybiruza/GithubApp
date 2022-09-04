package com.example.githubapp.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.githubapp.R
import com.example.githubapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSignIn.setOnClickListener {
                val intent = Intent(this@WelcomeActivity,SignInActivity::class.java)
                startActivity(intent)
            }
            btnSignUp.setOnClickListener {
                val intent = Intent(this@WelcomeActivity,SignUpActivity::class.java)
                startActivity(intent)
            }
            googleSignBtn.setOnClickListener {
                Toast.makeText(this@WelcomeActivity,"Islenbekde", Toast.LENGTH_LONG).show()
                /*val intent = Intent(this@WelcomeActivity,SignInActivity::class.java)
                startActivity(intent)*/
            }
        }
    }
}