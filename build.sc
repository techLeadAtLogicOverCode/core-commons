import mill.api.Loose
import mill.{T, _}
import mill.scalalib._
import mill.scalalib.publish.{Developer, License, PomSettings, VersionControl}

object `core-commons` extends ScalaModule with PublishModule {

  def scalaVersion = "2.13.8"

  override def sources = T.sources {
    val coreSrcDir = millSourcePath / os.up / "core"
    val generatorSrcDir = millSourcePath / os.up / "generated-dependencies"
    Seq(coreSrcDir, generatorSrcDir).map( PathRef(_) )
  }

  override def pomSettings = {
    PomSettings(
      description = "core commons",
      organization = "com.logicovercode",
      url = "https://github.com/techLeadAtLogicOverCode/core-commons",
      licenses = Seq(License.MIT),
      versionControl = VersionControl.github("techLeadAtLogicOverCode", "core-commons"),
      developers = Seq(
        Developer("techlead", "techlead", "https://github.com/techLeadAtLogicOverCode")
      )
    )
  }

  override def publishVersion = "0.0.001"

  override def ivyDeps: T[Loose.Agg[Dep]] = Agg(ivy"com.logicovercode::core-adts:0.0.001")
}