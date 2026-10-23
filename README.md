# ⚽ Fútbol Quiz App (Trivia de Fútbol)

Aplicación móvil de Trivia y Quiz de Fútbol desarrollada en **Android Studio** utilizando **Kotlin** y **Jetpack Compose**.

---

## 🚀 Resumen del Proyecto y Funcionalidades

### 1. 🏆 Modos de Juego
- **Desafío Diario:** 10 preguntas competitivas únicas por día (semilla matemática por fecha). Suma puntos directamente al Ranking Global y mantiene la racha.
- **⚡ Sprint 60 Segundos:** Modo contrareloj de alta velocidad (+2s por acierto, -5s por error).
- **📚 Modo Entrenamiento:** Partidas ilimitadas por categorías (*Fútbol Argentino, Mundiales, Champions League, Leyendas*).

### 2. 🎨 Diseño e Interfaz Estilo Consola (EA Sports FC / FIFA)
- Encabezado con **imagen de estadio HD** que cambia aleatoriamente en cada apertura de la app.
- Botón gigante protagónico con resplandor neón **"¡JUGAR DESAFÍO DIARIO! 🔥"**.
- Avatar del usuario con anillo neón brillante.
- Menú de navegación inferior (*BottomBar*) con íconos vectoriales sobrios e integrados (*Inicio, Ranking, Entrenar, Perfil*).

### 3. 🔊 Sonidos y Efectos Visuales
- **Música ambiental de estadio** con botón de silenciar/activar parlante (🔊 / 🔇) en el encabezado.
- **Sonidos reales:**
  - `intro.mp3`: Sonido de apertura al iniciar la app.
  - `whistle.mp3`: Silbato de árbitro al recibir amonestaciones.
  - `cheer.mp3`: Festejo de hinchada al acertar.
  - `click.wav`: Clic táctil de botones.
- **Efectos 3D:** Tarjetas amonestadoras 🟨/🟥 con animación de rebote y **lluvia de confeti 🎊 con estrellas doradas** al finalizar la partida.

### 4. 👤 Perfil, Niveles y Logros
- **Sistema de Niveles (1 al 10):** Escala de experiencia XP desde *Nivel 1 (Promesa de Cantera 🌱)* hasta *Nivel 10 (Leyenda Mundial 👑)* según los puntos acumulados.
- **Medidor de Efectividad:** Porcentaje de precisión en vivo (*🎯 Francotirador, ⚽ Goleador Letal, etc.*).
- **🗺️ Camino de Temporada:** Mapa de progresión de 8 estaciones con recompensas.
- **🏆 Vitrina de Logros:** Desafíos de alto nivel a largo plazo (*Bota de Oro 👟, Copa de Campeones 🏆, Titular Indiscutido 🔥, Director Técnico 🧢*).
- **Edición de Perfil:** Personalización de nombre de usuario y selección entre 10 avatares/escudos preestablecidos.

### 5. 🛡️ Protección de Racha (Salvavidas) & Reloj Contador
- **Pase Salvavidas 🛡️:** Protege y congela la racha del jugador automáticamente si olvida entrar un día.
- **Reloj `HH:MM:SS`:** Muestra la cuenta regresiva en vivo hasta la medianoche para habilitar el siguiente Desafío Diario.

### 6. ☁️ Backend Supabase
- **Cliente de Conexión:** `data/remote/SupabaseClientManager.kt`
- **Tablas:** `questions` y `profiles`.
- **Ranking Global en Vivo:** Consultas en tiempo real ordenadas por puntos totales.

---

## 🛠️ Estructura del Código (Clean Architecture + MVVM)

```
com.example.triviafutbol/
├── data/
│   ├── mock/          # Preguntas locales y categorías
│   ├── model/         # DTOs (ProfileDto, QuestionDto)
│   ├── remote/        # SupabaseClientManager
│   └── repository/    # TriviaRepository
├── domain/
│   └── model/         # UserProfile, Question, GameSession, Achievement
├── ui/
│   ├── components/    # Botones, Tarjetas 3D, Confeti, Reloj, Avatares, Mapa
│   ├── home/          # HomeScreen & HomeViewModel
│   ├── navigation/    # NavHost, AppNavigation, Screen
│   ├── notification/  # NotificationHelper
│   ├── profile/       # ProfileScreen
│   ├── quiz/          # QuizScreen & QuizViewModel
│   ├── ranking/       # RankingScreen & RankingViewModel
│   ├── result/        # ResultScreen
│   ├── sound/         # SoundManager
│   ├── theme/         # Colores Fútbol Dark, Tipografía
│   └── training/      # TrainingScreen
└── MainActivity.kt
```

---

## 📌 Pendientes Futuros
- [ ] Integración de métricas con Firebase Analytics.
- [ ] Pantalla de celebración de Subida de Nivel (*Level Up*).
