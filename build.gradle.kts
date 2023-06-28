import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("architectury-plugin")
    id("dev.architectury.loom").apply(false)
    id("com.github.johnrengelman.shadow").apply(false)
    kotlin("jvm") apply false
}

architectury {
    minecraft = minecraft_version
}

subprojects {
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "maven-publish")

    val loom = extensions.getByType<LoomGradleExtensionAPI>()
    loom.run {
        silentMojangMappingsLicense()
    }
    repositories {
        maven {
            url = uri("https://maven.parchmentmc.org/")
            content {
                includeGroup("org.parchmentmc.data")
            }
        }
    }

    dependencies {
        minecraft("com.mojang:minecraft:$minecraft_version")
        mappings(loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-$parchment_version@zip")
        })
    }

    extensions.getByType<BasePluginExtension>().apply {
        archivesName.set(archiveBaseName)
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "architectury-plugin")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    version = semantics_version
    group = maven_group

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"

        kotlinOptions.freeCompilerArgs = listOf(
            "-Xlambdas=indy",
            "-Xjvm-default=all",
        )
    }

    extensions.getByType<JavaPluginExtension>().apply {
        withSourcesJar()
    }

}