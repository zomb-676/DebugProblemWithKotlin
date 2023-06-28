plugins {
    id("com.github.johnrengelman.shadow")
    kotlin("jvm")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
}

val common by configurations.creating
val shadowCommon by configurations.creating
val developmentFabric = configurations.named("developmentFabric")

configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    developmentFabric.get().extendsFrom(common)
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:$fabric_loader_version")
    modApi("net.fabricmc.fabric-api:fabric-api:$fabric_api_version")

    common(project(path = ":Common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":Common", configuration = "transformProductionFabric")) { isTransitive = false }

//    implementation(kotlin("stdlib"))
    modImplementation("net.fabricmc:fabric-language-kotlin:1.9.6+kotlin.1.8.22")

}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand("versions" to project.version)
    }
}

tasks.shadowJar {
    exclude("architectury.common.json")
    configurations = listOf(shadowCommon)
    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    val shadowJarTask = tasks.shadowJar.get()
    injectAccessWidener.set(true)
    inputFile.set(shadowJarTask.archiveFile)
    dependsOn(shadowJarTask)
    archiveClassifier.set(null as String?)
}

tasks.jar {
    archiveClassifier.set("dev")
}

tasks.sourcesJar {
    val commonSources = project(":Common").tasks.sourcesJar.get()
    dependsOn(commonSources)
    from(commonSources.archiveFile.map(project::zipTree))
}

components.getByName<SoftwareComponent>("java") {
    (this as AdhocComponentWithVariants).apply {
        withVariantsFromConfiguration(project.configurations.shadowRuntimeElements.get()) {
            skip()
        }
    }
}
