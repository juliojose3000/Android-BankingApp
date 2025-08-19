plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("com.google.devtools.ksp")  //Kotlin Symbol Processing
    //id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.loaizasoftware.data"
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
}

dependencies {

    // ------------------------------------------------------
    // 📦 MODULE DEPENDENCIES
    // ------------------------------------------------------

    implementation(project(":domain"))

    // ----------------------------
    // 🔷 ANDROID X
    // ----------------------------

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // ------------------------------------------------------
    // 🔐 DEPENDENCY INJECTION
    // ------------------------------------------------------

    //Hilt
    //implementation("com.google.dagger:hilt-android:2.56.2")
    //ksp("com.google.dagger:hilt-android-compiler:2.56.2")

    implementation("io.insert-koin:koin-android:3.5.0")
    implementation("io.insert-koin:koin-core:3.5.0")
    implementation("io.insert-koin:koin-androidx-compose:3.5.0") // if using Compose


    // ------------------------------------------------------
    // 🧰 Utilities
    // ------------------------------------------------------

    //Room
    //val room_version = "2.6.1" // Use the latest stable version
    implementation("androidx.room:room-runtime:2.7.2")
    annotationProcessor("androidx.room:room-compiler:2.7.2") // For Java
    ksp("androidx.room:room-compiler:2.7.2") // For Kotlin


    // ----------------------------
    // 🧪 JVM UNIT TESTS (test/)
    // ----------------------------

    testImplementation(libs.junit)

    // ----------------------------
    // 🧪 ANDROID INSTRUMENTED TESTS (androidTest/)
    // ----------------------------

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}