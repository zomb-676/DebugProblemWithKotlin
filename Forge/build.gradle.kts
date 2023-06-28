plugins {
    id("com.github.johnrengelman.shadow")
    kotlin("jvm")
}

architectury {
    platformSetupLoomIde()
    forge()
}

dependencies {
    forge("net.minecraftforge:forge:$forge_version")
}

loom {
    accessWidenerPath.set(project(":Common").loom.accessWidenerPath)
}

val common by configurations.creating
val shadowCommon by configurations.creating
val developmentForge = configurations.named("developmentForge")

configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    developmentForge.get().extendsFrom(common)
}

dependencies {
    forge("net.minecraftforge:forge:$forge_version")

    common(project(path = ":Common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":Common", configuration = "transformProductionForge")) { isTransitive = false }


    forgeRuntimeLibrary(kotlin("stdlib:1.8.10"))
    compileOnly(kotlin("stdlib"))

//    forgeRuntimeLibrary("icyllis.modernui:ModernUI-Core:${modernui_core_version}")
//    modRuntimeOnly("icyllis.modernui:ModernUI-Forge:${minecraft_version}-${modernui_version}")
}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("META-INF/mods.toml") {
        expand("version" to project.version)
    }
}

tasks.shadowJar {
    exclude("fabric.mod.json")
    exclude("architectury.common.json")

    configurations = listOf(shadowCommon)

    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    val shadowJarTask = tasks.shadowJar.get()
    inputFile.set(shadowJarTask.archiveFile)
    dependsOn(shadowJarTask)
    archiveClassifier.set(null as String?)
}

tasks.jar {
    archiveClassifier.set("dev")
}

tasks.sourcesJar {
    val commonSources = project(":Common").tasks.sourcesJar
    dependsOn(commonSources)
    from(commonSources.get().archiveFile.map(project::zipTree))
}

components.getByName<SoftwareComponent>("java") {
    (this as AdhocComponentWithVariants).apply {
        withVariantsFromConfiguration(project.configurations.shadowRuntimeElements.get()) {
            skip()
        }
    }
}

//see https://github.com/architectury/architectury-loom/issues/135
tasks.runClient {
    classpath += project.files(File(projectDir, "build/classes/kotlin/main"))
}

tasks.runServer {
    classpath += project.files(File(projectDir, "build/classes/kotlin/main"))
}