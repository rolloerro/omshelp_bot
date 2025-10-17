plugins {
    id("fabric-loom") version "1.7.4"
    id("maven-publish")
}

group = "com.rolloerro"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://libraries.minecraft.net/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.20.1")
    mappings("net.fabricmc:yarn:1.20.1+build.10:v2")
    modImplementation("net.fabricmc:fabric-loader:0.15.7")

    // fabric API (если используешь)
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.92.0+1.20.1")

    // убираем JUnit и тесты
}

tasks {
    // вырубаем тесты
    named<Test>("test") {
        enabled = false
    }

    processResources {
        filesMatching("fabric.mod.json") {
            expand(
                "version" to project.version
            )
        }
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${project.name}" }
        }
    }

    // чистим всякие toolchain’ы
    compileJava {
        options.release.set(17)
    }
}
