import com.logicovercode.bsbt.scala_module.ScalaBuild

val githubRepo = githubHosting("logicovercode", "core-commons", "techLeadAtLogicOverCode", "techlead@logicovercode.com")

val build = ScalaBuild("com.logicovercode", "core-commons", "0.0.001")
  .sourceDirectories("core", "generated-dependencies")
  .testSourceDirectories("generator")
  .dependencies(
    "com.logicovercode" %% "core-adts" % "0.0.001",
  )
  .testDependencies(
    "org.scalatest" %% "scalatest" % "3.2.10",
    "org.scalameta" %% "scalameta" % "4.4.32",
    "org.typelevel" %% "cats-core" % "2.8.0",
    "com.github.pathikrit" %% "better-files" % "3.9.1"
  )
  .testResourceDirectories("generator-config")
  .javaCompatibility("1.8", "1.8")
  .scalaVersions("2.13.8")
  .publish(githubRepo.developer, MIT_License, githubRepo, Opts.resolver.sonatypeStaging)

//idePackagePrefix := Some("com.logicovercode.ccommons")

//val fSbtAdtsModule = SbtModule("com.logicovercode" %% "fsbt-adts" % "0.0.001", PARENT_DIRECTORY / "fsbt-adts", "fSbtAdtsProject")

lazy val fSbtCommonsProject = (project in file("."))
  .settings(build.settings)
  //.dependsOn(fSbtAdtsModule)

(publishLocal) := ( (publishLocal).toTask dependsOn (Compile / scalafmt) dependsOn (Test / test) ).value

