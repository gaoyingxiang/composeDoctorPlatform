import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
}

group = "me.administrator"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    val ktorVersion = "2.1.2"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("com.alibaba:fastjson:1.2.76")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        javaHome = System.getenv("JAVA_HOME_FOR_DESKTOP")
        nativeDistributions {
//            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb,TargetFormat.Exe)
//            targetFormats(TargetFormat.Exe)
            targetFormats(TargetFormat.Msi)
            packageName = "log"
            packageVersion = "1.0.0"
//            outputBaseDir.set(project.buildDir.resolve("customOutputDir"))
            macOS {
                packageVersion = "1.1.0"
                dmgPackageVersion = "1.1.0"
                pkgPackageVersion = "1.1.0"

                packageBuildVersion = "1.1.0"
                dmgPackageBuildVersion = "1.1.0"
                pkgPackageBuildVersion = "1.1.0"
            }
            windows {
                packageVersion = "1.2.0"
                msiPackageVersion = "1.2.0"
                exePackageVersion = "1.2.0"
            }
        }
    }
}