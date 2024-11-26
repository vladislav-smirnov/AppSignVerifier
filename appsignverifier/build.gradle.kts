import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("com.vanniktech.maven.publish") version "0.30.0"
}


val aarVersion: String? = gradleLocalProperties(rootDir, providers).getProperty("VERSION_NAME")

android {
    namespace = "io.github.airdaydreamers.appsignverifier"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

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

//maven
//publishReleasePublicationToMavenCentralRepository
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "io.github.airdaydreamers.appsignverifier"
            artifactId = "appsignverifier"

            version = aarVersion ?: "0.8.2-alpha"

            pom {
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }

                developers {
                    name = "appsignverifier"
                    description =
                        "App Signature Verifier is a utility library for Android that provides tools to verify various properties of applications"
                    url = "https://github.com/vladislav-smirnov/AppSignVerifier"

                    developer {
                        id = "vladislavsmirnov"
                        name = "Vladislav Smirnov"
                        email = "sivdead@gmail.com"
                    }
                }

                scm {
                    connection = "scm:git:git://github.com/vladislav-smirnov/AppSignVerifier.git"
                    developerConnection =
                        "scm:git:ssh://github.com:vladislav-smirnov/AppSignVerifier.git"
                    url = "https://github.com/vladislav-smirnov/AppSignVerifier"
                }
            }

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
dependencies {
    implementation(libs.androidx.core.ktx)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.core.ktx)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.byte.buddy)
}