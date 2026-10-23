package com.example.triviafutbol.data.remote

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientManager {

    const val SUPABASE_URL = "https://opwbyaftzvjngneivopw.supabase.co"
    const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9wd2J5YWZ0enZqbmduZWl2b3B3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTIzMTcxNjQsImV4cCI6MjEwNzg5MzE2NH0.vtNcBuxxj4dTn0PXg_DfFWT7SJEV2fiM_A-71eZHQRc"

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        ) {
            install(Postgrest)
        }
    }
}
