plugins {
    `java-library`
    `maven-publish`
    signing
    id ("net.researchgate.release") version "2.8.1"
}

group = "com.github.evantill"
description = "plugin for liquibase extensions"

val liquibasePackages = listOf(
        "liquibase.ext.utils.preconditions"
)

val ossrhReleaseUrl: String by project
val ossrhSnapshotUrl: String by project
val ossrhUsername: String by project
val ossrhPassword: String by project

val travis = System.getenv("CI")?.toBoolean()?:false

repositories {
    jcenter()
}

dependencies {
    implementation("org.liquibase:liquibase-core:3.8.1")
    testImplementation("junit:junit:4.13")
    testImplementation("org.assertj:assertj-core:3.16.1")
    testImplementation("org.hsqldb:hsqldb:2.3.4")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

tasks.jar {
    manifest {
        attributes("Liquibase-Package" to liquibasePackages.joinToString())
    }
}

publishing {
    publications {
        repositories {
            maven {
                name = "nexus"
                val releasesRepoUrl = ossrhReleaseUrl
                val snapshotsRepoUrl = ossrhSnapshotUrl
                url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
        }

        create<MavenPublication>("maven") {
            pom {
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/evantill/${project.name}")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("evantill")
                        name.set("Eric Vantillard")
                        email.set("eric.vantillard@evaxion.fr")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/evantill/${project.name}.git")
                    developerConnection.set("scm:git:ssh://github.com:evantill/${project.name}.git")
                    url.set("http://github.com/evantill/${project.name}/tree/master")
                }
            }
            from(components["java"])
        }
    }

}

signing {
    if (travis) {
        useInMemoryPgpKeys(
                project.property("signingKey").toString(),
                project.property("signingPassword").toString()
        )
    }
    sign(publishing.publications["maven"])
}
