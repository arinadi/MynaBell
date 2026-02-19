plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
}

import java.util.Properties
import java.io.FileInputStream

android {
    namespace = "com.myna.bell"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.myna.bell"
        minSdk = 26
        targetSdk = 34

        // Git-based Versioning
        fun getGitCommitCount(): Int {
            return try {
                val process = ProcessBuilder("git", "rev-list", "--count", "HEAD").start()
                process.inputStream.bufferedReader().readText().trim().toInt()
            } catch (e: Exception) {
                1 // Fallback
            }
        }

        fun getGitCommitHash(): String {
            return try {
                val process = ProcessBuilder("git", "rev-parse", "--short", "HEAD").start()
                process.inputStream.bufferedReader().readText().trim()
            } catch (e: Exception) {
                "unknown"
            }
        }

        val commitCount = getGitCommitCount()
        val commitHash = getGitCommitHash()

        versionCode = commitCount
        versionName = "1.$commitCount.$commitHash"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            // Only sign if keystore exists (prevents local build failures if missing)
            if (file("release.jks").exists()) {
                storeFile = file("release.jks")
                storePassword = System.getenv("SIGNING_STORE_PASSWORD")
                keyAlias = System.getenv("SIGNING_KEY_ALIAS")
                keyPassword = System.getenv("SIGNING_KEY_PASSWORD")

                // Fallback to secrets.properties for local builds if env vars are missing
                if (storePassword.isNullOrEmpty()) {
                    val secretsPropertiesFile = rootProject.file("secrets.properties")
                    if (secretsPropertiesFile.exists()) {
                        val props = Properties()
                        props.load(FileInputStream(secretsPropertiesFile))
                        storePassword = props.getProperty("SIGNING_STORE_PASSWORD")
                        keyAlias = props.getProperty("SIGNING_KEY_ALIAS")
                        keyPassword = props.getProperty("SIGNING_KEY_PASSWORD")
                    }
                }
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    // Rename APK to include version name
    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                output.outputFileName = "MynaBell-${variant.name}-${variant.versionName}.apk"
            }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true // Enable desugaring if needed for API level < 24 with Java 8+ features
    }
    // Enable deprecation warnings for Java compilation
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:deprecation")
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    // composeOptions block removed as it is no longer needed with the Compose Compiler plugin
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.57.2")
    ksp("com.google.dagger:hilt-android-compiler:2.57.2")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Media3
    implementation("androidx.media3:media3-exoplayer:1.3.0")
    implementation("androidx.media3:media3-ui:1.3.0")
    implementation("androidx.media3:media3-session:1.3.0")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Security
    implementation("androidx.security:security-crypto:1.0.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
