plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.papermc.paperweight.userdev") version "1.7.1"
}

group = "me.zaksen"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven( "https://repo.xenondevs.xyz/releases") {
        name = "inv-ui"
    }
    maven("https://maven.enginehub.org/repo/") {
        name = "worldedit"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    shadow("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.charleskorn.kaml:kaml:0.66.0")

    implementation("xyz.xenondevs.invui:invui:" + property("invui_version"))
    implementation("xyz.xenondevs.invui:invui-kotlin:" + property("invui_version"))

    implementation("io.github.skytasul:guardianbeam:2.3.6")

    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.6")

    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.assemble {
    dependsOn(tasks.reobfJar)
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
