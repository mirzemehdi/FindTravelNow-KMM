plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinNativeCocoapods)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    task("testClasses") //For some reason without that it gives error
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()


    cocoapods {
        ios.deploymentTarget = "14.1"
        version = "1.0.0"
        summary = "Shared module of FindTravelNow"
        homepage = "https://github.com/mirzemehdi/FindTravelNow-KMM/"
        framework {
            baseName = "shared"
            isStatic = true
            export(libs.kmpNotifier)
        }
    }


    //Generating BuildConfig for multiplatform
    val buildConfigGenerator by tasks.registering(Sync::class) {
        val packageName = "secrets"
        val secretProperties = readPropertiesFromFile("secrets.properties")
        from(
            resources.text.fromString(
                """
        |package $packageName
        |
        |object BuildConfig {
        |  const val API_KEY = "${secretProperties.getPropertyValue("API_KEY")}"
        |  const val REVENUECAT_API_KEY_ANDROID = "${secretProperties.getPropertyValue("REVENUECAT_API_KEY_ANDROID")}"
        |  const val REVENUECAT_API_KEY_IOS = "${secretProperties.getPropertyValue("REVENUECAT_API_KEY_IOS")}"
        |}
        |
      """.trimMargin()
            )
        )
        {
            rename { "BuildConfig.kt" } // set the file name
            into(packageName) // change the directory to match the package
        }
        into(layout.buildDirectory.dir("generated-src/kotlin/"))
    }

    sourceSets {
        commonMain {
            kotlin.srcDir(
                // convert the task to a file-provider
                buildConfigGenerator.map { it.destinationDir })
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)

                implementation(libs.bundles.koin)
                implementation(libs.bundles.voyager)
                implementation(libs.bundles.ktor)
                implementation(libs.multiplatformSettings.noargs)
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.datetime)
                implementation(libs.napier)
                api(libs.kmpNotifier)
                implementation(libs.kmpAuth.firebase)
                implementation(libs.kmpAuth.uihelper)
                implementation(libs.kmpAuth.google)

                implementation(libs.coil.compose)
                implementation(libs.coil.ktor)

                implementation(libs.kmprevenuecat.purchases)
                implementation(libs.kmprevenuecat.purchases.ui)


            }
        }
        androidMain  {
            dependencies {
                api(libs.androidx.activity.compose)
                api(libs.androidx.appcompat)
                api(libs.androidx.core)
                api(libs.androidx.lifecycle.runtime.compose)
                api(libs.koin.android)

                //Firebase
                api(project.dependencies.platform(libs.firebase.bom))
                api(libs.firebase.analytics)
                api(libs.firebase.crashlytics)
                api(libs.firebase.messaging)

            }
        }
        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.measify.findtravelnow.shared"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }

}
/**
 * Property File name example secrets.properties
 * two local file can be created
 * secrets.properties (can be pushed to Git) as template to show the keys,
 * secrets.local.properties with actual values (should be added to .gitignore)
 *
 * Properties file content should be like this.
 * key=value
 * key2=value2
 */
fun readPropertiesFromFile(fileName: String): Map<String, String> {
    val parts = fileName.split('.')
    val localPropertyFileName = if (parts.size >= 2) {
        val nameWithoutExtension = parts.dropLast(1).joinToString(".")
        val extension = parts.last()
        "$nameWithoutExtension.local.$extension"
    } else {
        fileName
    }
    val isLocalFileExists= File(project.rootDir,localPropertyFileName).exists()
    val fileContent = try {
        File(project.rootDir,if (isLocalFileExists) localPropertyFileName else fileName).readText()
    } catch (e: Exception) {
        e.printStackTrace()
        return emptyMap()
    }

    val properties = mutableMapOf<String, String>()

    fileContent.lines().forEach { line ->
        val keyValuePair = line.split('=')
        if (keyValuePair.size == 2) {
            properties[keyValuePair[0].trim()] = keyValuePair[1]
        }
    }
    return properties
}

/**
 * If System.env value exists it will be returned value which can be useful for CI/CD pipeline
 */
fun Map<String, String>.getPropertyValue(key: String): String? {
    val envValue = System.getenv(key)
    if (envValue.isNullOrEmpty().not()) return envValue
    return this.getOrDefault(key, null)
}

