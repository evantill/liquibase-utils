plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

dependencies {
    implementation(group = "net.researchgate", name = "gradle-release", version = "2.8.1")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
