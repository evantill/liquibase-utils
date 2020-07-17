import net.researchgate.release.GitAdapter.GitConfig
import net.researchgate.release.ReleaseExtension

fun ReleaseExtension.git(configure: GitConfig.() -> Unit) {
    (propertyMissing("git") as GitConfig).configure()
}