import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildKonfig)
}

buildkonfig {
    packageName = "ravi.gaurav.learning.tmdb"

    defaultConfigs {
        val authToken: String = gradleLocalProperties(rootDir).getProperty("AUTH_TOKEN")

        require(authToken.isNotEmpty()) {
            "Register your api key from developer and place it in local.properties as `AUTH_TOKEN`"
        }

        buildConfigField(FieldSpec.Type.STRING, "AUTH_TOKEN", authToken)
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            //Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            //Ktor
            implementation(libs.ktor.client.okhttp)
            implementation(libs.slf4j.android)
            implementation(libs.ktor.client.android)

            //Decompose
            implementation(libs.decompose)

            implementation(libs.kamel.image)
            implementation(libs.androidx.window)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(compose.materialIconsExtended)

            //Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            api(libs.koin.core)
            implementation(libs.koin.compose)

            //Ktor
            implementation(libs.bundles.ktor)

            //Kotlin-Serialization
            implementation(libs.kotlinx.serialization.json)

            //Decompose
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)
            api(libs.esenty.lifecycle.coroutine)

            implementation(libs.kamel.image)

            implementation(libs.kotlinx.datetime)

        }
        iosMain.dependencies {
            //Ktor
            implementation(libs.ktor.client.darwin)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            //Ktor
            implementation(libs.ktor.client.okhttp)
            implementation(libs.slf4j.simple)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "ravi.gaurav.learning.tmdb"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
//    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "ravi.gaurav.learning.tmdb"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ravi.gaurav.learning.tmdb"
            packageVersion = "1.0.0"
        }
    }
}
