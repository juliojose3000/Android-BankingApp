plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) //Enable Compose

    id("com.google.devtools.ksp")  //Kotlin Symbol Processing
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.loaizasoftware.feature_login"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))

    // ----------------------------
    // 🔷 ANDROID X
    // ----------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // Lifecycle ViewModel

    // ----------------------------
    // 🧩 COMPOSE CORE
    // ----------------------------
    implementation(platform("androidx.compose:compose-bom:2024.03.00")) // Compose BOM (manages all Compose versions)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3") //To use Box, Column, Row, Scaffold, etc
    implementation("androidx.compose.ui:ui-tooling-preview")

    // ------------------------------------------------------
    // 🔐 DEPENDENCY INJECTION
    // ------------------------------------------------------

    //Hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}