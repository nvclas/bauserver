plugins {
    alias(libs.plugins.runPaper)
    alias(libs.plugins.paperweight.userdev)
}

version = 2.0

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)
}

tasks {
    runServer {
        minecraftVersion(libs.versions.minecraft.get())
    }
}