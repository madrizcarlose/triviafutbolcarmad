package com.example.triviafutbol

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.Coil
import coil.ImageLoader
import com.example.triviafutbol.ui.navigation.AppNavigation
import com.example.triviafutbol.ui.notification.NotificationHelper
import com.example.triviafutbol.ui.sound.SoundManager
import com.example.triviafutbol.ui.theme.TriviafutbolTheme
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Solicitar permiso de notificaciones para Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

        // Crear canal de notificaciones locales
        NotificationHelper.createNotificationChannel(applicationContext)

        // Configurar Coil ImageLoader con User-Agent
        val imageLoader = ImageLoader.Builder(applicationContext)
            .okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .header("User-Agent", "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)

        // Desactivar el sonido sintético por defecto del sistema Android al tocar la pantalla
        window.decorView.isSoundEffectsEnabled = false

        // Inicializar efectos de sonido
        SoundManager.init(applicationContext)

        // Reproducir sonido de bienvenida / inicio al abrir la app
        SoundManager.playIntro()

        setContent {
            TriviafutbolTheme {
                AppNavigation()
            }
        }
    }
}
