plugins {
    kotlin("jvm")
}

architectury {
    common((enabled_platforms).split(","))
}

loom {
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${fabric_loader_version}")
    compileOnly(kotlin("stdlib"))
}