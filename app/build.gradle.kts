import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)


    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
    kotlin("plugin.serialization") version "2.1.0"
    alias(libs.plugins.baselineprofile)

//    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
//    kotlin("plugin.serialization") version "1.9.0"
//    id("com.google.gms.google-services")

//    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" // 👈 Add this

}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}
val baseUrl = localProperties.getProperty("BASE_URL") ?: ""

android {
    namespace = "com.video.videoappdemo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.video.videoappdemo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
    }

    buildTypes {
        /*release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }*/

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.test.junit4.android)
    implementation(libs.androidx.profileinstaller)
    testImplementation(libs.junit)
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    // MockWebServer for API testing
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
    // Ktor client testing
    testImplementation("io.ktor:ktor-client-mock:2.2.3")
    testImplementation("io.ktor:ktor-client-content-negotiation:2.2.3")
    testImplementation("io.ktor:ktor-serialization-kotlinx-json:2.2.3")

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation("io.mockk:mockk-android:1.13.8")
    "baselineProfile"(project(":baselineprofile"))

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.compose.navigation)


    implementation("io.ktor:ktor-client-core:2.2.3")
    implementation("io.ktor:ktor-client-logging:2.2.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.2.3")
    implementation("io.ktor:ktor-client-okhttp:2.2.3")
    implementation("io.ktor:ktor-client-android:2.2.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("io.coil-kt:coil-compose:2.2.2")

//    implementation("androidx.compose.material:material:1.6.8")


    implementation ("com.google.code.gson:gson:2.9.0")

    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("androidx.core:core-ktx:1.12.0")


    implementation("androidx.media3:media3-exoplayer:1.4.1" )
    implementation("androidx.media3:media3-ui:1.4.1" )
    implementation("androidx.media3:media3-exoplayer-dash:1.4.1")






}

kotlin {
    sourceSets.all {
        kotlin.srcDir("build/generated/ksp/${name}/kotlin")
    }
}