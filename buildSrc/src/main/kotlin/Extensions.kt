import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import net.fabricmc.loom.util.Constants.Configurations as FabricConstantConfiguration

val Project.common get() = project.project(":Common")

fun DependencyHandlerScope.minecraft(any: Any) {
    FabricConstantConfiguration.MINECRAFT(any)
}

fun DependencyHandlerScope.mappings(dependency: Dependency) {
    FabricConstantConfiguration.MAPPINGS(dependency)
}

fun cusrseMaven(modName: String, projectID: Long, fileId: Long) = "curse.maven:$modName-$projectID:$fileId"