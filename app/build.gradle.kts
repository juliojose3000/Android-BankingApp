plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp")  //Kotlin Symbol Processing
    //id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.loaizasoftware.bankingapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.loaizasoftware.bankingapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    // ------------------------------------------------------
    // 📦 MODULE DEPENDENCIES
    // ------------------------------------------------------

    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":feature_login"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.navigation:navigation-compose:2.9.0") //Compose Navigation

    // ------------------------------------------------------
    // 🧰 Utilities
    // ------------------------------------------------------

    //Room
    //val room_version = "2.6.1" // Use the latest stable version
    implementation("androidx.room:room-runtime:2.7.2")
    annotationProcessor("androidx.room:room-compiler:2.7.2") // For Java
    ksp("androidx.room:room-compiler:2.7.2") // For Kotlin

    // ------------------------------------------------------
    // 🔐 DEPENDENCY INJECTION
    // ------------------------------------------------------

    //Hilt
    //implementation("com.google.dagger:hilt-android:2.56.2")
    //ksp("com.google.dagger:hilt-android-compiler:2.56.2")

    //Koin
    implementation("io.insert-koin:koin-android:3.5.0")
    implementation("io.insert-koin:koin-core:3.5.0")
    implementation("io.insert-koin:koin-androidx-compose:3.5.0") // if using Compose


    // ----------------------------
    // 🧪 JVM UNIT TESTS (test/)
    // ----------------------------

    testImplementation(libs.junit)

    // ----------------------------
    // 🧪 ANDROID INSTRUMENTED TESTS (androidTest/)
    // ----------------------------

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}