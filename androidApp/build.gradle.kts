import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.measify.findtravelnow"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.travelapp.findtravelnow"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 7
        versionName = "2.0.0-alpha01"
    }

    val keystorePropertiesFile = rootProject.file("androidApp/keystore/keystore.properties")
    val isSigningKeyExists = keystorePropertiesFile.exists()
    val keystoreProperties = Properties()
    if (isSigningKeyExists) keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    signingConfigs {
        if (isSigningKeyExists)
            create("release") {
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["keystorePassword"] as String?
                keyAlias = keystoreProperties["keyAlias"] as String?
                keyPassword = keystoreProperties["keyPassword"] as String?
            }
    }

    buildTypes {
        val debug by getting {
            isMinifyEnabled = false
            isDebuggable = true
        }

        val release by getting {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            if (isSigningKeyExists) signingConfig = signingConfigs.getByName("release")
        }
    }

    tasks.register("getAppName") {
        // Set the task"s actions
        doLast {
            val versionName = defaultConfig.versionName
            val versionCode = defaultConfig.versionCode
            val appName = "APP-v${versionName}-$versionCode"
            // Print the app name
            println(appName)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        buildConfig = true
    }
}
