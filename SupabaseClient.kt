package com.example.kuholifier

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage


object SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://sznbpfwaowugfbxvkzbo.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InN6bmJwZndhb3d1Z2ZieHZremJvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM4NzgzNTgsImV4cCI6MjA3OTQ1NDM1OH0.n1qjo2-bF8AXAWzC7xocVKH5OMK-CP3BeNiY0gZaLEg"

    ) {
        install(Auth)
        install(Postgrest)
    }
}