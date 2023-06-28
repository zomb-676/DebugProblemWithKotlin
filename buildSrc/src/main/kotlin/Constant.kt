import org.gradle.api.Project

//Mod options
const val mod_name = "Lens"

//Common
const val minecraft_version = "1.20.1"
const val parchment_version = "1.20.1:2023.06.26"
const val enabled_platforms = "fabric,forge"

//Fabric
const val fabric_loader_version = "0.14.21"
const val fabric_api_version = "0.84.0+$minecraft_version"

//Forge
const val forge_version = "$minecraft_version-47.0.19"
const val modernui_core_version = "3.7.0"
const val modernui_version = "3.7.0.5"

//Project
const val version_major = 0.1
const val version_patch = 1
const val semantics_version = "$minecraft_version-$version_major.$version_patch"
const val maven_group = "lens"

val Project.archiveBaseName get() = "$mod_name-${project.name.lowercase()}"
