import java.util.*
import java.io.*

plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}

val localProperties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "local.properties")))
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "environment"

    productFlavors {

        create(BuildFlavors.REDFILE) {
            dimension = "environment"

            versionName = Android.versionName
            applicationIdSuffix = ApplicationIdSuffix.REDFILE
            manifestPlaceholders["appTheme"] = "@style/Theme.ImageCalculatorTestRed"
            resValue("string", "app_name", "Image Calculator Test - RedFile")

            buildConfigField("String", "BUILD_VARIANT", "\"REDFILE\"")
        }

        create(BuildFlavors.REDCAMERA) {
            dimension = "environment"

            versionName = Android.versionName
            applicationIdSuffix = ApplicationIdSuffix.REDCAMERA
            manifestPlaceholders["appTheme"] = "@style/Theme.ImageCalculatorTestRed"
            resValue("string", "app_name", "Image Calculator Test - RedCamera")

            buildConfigField("String", "BUILD_VARIANT", "\"REDCAMERA\"")
        }

        create(BuildFlavors.GREENFILE) {
            dimension = "environment"

            versionName = Android.versionName
            applicationIdSuffix = ApplicationIdSuffix.GREENFILE
            manifestPlaceholders["appTheme"] = "@style/Theme.ImageCalculatorTestGreen"
            resValue("string", "app_name", "Image Calculator Test - GreenFile")

            buildConfigField("String", "BUILD_VARIANT", "\"GREENFILE\"")
        }

        create(BuildFlavors.GREENCAMERA) {
            dimension = "environment"

            versionName = Android.versionName
            applicationIdSuffix = ApplicationIdSuffix.GREENCAMERA
            manifestPlaceholders["appTheme"] = "@style/Theme.ImageCalculatorTestGreen"
            resValue("string", "app_name", "Image Calculator Test - GreenCamera")

            buildConfigField("String", "BUILD_VARIANT", "\"GREENCAMERA\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    }

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        viewBinding = true
    }


    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.homeUiHome))

    implementation(Androidx.core)
    implementation(Androidx.appCompat)
    implementation(Google.material)
    implementation(Androidx.constraint)
    implementation(Androidx.legacy)

    // Lifecycle Components
    implementation(Androidx.viewModel)
    implementation(Androidx.liveData)
    implementation(Androidx.runtime)
    implementation(Androidx.viewModelSavedState)
    implementation(Androidx.commonJava)

    // Hilt
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

    // Coroutine
    implementation(Kotlin.coroutineCore)
    implementation(Kotlin.coroutineAndroid)

    // Navigation(Component
    implementation(Jetpack.fragmentNavigation)
    implementation(Jetpack.uiNavigation)

    // Timber
    implementation(Timber.library)
}