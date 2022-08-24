package com.logicovercode.ccommons

import better.files.Dsl.{cwd, mkdirs, rm, touch}
import better.files.File
import com.logicovercode.cadts._

trait FileHandling {

  private def recreateFile(file: File): File = {
    if (!file.exists) {
      mkdirs(file.parent)
      touch(file)
    } else {
      rm(file)
      touch(file)
    }
  }

  def parseDependencyLines(configFile: File): List[String] = {
    println(s"config files exists : ${configFile.exists}")

    for {
      line <- configFile.lines.toList
      isNonEmpty = !line.trim.isEmpty
      isNotStartingWithHash = !line.trim.startsWith("#")
      if (isNonEmpty && isNotStartingWithHash)
    } yield line
  }

  def allGeneratedDependenciesTraitFile : File = cwd / "generated-dependencies" / "dependencies" / s"AllGeneratedDependencies.scala"

  def regeneratedTraitFile(configFile : File) : File = {
    val traitFile = cwd / "generated-dependencies" / "dependencies" / s"${configFile.nameWithoutExtension}Dependencies.scala"
    recreateFile(traitFile)
    traitFile
  }

  def parseJDependencyLine(line: String): Either[String, DependencyMeta[JDependency]] = {
    val isNotCrossPath = !line.contains("%%")
    val str = line.replace("%%", "%").replace("\"", "").replace(":", "%")
    str.split("%") match {
      case Array(method_name, org, artifactId, version) if isNotCrossPath =>
        val jdep = JDependency(org.trim, artifactId.trim, version.trim)
        Right(DependencyMeta(method_name, jdep))
      case _ => Left(s"$line is not parsable.")
    }
  }

  def parseSDependencyLine(line: String): Either[String, DependencyMeta[SDependency]] = {
    val isCrossPath = line.contains("%%")
    val str = line.replace("%%", "%").replace("\"", "").replace(":", "%")
    str.split("%") match {
      case Array(method_name, org, artifactId, version) if isCrossPath =>
        val dep = SDependency(org.trim, artifactId.trim, version.trim)
        Right(DependencyMeta(method_name, dep))
      case _ => Left(s"$line is not parsable.")
    }
  }
}
