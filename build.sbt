name := "core-commons"

version := "0.0.001"

scalaVersion := "2.13.8"

crossScalaVersions := Seq("2.13.8", "3.3.1")

libraryDependencies += "com.logicovercode" %% "core-adts" % "0.0.001"

val projectSourceDirs = List("core", "generated-dependencies")
Compile / unmanagedSourceDirectories ++= projectSourceDirs.map(dir => (Compile / baseDirectory).value / dir)

organization := "com.logicovercode"

val techLead = Developer(
  "techLead",
  "techLead",
  "techlead@logicovercode.com",
  url("https://github.com/logicovercode")
)
developers := List(techLead)

homepage := Some(
  url("https://github.com/logicovercode/core-commons")
)
scmInfo := Some(
  ScmInfo(
    url("https://github.com/logicovercode/core-commons"),
    "git@github.com:logicovercode/core-commons.git"
  )
)

licenses += ("MIT", url("https://opensource.org/licenses/MIT"))

publishTo := Some(Opts.resolver.sonatypeStaging)

publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)

(publishLocal) := ((publishLocal).toTask dependsOn (Compile / scalafmt) dependsOn (Test / test)).value

val fSbtCommonsProject = project in file(".")

lazy val dependencyCodeGenerator = (project in file("dependency-code-generator"))
  .settings(
    // Define the Scala version for compile scope
    scalaVersion := "2.13.8",
    // Define the Scala version for test scope
    Test / scalaVersion := "2.13.6",
    Test / unmanagedSourceDirectories ++= List("generator").map(dir => (Test / baseDirectory).value / dir),

    Test / unmanagedSourceDirectories ++= List("generator-config").map(dir => (Test / baseDirectory).value / dir),
    libraryDependencies += "com.logicovercode" %% "core-adts" % "0.0.001",


    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.17",
      "org.scalameta" %% "scalameta" % "4.4.32",
      "org.typelevel" %% "cats-core" % "2.10.0",
      "com.github.pathikrit" %% "better-files" % "3.9.2"
    ).map(_ % Test)
  )