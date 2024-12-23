plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.parcelize)
//    alias(libs.plugins.compose.compiler) version "2.0.0"
}
android {
    namespace = "com.nooro.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        defaultConfig {
            resValue("string", "apiUrl", project.findProperty("apiUrl").toString())
            resValue("string", "apiKey", project.findProperty("apiKey").toString())
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.coroutines.adapter)
    implementation(libs.koin.android)
    implementation(libs.okhttp3.logging.interceptor)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.arch.core.testing)
}
