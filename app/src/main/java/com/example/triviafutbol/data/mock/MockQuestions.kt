package com.example.triviafutbol.data.mock

import com.example.triviafutbol.domain.model.Category
import com.example.triviafutbol.domain.model.Question

object MockQuestions {

    val categories = listOf(
        Category(
            id = "cat_arg",
            name = "Fútbol Argentino",
            icon = "🇦🇷",
            description = "Superliga, Copa de la Liga e historia del fútbol local.",
            questionCount = 15
        ),
        Category(
            id = "cat_wc",
            name = "Mundiales",
            icon = "🏆",
            description = "Copas del Mundo de la FIFA desde 1930 hasta la actualidad.",
            questionCount = 20
        ),
        Category(
            id = "cat_ucl",
            name = "Champions League",
            icon = "⭐",
            description = "El torneo de clubes más importante de Europa.",
            questionCount = 15
        ),
        Category(
            id = "cat_legends",
            name = "Grandes Jugadores",
            icon = "👑",
            description = "Messi, Maradona, Pelé, Cristiano Ronaldo y más leyendas.",
            questionCount = 18
        )
    )

    val dailyQuestions = listOf(
        Question(
            id = "d1",
            text = "¿En qué año ganó la Selección Argentina su tercera Copa del Mundo en Qatar?",
            options = listOf("2018", "2020", "2022", "2024"),
            correctAnswerIndex = 2,
            explanation = "Argentina venció a Francia en los penales en la final de Qatar 2022.",
            category = "Mundiales"
        ),
        Question(
            id = "d2",
            text = "¿Qué club argentino es conocido como 'El Xeneize'?",
            options = listOf("River Plate", "Boca Juniors", "Racing Club", "Independiente"),
            correctAnswerIndex = 1,
            explanation = "'Xeneize' proviene del ligur y significa 'genovés', por el origen de los fundadores de Boca Juniors.",
            category = "Fútbol Argentino"
        ),
        Question(
            id = "d3",
            text = "¿Quién es el máximo goleador histórico de la Champions League?",
            options = listOf("Lionel Messi", "Cristiano Ronaldo", "Robert Lewandowski", "Karim Benzema"),
            correctAnswerIndex = 1,
            explanation = "Cristiano Ronaldo convirtió 140 goles en la UEFA Champions League.",
            category = "Champions League"
        ),
        Question(
            id = "d4",
            text = "¿En qué país se jugó el primer Mundial de Fútbol en 1930?",
            options = listOf("Brasil", "Argentina", "Uruguay", "Italia"),
            correctAnswerIndex = 2,
            explanation = "Uruguay fue la sede y además el primer campeón del mundo.",
            category = "Mundiales"
        ),
        Question(
            id = "d5",
            text = "¿Qué jugador es apodado 'La Pulga'?",
            options = listOf("Diego Maradona", "Lionel Messi", "Sergio Agüero", "Ángel Di María"),
            correctAnswerIndex = 1,
            explanation = "Lionel Messi fue apodado así desde joven por su baja estatura y velocidad.",
            category = "Grandes Jugadores"
        ),
        Question(
            id = "d6",
            text = "¿Qué equipo ganó la Copa Libertadores 2018 jugada en Madrid?",
            options = listOf("Boca Juniors", "Gremio", "River Plate", "Flamengo"),
            correctAnswerIndex = 2,
            explanation = "River Plate venció 3-1 a Boca Juniors en el Estadio Santiago Bernabéu.",
            category = "Fútbol Argentino"
        ),
        Question(
            id = "d7",
            text = "¿Cuántos balones de oro ha ganado Lionel Messi?",
            options = listOf("5", "7", "8", "9"),
            correctAnswerIndex = 2,
            explanation = "Messi ganó su 8º Balón de Oro en el año 2023.",
            category = "Grandes Jugadores"
        ),
        Question(
            id = "d8",
            text = "¿Qué selección tiene más títulos de la Copa Mundial de la FIFA?",
            options = listOf("Alemania", "Italia", "Argentina", "Brasil"),
            correctAnswerIndex = 3,
            explanation = "Brasil ha ganado 5 mundiales (1958, 1962, 1970, 1994, 2002).",
            category = "Mundiales"
        ),
        Question(
            id = "d9",
            text = "¿Qué club europeo tiene más títulos de Champions League / Copa de Europa?",
            options = listOf("A.C. Milan", "Real Madrid", "FC Barcelona", "Bayern Múnich"),
            correctAnswerIndex = 1,
            explanation = "El Real Madrid ostenta el récord histórico con 15 títulos continentales.",
            category = "Champions League"
        ),
        Question(
            id = "d10",
            text = "¿En qué club argentino debutó profesionalmente Diego Armando Maradona?",
            options = listOf("Boca Juniors", "Argentinos Juniors", "Newell's Old Boys", "Lanús"),
            correctAnswerIndex = 1,
            explanation = "Maradona debutó en Argentinos Juniors el 20 de octubre de 1976.",
            category = "Fútbol Argentino"
        )
    )

    val trainingQuestions = mapOf(
        "cat_arg" to listOf(
            Question(
                id = "arg_1",
                text = "¿Cuál es el estadio conocido como 'La Bombonera'?",
                options = listOf("Estadio Monumental", "Estadio Alberto J. Armando", "Estadio Libertadores de América", "Estadio Mario Alberto Kempes"),
                correctAnswerIndex = 1,
                explanation = "Es la mítica cancha de Boca Juniors, inaugurada en 1940.",
                category = "Fútbol Argentino"
            ),
            Question(
                id = "arg_2",
                text = "¿Qué equipo es conocido como el 'Rey de Copas' por sus 7 Copas Libertadores?",
                options = listOf("Boca Juniors", "River Plate", "Independiente", "Estudiantes de La Plata"),
                correctAnswerIndex = 2,
                explanation = "Independiente ganó 7 Libertadores, siendo el máximo ganador histórico del torneo.",
                category = "Fútbol Argentino"
            ),
            Question(
                id = "arg_3",
                text = "¿Qué entrenador dirigió a la Selección Argentina campeona de América en 2021 y del Mundo en 2022?",
                options = listOf("Marcelo Bielsa", "Lionel Scaloni", "Alejandro Sabella", "Jorge Sampaoli"),
                correctAnswerIndex = 1,
                explanation = "Lionel Scaloni lideró el proceso denominado la 'Scaloneta'.",
                category = "Fútbol Argentino"
            )
        ),
        "cat_wc" to listOf(
            Question(
                id = "wc_1",
                text = "¿Quién convirtió la famosa 'Mano de Dios' en el Mundial de 1986?",
                options = listOf("Pelé", "Diego Maradona", "Mario Kempes", "Ronaldo Nazário"),
                correctAnswerIndex = 1,
                explanation = "Maradona anotó el legendario gol frente a Inglaterra en los cuartos de final de México '86.",
                category = "Mundiales"
            ),
            Question(
                id = "wc_2",
                text = "¿En qué año fue campeón del mundo la Selección de España?",
                options = listOf("2006", "2010", "2014", "2018"),
                correctAnswerIndex = 1,
                explanation = "España venció a Países Bajos 1-0 en Sudáfrica 2010 con gol de Iniesta.",
                category = "Mundiales"
            )
        )
    )
}
