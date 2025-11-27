package com.example.kuholifier

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private fun signupDatabase(email: String, password: String) {
        lifecycleScope.launch {
            try {
                SupabaseClient.supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                Toast.makeText(this@SignupActivity,"Signup Successful",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                finish()

            }catch (e: Exception) {
                Toast.makeText(this@SignupActivity,"Signup failed:${e.message}",Toast.LENGTH_LONG).show()
            }
        }
    }
}