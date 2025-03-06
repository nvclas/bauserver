plugins {
    alias(libs.plugins.runPaper)
    alias(libs.plugins.paperweight.userdev)
}

version = 2.1

dependencies {
    compileOnly(libs.luckperms)
    paperweight.paperDevBundle(libs.versions.paper)
}

tasks {
    runServer {
        minecraftVersion(libs.versions.minecraft.get())
    }
}