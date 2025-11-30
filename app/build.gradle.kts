plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs.kotlin") // add safe args in navigation with navGraph
    alias(libs.plugins.kotlin.serialization) // add Json serialization in Ktor
}

android {
    namespace = "com.mobile.vedroid.kt"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mobile.vedroid.kt"
        minSdk = 24
        targetSdk = 36
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

    buildFeatures {
        viewBinding = true  // add view binding instead of using findViewById()
        buildConfig = true
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
    // kotlin
    implementation(libs.androidx.core.ktx)

    // material design
    implementation(libs.material)

    // android base elements
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)

    // to use fragments
    implementation(libs.androidx.fragment)

    // to use navGraph
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Ktor (base)
    implementation(libs.ktor.client.core)
    // Ktor client to Android
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)
    // Json serialization in Ktor
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.2.0")

    // tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}