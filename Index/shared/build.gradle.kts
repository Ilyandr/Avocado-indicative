import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
}

kotlin {
    android()
    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64


    iosTarget("ios") {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    val ktorVersion = "1.5.0"
    val coroutinesVersion = "1.4.2-native-mt"
    val serializationVersion = "1.0.1"
    val sqlDelightVersion = "1.4.4"

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-core:${serializationVersion}")

                implementation("io.ktor:ktor-client-core:${ktorVersion}")
                implementation("io.ktor:ktor-client-json:${ktorVersion}")
                implementation("io.ktor:ktor-client-serialization:${ktorVersion}")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("de.peilicke.sascha:kase64:1.0.6")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
                implementation("com.squareup.sqldelight:coroutines-extensions:${sqlDelightVersion}")

                implementation("org.kodein.di:kodein-di:7.1.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("io.ktor:ktor-client-okhttp:${ktorVersion}")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutinesVersion}")
                api("com.squareup.sqldelight:android-driver:${sqlDelightVersion}")

                implementation("com.google.android.gms:play-services-vision-common:19.1.3")
                implementation("com.google.android.gms:play-services-vision:20.1.3@aar")
                implementation("com.google.android.gms:play-services-location:20.0.0")
                implementation("com.google.zxing:core:3.4.1")
                implementation("com.journeyapps:zxing-android-embedded:4.3.0@aar")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:${ktorVersion}")
                implementation("com.squareup.sqldelight:native-driver:${sqlDelightVersion}")
            }
        }

        val iosTest by getting
    }
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}