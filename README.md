# Level Up Life (APK base)

Base funcional de una app Android estilo RPG para progreso personal.

## Arquitectura

- **Kotlin + Jetpack Compose**
- **MVVM**
- **Room** para persistencia local
- **Carga inicial desde JSON editable** (`app/src/main/assets/missions_seed.json`)

### Estructura

- `data/model`: entidades Room, enums del dominio
- `data/database`: `RoomDatabase`, DAO y convertidores
- `data/repository`: reglas de negocio (XP, nivel, racha, bootstrap)
- `viewmodel`: estado de UI y acciones de usuario
- `ui/screens`: pantallas principales (inicio, categorías, misiones, detalle, estadísticas, logros, configuración)
- `navigation`: grafo de navegación Compose

## Sistema base implementado

- Perfil inicial con nivel/rango/racha/presupuesto
- Misiones diarias/semanales/mensuales
- Completar misión => otorga XP automáticamente
- Fórmula de nivel: `nivelActual * 100` para siguiente nivel
- Rank automático (E, D, C, B, A, S, SS)
- Penalización ligera al omitir misión (reducción de racha)
- Registro de gastos y presupuesto configurable
- Persistencia local completa

## Escalabilidad a 500 objetivos por categoría

La app ya carga misiones desde JSON. Para escalar:
1. Expandir `missions_seed.json` (o dividir por archivos por categoría/dificultad).
2. O migrar a tabla de seeds más robusta manteniendo el mismo parser DTO.
3. Mantener `totalMissions=500` por categoría en `CategoryProgress` como base de progreso.

## Compilar APK (Debug)

1. Instalar Android Studio (JDK 17 + SDK Android 35).
2. Abrir carpeta del proyecto.
3. Sincronizar Gradle.
4. Ejecutar:

```bash
./gradlew :app:assembleDebug
```

APK generado en:

`app/build/outputs/apk/debug/app-debug.apk`

## Próximos pasos recomendados

- Añadir DI con Hilt
- WorkManager para refresco de misiones por calendario
- Exportación real de progreso (JSON/CSV)
- Filtros avanzados de misiones por categoría/dificultad
- Métricas históricas por día/semana/mes

## Dataset masivo de objetivos

Se añadió `app/src/main/assets/objectives_4000.json` con 4,000 objetivos (500 por categoría) distribuidos en cinco niveles de dificultad y listos para edición.
