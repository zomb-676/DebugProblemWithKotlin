package lens.fabric.client

import lens.Listener
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

class LensModClient : ClientModInitializer {
    override fun onInitializeClient() {

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(
            object : SimpleSynchronousResourceReloadListener {
                override fun getFabricId(): ResourceLocation = ResourceLocation("len","listener")
                override fun onResourceManagerReload(resourceManager: ResourceManager) {
                    println("listener trig before")
                    Listener.onResourceManagerReload(resourceManager)
                    println("listener trig after")
                }
            }
        )

    }
}
