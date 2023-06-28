package lens

import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.ResourceManagerReloadListener

object Listener : ResourceManagerReloadListener {

    override fun onResourceManagerReload(resourceManager: ResourceManager) {
        println("mark 1")
        println("mark 2")
        println("mark 3")
    }

}