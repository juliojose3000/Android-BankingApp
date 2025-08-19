plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.loaizasoftware.core"
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

    // ----------------------------
    // 🔷 ANDROID X
    // ----------------------------

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)


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