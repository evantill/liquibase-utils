import com.vdurmont.semver4j.Semver

plugins {
    `java-library`
    `maven-publish`
    signing
    jacoco
    id("net.researchgate.release")
    id("com.github.kt3k.coveralls") version "2.8.4"
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

val liquibaseVersion:String by project

val travis = System.getenv("CI")?.toBoolean() ?: false
val useAutomaticVersion = booleanProperty("release.useAutomaticVersion")

repositories {
    jcenter()
}

buildscript {
    dependencies {
        classpath("com.vdurmont:semver4j:3.1.0")
    }
}

dependencies {
    compileOnly("org.liquibase:liquibase-core:$liquibaseVersion")
    compileOnly("org.jetbrains:annotations:19.0.0")
    testImplementation("org.liquibase:liquibase-core:$liquibaseVersion")
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
            maven {
                name = "local"
                url = uri("$buildDir/publishing-repository")
            }
        }

        create<MavenPublication>("library") {
            from(components["java"])
            pom {
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/evantill/${project.name}")
                packaging = "jar"
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
    sign(publishing.publications["library"])
}



if (travis || useAutomaticVersion) {
    val projectVer = Semver("${project.version}")
    project.ext["release.releaseVersion"] = projectVer.withClearedSuffixAndBuild().value
    project.ext["release.newVersion"] = projectVer.nextMinor().withSuffix("SNAPSHOT").value
}

tasks {

    //declare liquibase extensions packages
    jar {
        manifest {
            attributes("Liquibase-Package" to liquibasePackages.joinToString())
        }
    }

    // publish artefacts when releasing
    // disabled as we use travis trigger to publish on tag
    /*afterReleaseBuild {
        dependsOn(publish)
    }*/

    // report is always generated after tests run
    test {
        finalizedBy(jacocoTestReport)
    }

    // tests are required to run before generating the report
    jacocoTestReport {
        dependsOn(test)
        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }
    }

}
