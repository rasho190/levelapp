package com.leveluplife.data.model

enum class MissionCategory(val displayName: String) {
    HYGIENE("Higiene diaria"),
    NUTRITION("Buena alimentación"),
    EXPENSE("Control de gasto diario"),
    READING("Hábitos de lectura"),
    LEARNING("Aprendizaje"),
    EXERCISE("Ejercicio físico"),
    WORK("Metas laborales"),
    ACADEMIC("Metas académicas")
}

enum class Difficulty(val xpReward: Int) {
    EASY(10),
    MEDIUM(25),
    HARD(50),
    EPIC(100)
}

enum class Frequency {
    DAILY,
    WEEKLY,
    MONTHLY
}

enum class UserRank(val label: String, val minLevel: Int) {
    E("Iniciado", 1),
    D("Constante", 5),
    C("Disciplinado", 10),
    B("En crecimiento", 15),
    A("Alto rendimiento", 20),
    S("Élite personal", 30),
    SS("Monarca de hábitos", 40)
}
