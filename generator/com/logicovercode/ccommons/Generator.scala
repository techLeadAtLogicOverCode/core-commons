package com.logicovercode.ccommons

import better.files.Dsl._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpecLike
import org.scalatest.matchers.should.Matchers

import scala.meta._

class Generator extends AsyncFlatSpecLike with Matchers with FileHandling with BeforeAndAfterAll{

  override protected def beforeAll(): Unit = {
    rm(allGeneratedDependenciesTraitFile.parent)
  }

  def traitBody(allMethods: List[Stat], traitName: String): String = {
    val prefix =
      s"""
     package com.logicovercode.ccommons
     package dependencies
     import com.logicovercode.cadts._

     trait $traitName extends DependencyValidation
      """

    val methodStrings = q"""..${allMethods}"""

    prefix ++ methodStrings.syntax
  }

  "generating java dependencies from Java.conf" should "work" in {

    val configFile = cwd / "generator-config" / "Java.conf"
    val traitFile = regeneratedTraitFile(configFile)

    val eitherModuleIds = parseDependencyLines(configFile).map(parseJDependencyLine)

    val errorModuleIdMsgs = eitherModuleIds collect { case Left(msg) => msg }
    val dependencyMetaSeq = eitherModuleIds collect { case Right(fSbtMid) => fSbtMid }

    val allDefs = dependencyMetaSeq.map(_.jDefinitions())

    traitFile.append(traitBody(allDefs.flatten, traitFile.nameWithoutExtension))

    println(errorModuleIdMsgs.mkString("\n"))
    errorModuleIdMsgs.size should be (0)
    traitFile.exists should be(true)
    traitFile.contentAsString.length > 0 should be(true)
  }

  "generating scala dependencies from Scala.conf" should "work" in {

    val configFile = cwd / "generator-config" / "Scala.conf"
    val traitFile = regeneratedTraitFile(configFile)

    val eitherModuleIds = parseDependencyLines(configFile).map(parseSDependencyLine)

    val errorModuleIdMsgs = eitherModuleIds collect { case Left(msg) => msg }
    val dependencyMetaSeq = eitherModuleIds collect { case Right(fSbtMid) => fSbtMid }

    val allDefs = dependencyMetaSeq.map(_.sDefinitions())

    traitFile.append(traitBody(allDefs.flatten, traitFile.nameWithoutExtension))

    println(errorModuleIdMsgs.mkString("\n"))
    errorModuleIdMsgs.size should be(0)
    traitFile.exists should be(true)
    traitFile.contentAsString.length > 0 should be(true)
  }

  "generate all dependencies" should "work" in {
    val str =
      """
        |package com.logicovercode.ccommons
        |package dependencies
        |
        |trait AllGeneratedDependencies extends JavaDependencies with ScalaDependencies{
        |
        |}
        |""".stripMargin

    val traitFile = regeneratedTraitFile(allGeneratedDependenciesTraitFile)

    traitFile.append(str)

    traitFile.exists should be(true)
    traitFile.contentAsString.length > 0 should be(true)
  }
}
