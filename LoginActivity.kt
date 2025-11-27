package com.example.kuholifier

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
private fun loginDatabase(email: String, password: String) {
    lifecycleScope.launch {
        try {
            SupabaseClient.supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Toast.makeText(this@LoginActivity, "Login Successful",Toast.LENGTH_SHORT).show()

            startActivity(Intent(this@LoginActivity, MainActivity::class.java))

            finish()
            }catch (e: Exception) {
            Toast.makeText(this@LoginActivity, "Login Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}