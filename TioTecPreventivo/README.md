# TíoTec Preventivo - esqueleto (Kotlin + Compose + MVVM)

Paquete base: cl.duoc.tiotec
- data/local       -> entidades y DAOs de Room
- data/remote      -> Retrofit (API REST de prueba)
- data/repository  -> TioTecRepository (Room es la fuente de datos de las pantallas)
- domain           -> RiesgoCalculator (umbrales de riesgo)
- ui/navigation    -> NavHost + barra inferior
- ui/evaluacion    -> ViewModel + pantalla (flujo principal ya conectado)
- ui/home, ui/common -> Inicio y UiState

Cómo usarlo: crea un proyecto "Empty Activity (Compose)" con paquete cl.duoc.tiotec,
copia las carpetas dentro de app/src/main/java/cl/duoc/tiotec, reemplaza el AndroidManifest
y fusiona las dependencias de app/build.gradle.kts. Ajusta BASE_URL en ApiService.kt.
