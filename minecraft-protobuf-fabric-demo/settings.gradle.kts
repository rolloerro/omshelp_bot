pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")        // Fabric repo
        gradlePluginPortal()                        // Gradle plugins
        mavenCentral()                              // Backup repo
    }
}

rootProject.name = "minecraft-protobuf-fabric-demo"
include("app")
