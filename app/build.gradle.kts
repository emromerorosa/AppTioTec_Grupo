plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "cl.duoc.apptiotec"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "cl.duoc.apptiotec"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("../tiotec-key.jks")
            storePassword = "tiotec123"
            keyAlias = "key0"
            keyPassword = "tiotec123"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Window Size Classes: sirve para saber si la pantalla es Compacta, Mediana o Expandida
    implementation("androidx.compose.material3:material3-window-size-class")

    // Navegación Jetpack Compose y ViewModel
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-rc02")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")

    implementation("io.coil-kt:coil-compose:2.6.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    // --- Guía 15: pruebas unitarias ---
    // MockK: crea "dobles" falsos de los repositorios para probar sin internet
    testImplementation("io.mockk:mockk:1.13.8")
    // Herramientas para probar coroutines (runTest, Dispatchers de prueba)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

}