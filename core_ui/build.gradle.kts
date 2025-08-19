plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose) //Enable Compose
}

android {
    namespace = "com.loaizasoftware.core_ui"
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

    // ----------------------------
    // 🔷 ANDROID X
    // ----------------------------

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // ----------------------------
    // 🧩 COMPOSE CORE
    // ----------------------------
    implementation(platform("androidx.compose:compose-bom:2024.03.00")) // Compose BOM (manages all Compose versions)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3") //To use Box, Column, Row, Scaffold, etc
    implementation("androidx.compose.ui:ui-tooling-preview")

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