import org.gradle.api.Project

fun Project.booleanProperty(name: String, defaultValue: Boolean = false): Boolean {
    return findProperty(name)?.toString()?.toBoolean() ?: defaultValue
}
