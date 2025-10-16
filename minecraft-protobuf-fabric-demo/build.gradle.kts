// Корневой build.gradle.kts
allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://libraries.minecraft.net/")
    }
}
