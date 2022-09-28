import mill.api.Loose
import mill.{T, _}
import mill.scalalib._
import mill.scalalib.publish.{Developer, License, PomSettings, VersionControl}
import coursier.maven.MavenRepository

object `core-commons` extends ScalaModule with PublishModule {

  def scalaVersion = "2.13.8"

  def repositoriesTask = T.task { 
    val localMillRepoPathString = "file:" + sys.env("LOCAL_MILL_REPO")
    println(s"local mill repo path >$localMillRepoPathString<")
    val localMillRepo = MavenRepository(localMillRepoPathString)
    super.repositoriesTask() ++ Seq(localMillRepo) 
  }

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