import json
from itertools import cycle

categories = [
    "Higiene diaria",
    "Buena alimentación",
    "Control de gasto diario",
    "Hábitos de lectura",
    "Aprendizaje de idiomas y cosas nuevas",
    "Ejercicio físico y deporte",
    "Metas laborales",
    "Metas académicas",
]

difficulties = [
    ("principiante", 10, "daily"),
    ("básico", 25, "daily"),
    ("intermedio", 50, "weekly"),
    ("avanzado", 75, "weekly"),
    ("élite", 100, "monthly"),
]

# 20 templates per category (all measurable/progressive via n/metric placeholders)
templates = {
    "Higiene diaria": [
        "Cepilla tus dientes durante {m} minutos al despertar ({n})",
        "Lava tu rostro y aplica rutina facial de {m} pasos ({n})",
        "Toma una ducha de máximo {m} minutos antes de las 8:00 ({n})",
        "Organiza tu habitación durante {m} minutos ({n})",
        "Desinfecta objetos personales clave ({n}) con {m} elementos",
        "Cambia toalla de baño tras {m} usos controlados ({n})",
        "Hidrata tu piel en {m} zonas del cuerpo ({n})",
        "Haz limpieza profunda del lavamanos por {m} minutos ({n})",
        "Prepara ropa limpia para {m} días siguientes ({n})",
        "Limpia y ordena calzado de uso diario ({n}) por {m} minutos",
        "Cambia funda de almohada cada {m} días ({n})",
        "Desinfecta celular y teclado {m} veces al día ({n})",
        "Lava manos en al menos {m} momentos críticos del día ({n})",
        "Recorta y limpia uñas de manos y pies ({n}) en {m} minutos",
        "Ventila dormitorio durante {m} minutos ({n})",
        "Lava botella reusable y utensilios personales ({n}) con checklist de {m} ítems",
        "Limpia espejo y superficies del baño ({n}) durante {m} minutos",
        "Haz rutina nocturna de higiene de {m} pasos ({n})",
        "Clasifica ropa sucia por tipo en {m} grupos ({n})",
        "Realiza revisión semanal de higiene personal con {m} criterios ({n})",
    ],
    "Buena alimentación": [
        "Consume al menos {m} porciones de verduras hoy ({n})",
        "Evita bebidas azucaradas durante {m} horas ({n})",
        "Toma {m} vasos de agua en el día ({n})",
        "Prepara una comida casera con {m} ingredientes frescos ({n})",
        "Reemplaza {m} snack procesado por fruta ({n})",
        "Registra macros de {m} comidas principales ({n})",
        "Limita frituras a {m} porciones semanales ({n})",
        "Incluye proteína magra en {m} tiempos de comida ({n})",
        "Cena antes de las {m}:00 ({n})",
        "Planifica menú saludable para {m} días ({n})",
        "Reduce azúcar añadida a menos de {m} gramos diarios ({n})",
        "Come conscientemente durante {m} minutos sin pantallas ({n})",
        "Añade {m} semillas o frutos secos a tu desayuno ({n})",
        "Prepara lonchera saludable para {m} jornadas ({n})",
        "Cumple ayuno nocturno de {m} horas ({n})",
        "Evita comida ultraprocesada en {m} comidas consecutivas ({n})",
        "Consume legumbres {m} veces en la semana ({n})",
        "Controla porciones usando plato equilibrado en {m} comidas ({n})",
        "Prueba una receta saludable nueva de {m} pasos ({n})",
        "Registra antojos y sustitúyelos con opción sana {m} veces ({n})",
    ],
    "Control de gasto diario": [
        "Registra el 100% de gastos del día en menos de {m} minutos ({n})",
        "No superes S/ {m} en gastos diarios ({n})",
        "Evita compras impulsivas durante {m} horas ({n})",
        "Compara precios en al menos {m} tiendas antes de comprar ({n})",
        "Ahorra S/ {m} preparando comida en casa ({n})",
        "Define tope por categoría para {m} rubros ({n})",
        "Revisa suscripciones y elimina {m} gasto innecesario ({n})",
        "Separa S/ {m} para fondo de emergencia ({n})",
        "Usa efectivo/control digital para solo {m} compras ({n})",
        "Haz conciliación de gastos por {m} minutos ({n})",
        "Reduce gasto en delivery a máximo {m} veces por semana ({n})",
        "Negocia o busca descuento en {m} compras ({n})",
        "Haz lista y compra solo {m} ítems planificados ({n})",
        "Evita compras menores a S/ {m} sin revisar presupuesto ({n})",
        "Registra gasto hormiga y recorta S/ {m} ({n})",
        "Establece meta de ahorro de S/ {m} esta semana ({n})",
        "Monitorea saldo diario {m} veces al día ({n})",
        "Reserva S/ {m} para meta grande mensual ({n})",
        "Compra transporte/servicios optimizando S/ {m} ({n})",
        "Evalúa utilidad real antes de comprar con check de {m} preguntas ({n})",
    ],
    "Hábitos de lectura": [
        "Lee {m} páginas continuas de un libro ({n})",
        "Dedica {m} minutos a lectura profunda sin interrupciones ({n})",
        "Subraya y resume {m} ideas clave del texto ({n})",
        "Lee antes de dormir durante {m} minutos ({n})",
        "Completa {m} capítulos esta semana ({n})",
        "Haz una reseña corta de {m} líneas ({n})",
        "Aprende {m} palabras nuevas desde la lectura ({n})",
        "Lee un artículo técnico de {m} minutos ({n})",
        "Practica lectura en voz alta por {m} minutos ({n})",
        "Lleva racha de lectura por {m} días ({n})",
        "Alterna ficción/no ficción en bloque de {m} sesiones ({n})",
        "Usa método Pomodoro y completa {m} pomodoros de lectura ({n})",
        "Comparte {m} aprendizajes del libro con otra persona ({n})",
        "Avanza {m}% del libro actual ({n})",
        "Relee y consolida {m} conceptos importantes ({n})",
        "Organiza tu lista de lectura con {m} prioridades ({n})",
        "Haz mapa mental con {m} nodos del capítulo ({n})",
        "Lee en segundo idioma durante {m} minutos ({n})",
        "Consulta bibliografía complementaria en {m} fuentes ({n})",
        "Finaliza un libro en {m} días o menos ({n})",
    ],
    "Aprendizaje de idiomas y cosas nuevas": [
        "Estudia {m} palabras nuevas del idioma objetivo ({n})",
        "Practica pronunciación durante {m} minutos ({n})",
        "Escucha audio comprensible por {m} minutos ({n})",
        "Completa {m} ejercicios de gramática ({n})",
        "Escribe {m} frases usando vocabulario nuevo ({n})",
        "Mantén conversación guiada de {m} minutos ({n})",
        "Aprende {m} conceptos nuevos de una habilidad técnica ({n})",
        "Mira clase corta y toma {m} apuntes clave ({n})",
        "Repasa tarjetas de memoria por {m} minutos ({n})",
        "Traduce un texto breve de {m} líneas ({n})",
        "Practica escucha activa con {m} preguntas de comprensión ({n})",
        "Haz shadowing durante {m} minutos ({n})",
        "Completa proyecto mini aplicando {m} aprendizajes ({n})",
        "Resuelve {m} problemas prácticos de tema nuevo ({n})",
        "Haz resumen de estudio de {m} puntos ({n})",
        "Repite contenido aprendido en {m} sesiones espaciadas ({n})",
        "Participa en intercambio de idioma por {m} minutos ({n})",
        "Lee documentación y extrae {m} comandos/conceptos ({n})",
        "Toma test de progreso y supera {m}% ({n})",
        "Cierra unidad de aprendizaje número {m} ({n})",
    ],
    "Ejercicio físico y deporte": [
        "Camina {m} minutos a ritmo moderado ({n})",
        "Haz {m} flexiones con técnica correcta ({n})",
        "Completa rutina de movilidad de {m} minutos ({n})",
        "Corre o trota {m} kilómetros ({n})",
        "Realiza {m} sentadillas controladas ({n})",
        "Practica deporte principal durante {m} minutos ({n})",
        "Haz plancha por {m} segundos en total ({n})",
        "Completa circuito de {m} rondas ({n})",
        "Sube escaleras por {m} pisos acumulados ({n})",
        "Entrena fuerza en {m} ejercicios compuestos ({n})",
        "Realiza sesión HIIT de {m} minutos ({n})",
        "Acumula {m} pasos diarios ({n})",
        "Haz estiramiento post-entreno de {m} minutos ({n})",
        "Entrena técnica deportiva en {m} drills ({n})",
        "Controla frecuencia cardiaca en {m} zonas de esfuerzo ({n})",
        "Programa {m} días activos sin saltos ({n})",
        "Mejora marca personal en {m} repetición/tiempo ({n})",
        "Haz sesión de recuperación activa de {m} minutos ({n})",
        "Completa reto de resistencia de {m} minutos ({n})",
        "Registra métricas de entrenamiento en {m} variables ({n})",
    ],
    "Metas laborales": [
        "Actualiza CV con {m} logros cuantificables ({n})",
        "Optimiza perfil profesional con {m} mejoras ({n})",
        "Postula a {m} oportunidades laborales ({n})",
        "Contacta a {m} personas de networking ({n})",
        "Completa tarea crítica de trabajo en {m} minutos de foco ({n})",
        "Aprende herramienta profesional con {m} ejercicios ({n})",
        "Publica {m} aportes de valor en red profesional ({n})",
        "Mejora portafolio con {m} proyectos/casos ({n})",
        "Practica entrevista durante {m} minutos ({n})",
        "Define plan laboral para {m} semanas ({n})",
        "Envía propuesta comercial a {m} potenciales clientes ({n})",
        "Automatiza proceso y ahorra {m} minutos diarios ({n})",
        "Documenta flujo de trabajo en {m} pasos ({n})",
        "Pide feedback profesional sobre {m} competencias ({n})",
        "Completa curso de carrera y finaliza módulo {m} ({n})",
        "Cierra pendientes importantes: {m} tareas ({n})",
        "Lidera o participa en reunión con agenda de {m} puntos ({n})",
        "Construye hábito de deep work con {m} bloques ({n})",
        "Negocia mejora profesional en {m} frentes ({n})",
        "Mide productividad con {m} indicadores ({n})",
    ],
    "Metas académicas": [
        "Estudia {m} minutos de forma enfocada ({n})",
        "Avanza {m} páginas de material de clase ({n})",
        "Resuelve {m} ejercicios académicos ({n})",
        "Haz resumen de {m} conceptos clave ({n})",
        "Repasa apuntes durante {m} minutos ({n})",
        "Completa {m} pomodoros de estudio ({n})",
        "Prepara exposición con {m} diapositivas ({n})",
        "Entrega avance de tarea con {m}% completado ({n})",
        "Realiza autoevaluación y alcanza {m}% ({n})",
        "Organiza calendario académico para {m} semanas ({n})",
        "Investiga en {m} fuentes académicas confiables ({n})",
        "Resuelve dudas con docente/foro en {m} preguntas ({n})",
        "Practica problemas tipo examen por {m} minutos ({n})",
        "Termina lectura obligatoria de {m} capítulos ({n})",
        "Reescribe notas en formato activo con {m} tarjetas ({n})",
        "Estudia en grupo durante {m} minutos ({n})",
        "Diseña plan de estudio para {m} cursos ({n})",
        "Refuerza tema débil con {m} sesiones extra ({n})",
        "Finaliza proyecto académico en {m} hitos ({n})",
        "Simula examen completo en {m} minutos ({n})",
    ],
}

# base metric ranges by difficulty to guarantee progression
metric_base = {
    "principiante": (2, 12),
    "básico": (8, 25),
    "intermedio": (15, 40),
    "avanzado": (25, 70),
    "élite": (40, 120),
}

all_items = []
uid = 1

for category in categories:
    for diff, xp, freq in difficulties:
        lo, hi = metric_base[diff]
        span = hi - lo
        tpls = templates[category]
        for i in range(100):
            metric = lo + (i % (span + 1))
            tpl = tpls[i % len(tpls)]
            title = f"[{diff.upper()}] " + tpl.format(m=metric, n=i + 1)
            description = (
                f"Objetivo {diff} en {category}. Registra evidencia al completar y verifica cumplimiento "
                f"medible del criterio ({metric})."
            )
            item = {
                "id": uid,
                "title": title,
                "description": description,
                "category": category,
                "difficulty": diff,
                "frequency": freq,
                "xpReward": xp,
            }
            all_items.append(item)
            uid += 1

assert len(all_items) == 4000
assert len({x['id'] for x in all_items}) == 4000
assert len({(x['category'], x['title']) for x in all_items}) == 4000

with open('app/src/main/assets/objectives_4000.json', 'w', encoding='utf-8') as f:
    json.dump(all_items, f, ensure_ascii=False, indent=2)

print('generated', len(all_items))
