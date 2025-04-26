import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm") version "2.1.10"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("com.vanniktech.maven.publish") version "0.31.0"
}

group = "org.ToothBrush"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks {
    runServer {
        minecraftVersion("1.21")
    }
}

mavenPublishing {
    coordinates(
        groupId = "io.github.haburashi76",
        artifactId = "publish-test",
        version = "0.0.1"
    )
    pom {
        name.set("publish-test")
        description.set("test")
        inceptionYear.set("2025")
        url.set("https://github.com/haburashi76/publishTest")
        licenses {
            license {
                name.set("GNU General Public License version 3")
                url.set("https://opensource.org/licenses/GPL-3.0")
            }
        }
        developers {
            developer {
                id.set("haburashi76")
                name.set("Haburashi76")
                email.set("haburashi76@gmail.com")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/haburashi76/publishTest.git")
            developerConnection.set("scm:git:ssh://github.com/username/publishTest.git")
            url.set("https://github.com/haburashi76/publishTest.git")
        }
    }
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    //signAllPublications()
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
