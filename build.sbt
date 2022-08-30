name := "core-commons"

version := "0.0.001"

scalaVersion := "2.13.8"

val projectSourceDirs = List("core", "generated-dependencies")
Compile / unmanagedSourceDirectories ++= projectSourceDirs.map(dir => (Compile / baseDirectory).value / dir)

val testSourceDirs = List("generator")
Test / unmanagedSourceDirectories ++= testSourceDirs.map(dir => (Test / baseDirectory).value / dir)

val testResourceDirs = List("generator-config")
Test / unmanagedSourceDirectories ++= testResourceDirs.map(dir => (Test / baseDirectory).value / dir)


libraryDependencies += "com.logicovercode" %% "core-adts" % "0.0.001"
libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.10",
    "org.scalameta" %% "scalameta" % "4.4.32",
    "org.typelevel" %% "cats-core" % "2.8.0",
    "com.github.pathikrit" %% "better-files" % "3.9.1"  
).map(_ % Test)

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

(publishLocal) := ( (publishLocal).toTask dependsOn (Compile / scalafmt) dependsOn (Test / test) ).value

val fSbtCommonsProject = project in file(".")