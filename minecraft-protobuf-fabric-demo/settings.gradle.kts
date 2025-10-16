pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") // ✅ тут лежит fabric-loom
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "minecraft-protobuf-fabric-demo"
include("app")
